/**
 * 
 */
package com.ypitta.MultiZoneClimateControl.gateway_device;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.ubidots.ApiClient;
import com.ubidots.Value;
import com.ubidots.Variable;

import com.ypitta.MultiZoneClimateControl.gateway_device.common.*;
import com.ypitta.MultiZoneClimateControl.gateway_device.service.UbidotsToAWSShadowUpdater;

/**
 * Test class for all requisite Module08 functionality.
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
public class UbidotsClientConnectorTest
{
	// setup methods
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
	
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
	public void testUbidotsApi()
	{
		UbidotsToAWSShadowUpdater shadow = new UbidotsToAWSShadowUpdater("Hall2", "https://a2k03cghbd2dw4-ats.iot.us-east-1.amazonaws.com:8443");
		shadow.instantiate();
		Thread t1 = new Thread(shadow);
		t1.start();
		SensorData data = new SensorData();
		data.addValue(22.0);
		ApiClient api = new ApiClient("BBFF-9c483f56a6cd19615f5ae77ea93cb36fd49");
		LOGGER.info("\nConnecting to Ubidots using the API......");
		Variable var = api.getVariable("5e9fc3ee1d847277347716c9");
		var.saveValue(data.getCurValue());
		LOGGER.info("\nGetting a variable instance of 5e9fc3ee1d847277347716c9");
		Value[] vals = var.getValues();
		assertTrue("Ubidots API test failed", BigDecimal.valueOf(data.getCurValue()).setScale(2,RoundingMode.HALF_UP).doubleValue() == vals[0].getValue());
	}
}
