"""
Test class for all requisite Module08 functionality.

Instructions:
1) Rename 'testSomething()' method such that 'Something' is specific to your needs; add others as needed, beginning each method with 'test...()'.
2) Add the '@Test' annotation to each new 'test...()' method you add.
3) Import the relevant modules and classes to support your tests.
4) Run this class as unit test app.
5) Include a screen shot of the report when you submit your assignment.

Please note: While some example test cases may be provided, you must write your own for the class.
"""

import unittest
from zone.adaptors.MqttClientConnector import MqttClientConnector 
from zone.common.SensorData import SensorData
from zone.util.DataUtil  import DataUtil
from threading import Thread
from time import sleep

class Module08Test(unittest.TestCase):

	"""
	Use this to setup your tests. This is where you may want to load configuration
	information (if needed), initialize class-scoped variables, create class-scoped
	instances of complex objects, initialize any requisite connections, etc.
	"""
	obj = None
	obj1 = None
	
	def setUp(self):
		self.obj = MqttClientConnector('python/client')
		self.obj1 = MqttClientConnector('python/actuator')
		
		

	"""
	Use this to tear down any allocated resources after your tests are complete. This
	is where you may want to release connections, zero out any long-term data, etc.
	"""
	def tearDown(self):
		pass

	"""
	Place your comments describing the test here.
	"""
	def testConnectionWithGateway(self):
		self.obj.connect('broker.hivemq.com', 1883)
		
		data = SensorData()
		data.SetName('temperature')
		data.addValue(15)
		json = DataUtil().JsonFromSensorData(data)
		self.obj.publish_message('test/csye6530', json,1)
		self.obj1.connect('broker.hivemq.com', 1883)
		self.t1 = Thread(target = self.obj1.subscribeToActuatorCommands('test/act/csye6530', 2))
		self.t1.setDaemon(True)
		self.t1.start()
		sleep(5)
		print('reached')
		self.assertTrue(self.obj1.act_data.curValue == data.curValue, 'test failed')
		pass

if __name__ == "__main__":
	#import sys;sys.argv = ['', 'Test.testName']
	unittest.main()