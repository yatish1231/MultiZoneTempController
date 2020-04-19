'''
Created on Apr 18, 2020

@author: yccha
'''

class PerformanceData():
    '''
    classdocs
    '''
    cpu = 0
    memory = 0

    def __init__(self):
        '''
        Constructor
        '''
        
    def getCpuUtil(self):
        return self.cpu
    
    def setCpuUtil(self, cpu_util):
        self.cpu = cpu_util
        
    def getMemUtil(self):
        return self.memory
    
    def setMemUtil(self,mem):
        self.memory = mem