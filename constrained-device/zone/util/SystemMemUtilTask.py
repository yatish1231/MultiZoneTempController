import psutil

class SystemMemUtilTask():
    #this class contains methods that return memory utilization data
    def __init__(self):
        pass
         
    def getDataFromSensor(self):
        #this method returns percentage of the used memory
        return psutil.virtual_memory()[2]
