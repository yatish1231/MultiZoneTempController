package com.ypitta.MultiZoneClimateControl.gateway_device;

import com.amazonaws.services.iot.client.AWSIotException;
import com.amazonaws.services.iot.client.AWSIotMessage;
import com.amazonaws.services.iot.client.AWSIotMqttClient;
import com.amazonaws.services.iot.client.AWSIotQos;
import com.ypitta.MultiZoneClimateControl.gateway_device.common.SampleUtil;
import com.ypitta.MultiZoneClimateControl.gateway_device.common.SampleUtil.KeyStorePasswordPair;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testAWSMqttClientConnector()
    {
    	String clientEndpoint = "a2k03cghbd2dw4-ats.iot.us-east-1.amazonaws.com";       // replace <prefix> and <region> with your own
    	String clientId = "livingRoom";                              // replace with your own client ID. Use unique client IDs for concurrent connections.
    	String certificateFile = "configKeys/0a92f0a33d-certificate.pem.crt";                       // X.509 based certificate file
    	String privateKeyFile = "configKeys/0a92f0a33d-private.pem.key";                        // PKCS#1 or PKCS#8 PEM encoded private key file
    	KeyStorePasswordPair pair = SampleUtil.getKeyStorePasswordPair(certificateFile, privateKeyFile);
    	AWSIotMqttClient client = new AWSIotMqttClient(clientEndpoint, clientId, pair.keyStore, pair.keyPassword);    	
    	try {
    	client.connect();
    		String payload = "{\"temp\" :12}";
    		client.publish(new AWSIotMessage("update/temperature/testZone2", AWSIotQos.QOS0, payload.getBytes()));
    		client.publish(new AWSIotMessage("update/temperature/testZone1", AWSIotQos.QOS0, payload.getBytes()));
    		assertTrue( true );
    	} catch (AWSIotException e) {
    		assertTrue(false);
    		e.printStackTrace();
    	}
        
    }
}
