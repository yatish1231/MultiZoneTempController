package com.ypitta.MultiZoneClimateControl.gateway_device.service;

import java.util.logging.Logger;

import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;

import com.ypitta.MultiZoneClimateControl.gateway_device.MqttClient.MqttClientConnector;
import com.ypitta.MultiZoneClimateControl.gateway_device.MqttClient.UbidotsMqttCallback;
import com.ypitta.MultiZoneClimateControl.gateway_device.common.AWSUpdateThingShadow;

public class UbidotsToAWSShadowUpdater implements Runnable {
	
	
	private MqttClientConnector sub;
	
	private String zoneId;
	
	private String endpoint;
	
	private AWSUpdateThingShadow shadow;
	
	private String ubidotsTopic;
	
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
			
	public UbidotsToAWSShadowUpdater(String zoneId, String endpoint) {
		super();
		this.zoneId = zoneId;
		this.endpoint = endpoint;
		this.ubidotsTopic = "/v1.6/devices/tempgateway/"+ zoneId.toLowerCase();
	}
	
	public void instantiate() {
		LOGGER.info("Instantiating Mqtt client....zone: " + this.zoneId);
		this.sub = new MqttClientConnector(MqttAsyncClient.generateClientId(), "ssl://industrial.api.ubidots.com:8883", "BBFF-0RvkGIaSBUVcV4bGS7sUeYGWcYyAMJ", "config/ubidots_cert.pem");
		this.shadow = new AWSUpdateThingShadow(this.zoneId, this.endpoint);
		this.shadow.initialize();
		this.sub.ConnectMqttClientOverSSLSetCallBack(new UbidotsMqttCallback(this.shadow));
		LOGGER.info("Connected MQTT client.........");
	}
	

	public void subscribeToUbidots() {
		try {
			this.sub.subscribe(this.ubidotsTopic, 1);
			LOGGER.info("Successfully subscribed to ubidots on topic: "+this.ubidotsTopic);
			
		}catch (Exception e) {
		LOGGER.info("Could not subscribe to UBIDOTS on topic: " + this.ubidotsTopic);	
		}
		
	}
	
	@Override
	public void run() {
		subscribeToUbidots();
	}
	
	public static void main(String[] args) {
		
		UbidotsToAWSShadowUpdater shadow = new UbidotsToAWSShadowUpdater("Hall2", "https://a2k03cghbd2dw4-ats.iot.us-east-1.amazonaws.com:8443");
		shadow.instantiate();
		Thread t1 = new Thread(shadow);
		t1.start();
	}
}
