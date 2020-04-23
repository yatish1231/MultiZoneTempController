'''
Created on Feb 7, 2020

@author: yccha
'''

class ActuatorData():
    '''
    ActuatorData is a model class for holding actuator data.
    '''
    command = 'null'
    timeStamp = 'none'
    name = 'not set'
    zoneId= ''
    curValue = 0
    avgValue = 0
    minValue = 0
    maxValue = 0
    totValue = 0 
    sampleCount = 0

    def __init__(self):
        '''
        Constructor
        '''
        
    def getCommand(self):
        
        return self.command
        
    def getValue(self):
        
        return self.curValue
    
    def getName(self):
        
        return self.name
    
    def setCommand(self, command):
        self.command = command
    
    def setName(self, name):
        
        self.name = name
        
class TempActuatorConst():
    '''
    TempActuatorConst() is a class that has constants for key values of the configuration file.
    '''
    TEMP_INC = 'INCREASE'
    TEMP_DEC = 'DECREASE'
    TEMP_MAIN = 'MAINTAIN'