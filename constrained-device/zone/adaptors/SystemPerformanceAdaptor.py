import logging
from threading import Thread
from time import sleep
import time

from labs.module01.SystemCpuUtilTask import SystemCpuUtilTask
from labs.module01.SystemMemUtilTask import SystemMemUtilTask


class SystemPerformanceAdaptor(Thread):
    #this class contains methods to log system performance data
    #rateInSec and enableAdaptor are to be passed in the constructor
    def __init__(self, rateInSec = 10, enableAdaptor = False):
        Thread.__init__(self)
        self.cpuUtilObj = SystemCpuUtilTask()
        self.memUtilObj = SystemMemUtilTask()
        self.enableAdaptor = enableAdaptor
        self.rateInSec = rateInSec
        
    def run(self):
        #this method is run when the thread is started, the thread executes once every 'rateInSec' seconds and logs the data
        while True:
            
            if self.enableAdaptor:
                cpuUtil = self.cpuUtilObj.getDataFromSensor()
                memUtil = self.memUtilObj.getDataFromSensor()
                logging.info('The CPU Utilization is ' + str(cpuUtil))
                logging.info('The memory Utilization is ' + str(memUtil) + ' percent ')
                                
            sleep(self.rateInSec)
