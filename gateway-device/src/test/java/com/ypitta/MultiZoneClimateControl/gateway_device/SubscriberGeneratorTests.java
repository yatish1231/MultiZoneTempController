package com.ypitta.MultiZoneClimateControl.gateway_device;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.amazonaws.services.iot.client.AWSIotConnectionStatus;
import com.amazonaws.services.iot.client.AWSIotQos;
import com.ypitta.MultiZoneClimateControl.gateway_device.MqttClient.AWSTopicSubscriber;
import com.ypitta.MultiZoneClimateControl.gateway_device.MqttClient.MqttClientSubscriber;
import com.ypitta.MultiZoneClimateControl.gateway_device.service.AWSSubscriberGenerator;
import com.ypitta.MultiZoneClimateControl.gateway_device.service.GatewaySubscriberGenerator;

public class SubscriberGeneratorTests {
	
	
	private GatewaySubscriberGenerator gateway_generator;
	
	private AWSSubscriberGenerator aws_generator;
	
	private String zoneId;
	
	private AWSIotQos qos;
	
	private MqttClientSubscriber sub_constrained;

	private AWSTopicSubscriber sub_aws;
	
	@Before
	public void setUp() throws Exception
	{
		this.zoneId = "testZone1";
		this.qos = AWSIotQos.QOS1;
	}
	
	
	@Test
	public void test() {
		
		this.aws_generator = new AWSSubscriberGenerator(zoneId, qos);
		this.gateway_generator = new GatewaySubscriberGenerator(zoneId);
		this.sub_constrained = this.gateway_generator.getInstance();
		this.sub_aws = this.aws_generator.getInstance();
		
		if(this.sub_aws instanceof AWSTopicSubscriber && this.sub_constrained instanceof MqttClientSubscriber) {
			Thread aws = new Thread(this.sub_aws);
			Thread constrained = new Thread(this.sub_constrained);
			try {
			aws.start();
			constrained.start();
			assertTrue(true);
			}
			catch (Exception e) {
				assertFalse("Failed to start thread", true);
			}
			if(!this.sub_constrained.getConnectedClient().isConnected())
			{
				assertTrue("Constrained device MQTT subscriber is not connected",false);
			}
			if(!(this.sub_aws.getSecureClient().getClientInstance().getConnection().getConnectionStatus() == AWSIotConnectionStatus.CONNECTED)) {
				assertTrue("AWS MQTT client is not connected",false);
			}
			return;
			
		}
		fail("test failed");
	}

}
