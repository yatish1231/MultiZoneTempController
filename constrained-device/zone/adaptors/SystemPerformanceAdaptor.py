import logging
from threading import Thread
from time import sleep

from zone.util.SystemCpuUtilTask import SystemCpuUtilTask
from zone.util.SystemMemUtilTask import SystemMemUtilTask


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
        
    def getCpuUtil(self):
        cpuUtilVal = self.cpuUtilObj.getDataFromSensor()
        logging.info('The CPU Utilization is ' + str(cpuUtilVal))
        return cpuUtilVal
    
    def getMemUtil(self):
        memUtilVal = self.memUtilObj.getDataFromSensor()
        logging.info('The memory Utilization is ' + str(memUtilVal) + ' percent ')
        return memUtilVal