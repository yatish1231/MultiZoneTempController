/**
 * 
 */
package com.ypitta.MultiZoneClimateControl.gateway_device;

import static org.junit.Assert.assertTrue;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.ypitta.MultiZoneClimateControl.gateway_device.common.*;
import com.ypitta.MultiZoneClimateControl.gateway_device.MqttClient.*;

/**
 * Test class for all requisite Module06 functionality.
 * 
 * Instructions:
 * 1) Rename 'testSomething()' method such that 'Something' is specific to your needs; add others as needed, beginning each method with 'test...()'.
 * 2) Add the '@Test' annotation to each new 'test...()' method you add.
 * 3) Import the relevant modules and classes to support your tests.
 * 4) Run this class as unit test app.
 * 5) Include a screen shot of the report when you submit your assignment.
 * 
 * Please note: While some example test cases may be provided, you must write your own for the class.
 */
public class MqttClientConnectorTest
{
	/*
	 * Tests the implementation of module06. Working with MQTT
	 */
	// setup methods
	private MqttClientConnector connector1;
	private MqttClientConnector connector2;
	private MqttClient publisher;
	private MqttClient subscriber;
	private DataUtil util;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		connector1 = new MqttClientConnector("test/publisher", "tcp://broker.hivemq.com:1883");
		connector1.connectMqttClient();
		publisher = connector1.getClient();
		connector2 = new MqttClientConnector("test/subscriber", "tcp://broker.hivemq.com:1883");
		connector2.connectMqttClient();
		subscriber = connector2.getClient();
		util = new DataUtil();
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception
	{
	}
	
	// test methods
	
	/**
	 * 
	 */
	@Test
	public void testMqttClientConnector()
	{	
		/*
		 * Tests the working of the Class ClientConnector.
		 * Test creates two clients, one as subscriber the other as publisher. 
		 * The message that is published and received is compared for the test.
		 */
		SensorData data = new SensorData();
		data.setName("test temperature");
	    data.addValue((double) 10);
	    String json = util.JsonFromSensorData(data);
		MqttMessage msg = new MqttMessage();
		msg.setQos(2);
		msg.setPayload(json.getBytes());
		try {	
			connector2.subscribe("test/6530", 2);
			Thread.currentThread();
			Thread.sleep(10000);
			publisher.publish("test/6530", msg);
		} catch (MqttPersistenceException e) {
			e.printStackTrace();
		} catch (MqttException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MqttMessage rcv = connector2.getRcvMsg();
			String msg1 = new String(rcv.getPayload());
			System.out.println(msg1);
			System.out.println(json);
			assertTrue("Test failed", json == msg1);
		
//		fail("Not yet implemented");
	}
	
}
