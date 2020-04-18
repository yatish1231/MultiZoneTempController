'''
Created on Jan 29, 2020

@author: yccha
'''

from _datetime import datetime


class SensorData():
    '''
    Class sensor data stores the sensor data.
    addValue() method takes in a values and adds the value to the previously stored value and calculates the average
    There are methods to get all the variable's values
    '''
    timeStamp = None
    name = 'not set'
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
        self.timeStamp = str(datetime.now())
    
    def addValue(self, newValue):
        self.sampleCount += 1
        self.timeStamp = str(datetime.now())
        self.curValue = newValue
        self.totValue += newValue
        
        if(self.sampleCount == 1):
            self.minValue = self.curValue
            
        if(self.curValue<self.minValue):
            self.minValue = self.curValue
        
        if (self.curValue>self.maxValue):
            self.maxValue = self.curValue
        
        if(self.totValue!=0 and self.sampleCount>0):
            self.avgValue = self.totValue/self.sampleCount
            #print('count is ' + str(self.sampleCount))
            
    def getSensorData(self):
        return str('\nTime: {}\nCurrent : {}\nAverage : {}\nSamples: {}\nMin: {}\nMax: {}\nName: {}\n'.format(self.getTime(),self.getCurrentValue(), self.getAverageValue(), self.getCount(), self.getMinValue(), self.getMaxValue(), self.getName()))
            
    def getAverageValue(self):
        return self.avgValue
    
    def getCount(self):
        return self.sampleCount
    
    def getCurrentValue(self):
        return self.curValue
    
    def getMaxValue(self):
        return self.maxValue
    
    def getMinValue(self):
        return self.minValue
    
    def getName(self):
        return self.name
    
    def SetName(self, name):
        self.name = name
        
    def getTime(self):
        return self.timeStamp