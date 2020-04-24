'''
Created on Feb 22, 2020

@author: yccha
'''
import json

from zone.common.ActuatorData import ActuatorData
from zone.common.SensorData import SensorData
import traceback
import logging
class DataUtil():
    '''
    This class is responsible for handling the conversion of SensorData and ActuatorData object to and from JSON.
    '''
    def __init__(self):
        '''
        Constructor
        '''
    def JsonFromSensorData(self, sensor_data):
        '''
        Converts to JSON from sensorData
        '''
        x = vars(sensor_data)
        return json.dumps(x)
    
    
    def SensorDataFromJson(self, json_data):
        '''
        Converts to SensorData object from JSON
        '''
        sensor_data = SensorData()
        data = json.loads(json_data)
        sensor_data.timeStamp = data['timeStamp']
        sensor_data.name = data['name']
        sensor_data.sampleCount = data['sampleCount']
        sensor_data.curValue = data['curValue']
        sensor_data.totValue = data['totValue']
        sensor_data.minValue = data['minValue']
        sensor_data.maxValue = data['maxValue']
        sensor_data.avgValue = data['avgValue']
        return sensor_data
    
    def JsonFromActuatorData(self, actuator_data):
        '''
        Converts to JSON from ActuatorData object
        '''
        x = vars(actuator_data)
        return json.dumps(x)
    
    
    def ActuatorDataFromJson(self, json_data):
        '''
        Converts to ActuatorData object from JSON
        '''
        try:    
            actuator_data = ActuatorData()
            data = json.loads(json_data)
            actuator_data.name = data['name']
            actuator_data.command = data['command']
            actuator_data.timeStamp = data['timestamp']
            actuator_data.zoneId = data['zoneId']
            actuator_data.curValue = data['reportedTemp']
            return actuator_data
        except Exception:
            logging.info(traceback.print_exc())
    
    def JsonFromPerformanceData(self,data):
        x= vars(data)
        return json.dumps(x)