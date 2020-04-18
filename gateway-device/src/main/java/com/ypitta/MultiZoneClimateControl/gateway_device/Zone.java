package com.ypitta.MultiZoneClimateControl.gateway_device;

import java.util.logging.Logger;

import com.amazonaws.services.iot.client.AWSIotQos;
import com.ypitta.MultiZoneClimateControl.gateway_device.MqttClient.AWSTopicSubscriber;
import com.ypitta.MultiZoneClimateControl.gateway_device.MqttClient.MqttClientSubscriber;
import com.ypitta.MultiZoneClimateControl.gateway_device.service.AWSSubscriberGenerator;
import com.ypitta.MultiZoneClimateControl.gateway_device.service.GatewaySubscriberGenerator;

public class Zone implements Runnable{
	
	private String zoneId;
	
	private AWSIotQos qos;
	
	private MqttClientSubscriber sub_constrained;

	private AWSTopicSubscriber sub_aws;
	
	private AWSSubscriberGenerator aws_generator;
	
	private GatewaySubscriberGenerator gateway_generator;
	
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	public Zone(String zoneId, AWSIotQos qos) {
		super();
		this.zoneId = zoneId;
		this.qos = qos;
	}

	private void instantiate() {
		LOGGER.info("\nInstantiating generators....");
		this.aws_generator = new AWSSubscriberGenerator(zoneId, qos);
		this.gateway_generator = new GatewaySubscriberGenerator(zoneId);
	}
	
	private void connectZone() {
		if(this.aws_generator == null || this.gateway_generator == null) {
			instantiate();
		}
		LOGGER.info("\nGetting instances of subscribers....");
		this.sub_constrained = this.gateway_generator.getInstance();
		this.sub_aws = this.aws_generator.getInstance();
		Thread aws = new Thread(this.sub_aws);
		Thread constrained = new Thread(this.sub_constrained);
		LOGGER.info("\nStarting worker threads....");
		aws.start();
		constrained.start();
				
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		connectZone();
	}
	
	
	
	
}
