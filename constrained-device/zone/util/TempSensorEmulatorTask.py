'''
Created on Jan 29, 2020

@author: yccha
'''
import logging
from random import random
from random import randint
class TempSensorEmulatorTask():
    '''
    Class TempsensorEmulatorTask() emulates a temperature sensor. It generates random values between 0-30
    '''
    def __init__(self):
        logging.basicConfig(format='%(asctime)s:%(levelname)s:%(message)s',level=logging.DEBUG)
        

    def randomValue(self):
        
        return randint(18,20)+ randint(-2,5) + random()*(2)
