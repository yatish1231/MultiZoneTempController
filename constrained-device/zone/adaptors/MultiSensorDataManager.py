'''
Created on Feb 7, 2020

@author: yccha
'''
import logging
import threading
import traceback

from labs.common.ActuatorData import ActuatorData, TempActuatorConst
from labs.common.ConfigUtil import ConfigUtil, ConfigConst
from labs.common.SensorData import SensorData
from labs.module02.SmtpClientConnector import SmtpClientConnector
from labs.module04 import MultiActuatorAdaptor, MultiSensorAdaptor

class MultiSensorDataManager():
    '''
    SensorDataManager class is responsible for handling the sensor data. It determines if the current data should result in a actuation.
    It has attributes sensorData, config_file, temp_act_adpt. 
    Method handleSensorData(data) takes in sensor data, and if the data requires actuation it calls the necessary methods.
    '''
    sensorData = SensorData
    config_file = None
    temp_act_adpt = None
    
    def __init__(self):
        '''
        Constructor. Initializes configuration file, Temperature actuator and smtp client.
        '''
        self.multi_act_adpt = MultiActuatorAdaptor.MultiActuatorAdaptor()
        self.connector = SmtpClientConnector()
        self.config_file = ConfigUtil()
        logging.basicConfig(format='%(asctime)s:%(levelname)s:%(message)s',level=logging.DEBUG)
        
        
    def startPolling(self):
        self.sensor_adaptor = MultiSensorAdaptor.MultiSensorAdaptor(MultiSensorDataManager().handleSensorData,MultiSensorDataManager().handleSensorData)
        
    
    def handleSensorData(self, data):
        '''
        Method handleSensorData(data) takes in sensor data, and if the data requires actuation it calls the necessary methods
        '''
        try:
            self.sensorData = data
                
            if (50 < data.getCurrentValue()):
                new_val = ActuatorData()
                new_val.setCommand(TempActuatorConst().TEMP_DEC)
                new_val.curValue = self.sensorData.getCurrentValue()
                self.multi_act_adpt.updateActuator(new_val)
                x=threading.Thread(target = self.connector.publishMessage, args=('Humidity above nominal\n', self.sensorData.getSensorData()))
                x.start()
                x.join()
                return True
            
            elif(25 > data.getCurrentValue()):
                new_val1 = ActuatorData()
                new_val1.setCommand(TempActuatorConst().TEMP_INC)
                new_val1.curValue = self.sensorData.getCurrentValue()
                self.multi_act_adpt.updateActuator(new_val1)
                x=threading.Thread(target = self.connector.publishMessage, args=('Humidity below nominal\n', self.sensorData.getSensorData()))
                x.start()
                x.join()
                return True
                
            else:
                new_val2 = ActuatorData()
                new_val2.setCommand(TempActuatorConst().TEMP_MAIN)
                new_val2.curValue = self.sensorData.getCurrentValue()
                self.multi_act_adpt.updateActuator(new_val2)
                return True
            
        except Exception:        
            logging.info(traceback.print_exc())
            
            