'''
Created on Feb 7, 2020

@author: yccha
'''
import threading
from time import sleep

from labs.module04.HI2CSensorAdaptorTask import HI2CSenseHatAdaptor
from labs.module04.HumiditySensorAdaptorTask import HumiditySensorAdaptorTask


class MultiSensorAdaptor():
    '''
    
    '''
    def __init__(self,data_manager1,data_manager2):
        '''
        Constructor
        Initializes all the necessary variables and starts HumidityAdaptorTask and HI2CSenseHatAdaptorTask thread
        '''
        self.Sense_adaptor = HI2CSenseHatAdaptor(data_manager1)
        self.Sense_adaptor.initI2CBus()
        self.Sense_adaptor.read_cont()
        self.Sense_adaptor.start()
        self.SenseHat_Adaptor = HumiditySensorAdaptorTask(data_manager2)
        self.SenseHat_Adaptor.start()
        x=threading.Thread(target=self.compareData)
        x.start()
        
    def compareData(self):
        '''
        This method calculates the percent difference between humidity value from SenseHat api and I2C adaptor task  
        '''
        while True:
            
            if(self.SenseHat_Adaptor.getData().getCurrentValue()!=0):
                
                diff = (abs(self.Sense_adaptor.getData().getCurrentValue() - self.SenseHat_Adaptor.getData().getCurrentValue())/self.SenseHat_Adaptor.getData().getCurrentValue())*100
                print('\nDifference is :' + str(diff) + ' percent')
            
            sleep(5)
