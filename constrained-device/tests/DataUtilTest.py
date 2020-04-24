'''
Created on Apr 24, 2020

@author: yccha
'''
import unittest
from zone.common.SensorData import SensorData
from zone.common.ActuatorData import ActuatorData
import json
from zone.util.DataUtil import DataUtil
class Test(unittest.TestCase):


    def setUp(self):
        self.util = DataUtil()
        pass


    def tearDown(self):
        pass

    def testDataUtilSensorUtil(self):
        
        self.sen = SensorData()
        self.sen.addValue(10);
        self.sen.SetName('temperature')
        val1 = self.util.JsonFromSensorData(self.sen)
        val2 = self.util.SensorDataFromJson(val1)
        val3 = json.loads(val1)
        self.assertTrue(val2.curValue == val3['curValue'], 'sensor util failed')
        
    def testDataUtilActuatorUtil(self):
        val1 = "{\"name\":\"Cooling\",\"command\":\"On\",\"zoneId\":\"Bedroom2\",\"timestamp\":{\"date\":{\"year\":2020,\"month\":4,\"day\":24},\"time\":{\"hour\":13,\"minute\":5,\"second\":54,\"nano\":627000000}},\"reportedTemp\":24.543771919402232}"
        val2 = self.util.ActuatorDataFromJson(val1)
        val3 = json.loads(val1)        
        self.assertTrue(val2.curValue == val3['reportedTemp'], 'actuator util failed')

if __name__ == "__main__":
    #import sys;sys.argv = ['', 'Test.testName']
    unittest.main()