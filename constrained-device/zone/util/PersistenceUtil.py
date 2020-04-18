'''
Created on Feb 22, 2020

@author: yccha
'''
import redis
import logging
from zone.adaptors.MultiActuatorAdaptor import MultiActuatorAdaptor
from zone.util.DataUtil import DataUtil
from threading import Thread 
class PersistenceUtil(Thread):
    '''
    This class is responsible for maintaining a connection with the Database. It has methods to write data to database and also subscribe to a 
    with the channel.
    '''
    def __init__(self, act_listener):
        super(PersistenceUtil,self).__init__()
        self.util = DataUtil()
        self.act_adaptor = MultiActuatorAdaptor()
        self.act_listener = act_listener
        self.data_util = DataUtil()
        pass
    
    def writeActuatorData(self, actuator_data):
        '''
        This method writes actuator data to the databse
        '''
        r = redis.Redis(host='redis-16265.c11.us-east-1-2.ec2.cloud.redislabs.com', port=16265, password='iot_1231')
        r.set('act_latest' ,actuator_data)
    
    def writeSensorData(self, sensor_data):
        '''
        This method writes sensor data to the databse
        '''
        r = redis.Redis(host='redis-16265.c11.us-east-1-2.ec2.cloud.redislabs.com', port=16265, password='iot_1231')
        r.set('sen_latest' ,sensor_data)
        r.publish('sen_channel', 'sen_latest')
    
    def run(self):
        self.registerActuatorDataDbmsListener()
    
    def registerActuatorDataDbmsListener(self):
        '''
        This method subscribes to the actuator trigger channel
        '''
        r1 = redis.Redis(host='redis-16265.c11.us-east-1-2.ec2.cloud.redislabs.com', port=16265, password='iot_1231')
        r = redis.Redis(host='redis-16265.c11.us-east-1-2.ec2.cloud.redislabs.com', port=16265, password='iot_1231')
        p = r.pubsub()
        p.subscribe('act_channel')
        
        while True:
            message = p.get_message()
            if(type(message)!=type(None)):
                if(message['data']!=1):
                    value = r1.get(message['data'])
                    logging.info('Actuator data recieved is:' + str(value))
                    act_obj = self.util.ActuatorDataFromJson(value)
                    self.act_adaptor.updateActuator(act_obj)
 
