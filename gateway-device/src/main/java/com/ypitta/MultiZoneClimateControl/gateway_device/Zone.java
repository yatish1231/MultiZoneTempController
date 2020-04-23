package com.ypitta.MultiZoneClimateControl.gateway_device;

import java.util.logging.Logger;

import com.amazonaws.services.iot.client.AWSIotQos;
import com.ypitta.MultiZoneClimateControl.gateway_device.MqttClient.AWSTopicSubscriber;
import com.ypitta.MultiZoneClimateControl.gateway_device.MqttClient.MqttClientSubscriber;
import com.ypitta.MultiZoneClimateControl.gateway_device.common.ConfigUtil;
import com.ypitta.MultiZoneClimateControl.gateway_device.common.DataUtil;
import com.ypitta.MultiZoneClimateControl.gateway_device.service.AWSSubscriberGenerator;
import com.ypitta.MultiZoneClimateControl.gateway_device.service.GatewaySubscriberGenerator;
import com.ypitta.MultiZoneClimateControl.gateway_device.service.PerformanceSubscriberGenerator;
import com.ypitta.MultiZoneClimateControl.gateway_device.service.UbidotsToAWSShadowUpdater;

public class Zone implements Runnable{
	
	private String zoneId;
	
	private AWSIotQos qos;
	
	private MqttClientSubscriber sub_constrained;

	private AWSTopicSubscriber sub_aws;
	
	private AWSSubscriberGenerator aws_generator;
	
	private MqttClientSubscriber performance_data_sub;
	
	private GatewaySubscriberGenerator gateway_generator;
	
	private PerformanceSubscriberGenerator performance_data;
	
	private UbidotsToAWSShadowUpdater shadow;
	
	private ConfigUtil config_util;
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	public Zone(String zoneId, AWSIotQos qos) {
		super();
		this.zoneId = zoneId;
		this.qos = qos;
		this.config_util = new ConfigUtil();
	}

	private void instantiate() {
		LOGGER.info("\tInstantiating generators....");
		this.aws_generator = new AWSSubscriberGenerator(zoneId, qos);
		this.gateway_generator = new GatewaySubscriberGenerator(zoneId);
		this.performance_data = new PerformanceSubscriberGenerator(zoneId);
		this.shadow = new UbidotsToAWSShadowUpdater(zoneId, "https://a2k03cghbd2dw4-ats.iot.us-east-1.amazonaws.com:8443");
	}
	
	private void connectZone() {
		if(this.aws_generator == null || this.gateway_generator == null) {
			instantiate();
		}
		LOGGER.info("\tGetting instances of subscribers....");
		this.sub_constrained = this.gateway_generator.getInstance();
		this.sub_aws = this.aws_generator.getInstance();
		LOGGER.info("\tGetting performance data Mqtt client instance");
		this.performance_data_sub = this.performance_data.getInstance();
		this.shadow.instantiate();
		Thread aws = new Thread(this.sub_aws);
		Thread constrained = new Thread(this.sub_constrained);
		Thread performanceThread = new Thread(this.performance_data_sub);
		Thread shadow_thread = new Thread(this.shadow);
		LOGGER.info("\tStarting worker threads....");
		aws.start();
		constrained.start();
		performanceThread.start();		
		shadow_thread.start();
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		connectZone();
	}
	
	
	
	
}
