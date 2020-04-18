package com.ypitta.MultiZoneClimateControl.gateway_device.service;

import java.util.logging.Logger;

import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

import com.amazonaws.services.iot.client.AWSIotException;
import com.ypitta.MultiZoneClimateControl.gateway_device.MqttClient.AWSIoTMqttClientSecure;
import com.ypitta.MultiZoneClimateControl.gateway_device.MqttClient.ConstrainedMqttCallback;
import com.ypitta.MultiZoneClimateControl.gateway_device.MqttClient.MqttClientConnector;
import com.ypitta.MultiZoneClimateControl.gateway_device.MqttClient.MqttClientSubscriber;
import com.ypitta.MultiZoneClimateControl.gateway_device.common.ConfigUtil;

public class GatewaySubscriberGenerator {
	
	private String zoneId;
	
	private AWSIoTMqttClientSecure aws_pub;
	
	private String endpoint;
	
	private String certificateFile;
	
	private String privateKeyFile;
	
	private MqttClientSubscriber sub_constrained;
	
	private ConfigUtil util;

	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	public GatewaySubscriberGenerator(String zoneId) {
		super();
		this.zoneId = zoneId;
		this.util = new ConfigUtil();
	}
	
	private void instantiate() {
		
		this.endpoint = this.util.getValue("aws.cloud", "endpoint");
		this.certificateFile = this.util.getValue("aws.cloud", "certificateFile");
		this.privateKeyFile = this.util.getValue("aws.cloud", "privateKeyFile");
		
		this.aws_pub = new AWSIoTMqttClientSecure(this.endpoint, this.zoneId+"AwsPub", this.certificateFile, this.privateKeyFile);
		try {
			this.aws_pub.getClientInstance().connect();
		} catch (AWSIotException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MqttClientConnector conn = new MqttClientConnector(MqttAsyncClient.generateClientId(), this.util.getValue("mqtt.cloud", "host"));
		
		conn.ConnectMqttClientSetCallBack(new ConstrainedMqttCallback(this.aws_pub, "update/temperature/"+zoneId));
		
		this.sub_constrained = new MqttClientSubscriber(conn.getClient(), "data/constrained/"+this.zoneId, Integer.valueOf(this.util.getValue("mqtt.cloud", "defaultQos")));
		
	}
	
	public MqttClientSubscriber getInstance() {
		
		if(this.sub_constrained != null) {
			return this.sub_constrained;
		}
		instantiate();
		return this.sub_constrained;
	}
}
