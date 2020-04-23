'''
Created on Apr 14, 2020

@author: yccha
'''
from zone.adaptors.MqttClientConnector import MqttClientConnector
from zone.util.ConfigUtil import ConfigUtil, ConfigConst
from zone.common.SensorData import SensorData
from zone.util import TempSensorEmulatorTask
from zone.util.DataUtil import DataUtil
import logging
from time import sleep
from threading import Thread
from zone.adaptors.SystemPerformanceAdaptor import SystemPerformanceAdaptor
from zone.common.PerformanceData import PerformanceData
from zone.adaptors.PersistenceUtil import PersistenceUtil


class ZoneInitializer():
    '''
    This class can be used to instantiate the Mqtt connectors for subscribing and publishing to Mqtt topics.
    Topics are automatically generated
    '''
    
    zoneid = None
    publisher = None
    subscriber = None
    mqtt_broker = None
    mqtt_qos = None
    mqtt_port = None
    config_util = None

    def __init__(self, zoneId): 
        '''
        @param zoneId: zoneId name as String 
        '''
        self.zoneid = zoneId
        self.config_util = ConfigUtil()
        self.mqtt_broker = self.config_util.getValue(ConfigConst.MQTT_CONF, ConfigConst.MQTT_HOST)
        self.mqtt_port = self.config_util.getValue(ConfigConst.MQTT_CONF, ConfigConst.MQTT_PORT)
        self.mqtt_qos = self.config_util.getValue(ConfigConst.MQTT_CONF, ConfigConst.MQTT_QOS)
        self.perfAdaptor = SystemPerformanceAdaptor(10, True)
        self.redis_instance = PersistenceUtil()
        
    def initialize(self):
        '''
        Method should be called initialize the Mqtt client connectors, SensorData object
        '''
        self.publisher = MqttClientConnector(self.zoneid + '_publisher')
        self.publisher.connect(self.mqtt_broker, int(self.mqtt_port))
        
        self.subscriber = MqttClientConnector(self.zoneid + '_subscriber')
        self.subscriber.connect(self.mqtt_broker, int(self.mqtt_port))
        
        self.sen_Data = SensorData()
        self.task = TempSensorEmulatorTask.TempSensorEmulatorTask()
        self.util = DataUtil()
        
    def readSensorData(self):
        
        self.sen_Data.SetName('temperature')
        
        while(True):
            ran = self.task.randomValue()
            self.sen_Data.addValue(ran)
            self.redis_instance.writeSensorData(self.sen_Data)
            val = self.util.JsonFromSensorData(self.sen_Data)
            self.publisher.publish_message('data/constrained/' + self.zoneid, val, int(self.config_util.getValue(ConfigConst.MQTT_CONF, ConfigConst.MQTT_QOS)))
            logging.info('published message - zone: ' + self.zoneid + ' Current temp: ' + str(ran))
            sleep(60)
        
    def subscribeToActuatorData(self):
        '''
        Call to subscribe to actuator commands
        '''
        t1 = Thread(target=MqttClientConnector.subscribeToActuatorCommands, args=(self.subscriber, 'action/gateway/' + self.zoneid, int(self.config_util.getValue(ConfigConst.MQTT_CONF, ConfigConst.MQTT_QOS)),))
        t1.start()
    
    def sendPerformanceData(self):
        sleep(30)
        while(True):
            cpu = self.perfAdaptor.getCpuUtil()
            mem = self.perfAdaptor.getMemUtil()
            data = PerformanceData()
            data.setCpuUtil(cpu)
            data.setMemUtil(mem)
            msg = self.util.JsonFromPerformanceData(data)
            logging.info('Publishing performace data to topic: ' + 'system/performance/' + self.zoneid + 'Performance data' + msg)
            self.publisher.publish_message('system/performance/' + self.zoneid, msg, int(self.config_util.getValue(ConfigConst.MQTT_CONF, ConfigConst.MQTT_QOS)))
            sleep(300)
        pass
    
    def startReading(self):
        '''
        Call to start reading sensor data - loop every 60 seconds
        '''
        t2 = Thread(target=ZoneInitializer.readSensorData, args=(self,))
        t2.start()
        t3 = Thread(target=ZoneInitializer.sendPerformanceData, args=(self,))
        t3.start()
