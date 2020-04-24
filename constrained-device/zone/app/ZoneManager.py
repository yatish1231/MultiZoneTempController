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
    zone4 = ZoneInitializer('Hall2')
    zone5 = ZoneInitializer('Studyroom2')
    zone6 = ZoneInitializer('Bedroom2')
    zone4.initialize()
    zone5.initialize()
    zone6.initialize()
    
    zone4.startReading()
    zone4.subscribeToActuatorData()
     
    zone5.startReading()
    zone5.subscribeToActuatorData()
    
    zone6.startReading()
    zone6.subscribeToActuatorData()