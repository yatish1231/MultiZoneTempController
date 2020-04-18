'''
Created on Jan 29, 2020

@author: yccha
'''
import logging
from labs.module02 import TempSensorEmulatorTask, SmtpClientConnector
from threading import Thread
from time import sleep
from labs.common.SensorData import SensorData
class TempEmulatorAdaptor(Thread):
    '''
    Class TempEmulatorAdaptor implements a thread to poll temperature values from Temperature sensor
    sendNotification checks the current temperature and call SmtpClientConnector is the temperature is excessive
    '''
    def __init__(self, threshold=10):
        Thread.__init__(self)
        self.threshold = threshold
        self.connector = SmtpClientConnector.SmtpClientConnector()
        logging.basicConfig(format='%(asctime)s:%(levelname)s:%(message)s',level=logging.DEBUG)
        self.TempEmulator = TempSensorEmulatorTask.TempSensorEmulatorTask()
        self.data = SensorData()
        
    def getSensorData(self):
        return str('\nTime: {}\nCurrent temperature: {}\nAverage Temperature: {}\nSamples: {}\nMin: {}\nMax: {}\n'.format(self.data.getTime(),self.data.getCurrentValue(), self.data.getAverageValue(), self.data.getCount(), self.data.getMinValue(), self.data.getMaxValue()))
    
    
    def run(self):
        
        while True:
            num = self.TempEmulator.randomValue()
            self.data.addValue(num)
            print('monitor data {}\n'.format(self.getSensorData()))
            self.sendNotification(self.data.getCurrentValue(), self.data.getAverageValue(), self.getSensorData())
            sleep(5)
    

    def sendNotification(self, currentVal, avgValue, sensorData):
        try:
            if(abs(currentVal - avgValue) >= self.threshold):
                logging.info(' Excessive temperature, sending mail.... \n' + self.getSensorData())
                self.connector.publishMessage('!Alert excessive temperature!', sensorData)
                return True
        except:
            logging.info('sending mail unsuccessful')
            return False
        