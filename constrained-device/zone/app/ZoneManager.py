'''
Created on Apr 14, 2020

@author: yccha
'''
from zone.app.ZoneInitializer import ZoneInitializer
from threading import Thread

if __name__ == '__main__':
    zone1 = ZoneInitializer('testZone1')
    zone2 = ZoneInitializer('testZone3')
    zone3 = ZoneInitializer('testZone4')
    zone1.initialize()
    zone2.initialize()
    zone3.initialize()
    
    zone1.startReading()
    zone1.subscribeToActuatorData()
    
    zone2.startReading()
    zone2.subscribeToActuatorData()
   
    zone3.startReading()
    zone3.subscribeToActuatorData()