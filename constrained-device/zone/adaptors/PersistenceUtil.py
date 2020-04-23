'''
Created on Feb 22, 2020

@author: yccha
'''
import redis
import logging
from zone.util.DataUtil import DataUtil
from threading import Thread 
class PersistenceUtil(Thread):
    '''
    This class is responsible for maintaining a connection with the Database. It has methods to write data to database and also subscribe to a 
    with the channel.
    '''
    def __init__(self):
        super(PersistenceUtil,self).__init__()
        self.util = DataUtil()
        self.data_util = DataUtil()
        pass
    
    def writeActuatorData(self, actuator_data):
        '''
        This method writes actuator data to the databse
        '''
        logging.info('writing actuator data')
        r = redis.Redis(host='localhost', port=6379)
        r.set(actuator_data.timestamp , str(actuator_data))
    
    def writeSensorData(self, sensor_data):
        '''
        This method writes sensor data to the databse
        '''
        logging.info('writing sensor data')
        r = redis.Redis(host='localhost', port=6379)
        r.set(sensor_data.timeStamp, str(sensor_data))
    
    def run(self):
        pass