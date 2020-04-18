import psutil


class SystemCpuUtilTask():
    #this class contains methods that return CPU utilization data
    def __init__(self):
        pass
         
    def getDataFromSensor(self):
    #this method returns average cpu load accross all cores 
        return psutil.cpu_percent()
