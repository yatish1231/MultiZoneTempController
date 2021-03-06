package com.ypitta.MultiZoneClimateControl.gateway_device.service;

import java.util.logging.Logger;

import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

import com.amazonaws.services.iot.client.AWSIotException;
import com.amazonaws.services.iot.client.AWSIotMqttClient;
import com.amazonaws.services.iot.client.AWSIotQos;
import com.ypitta.MultiZoneClimateControl.gateway_device.MqttClient.AWSIoTMqttClientSecure;
import com.ypitta.MultiZoneClimateControl.gateway_device.MqttClient.AWSTopicSubscriber;
import com.ypitta.MultiZoneClimateControl.gateway_device.MqttClient.MqttClientConnector;
import com.ypitta.MultiZoneClimateControl.gateway_device.callBack.AWSTopicCallBack;
import com.ypitta.MultiZoneClimateControl.gateway_device.common.ConfigUtil;

public class AWSSubscriberGenerator {
	
	private ConfigUtil util;
	
	private String endpoint;
	
	private String certificateFile;
	
	private String privateKeyFile;
	
	private String zoneId;
		
	private MqttClientConnector pub_constrained;
	
	private AWSIotQos aws_qos;
	
	private AWSTopicSubscriber aws_sub = null;

	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	public AWSSubscriberGenerator(String zoneId, AWSIotQos aws_qos) {
		super();
		this.zoneId = zoneId;
		this.aws_qos = aws_qos;
		this.util = new ConfigUtil();
	}
	
	private void instantiate() {
		
		this.pub_constrained = new MqttClientConnector(MqttAsyncClient.generateClientId(), this.util.getValue("mqtt.cloud", "host"));
		this.pub_constrained.connectMqttClient();
		
		String topic_pub = "action/gateway/"+this.zoneId;
		this.endpoint = this.util.getValue("aws.cloud", "endpoint");
		this.certificateFile = this.util.getValue("aws.cloud", "certificateFile");
		this.privateKeyFile = this.util.getValue("aws.cloud", "privateKeyFile");
		
		AWSIoTMqttClientSecure clientConn = new AWSIoTMqttClientSecure(this.endpoint, zoneId+"AwsSub" ,this.certificateFile , this.privateKeyFile);
		
		try {
			clientConn.getClientInstance().connect();
		} catch (AWSIotException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.aws_sub = new AWSTopicSubscriber(clientConn, new AWSTopicCallBack("action/aws/"+this.zoneId, this.aws_qos, this.pub_constrained,topic_pub));
		
	}
	
	public AWSTopicSubscriber getInstance() {
		if(this.aws_sub != null) {
			return this.aws_sub;
		}
		instantiate();
		return this.aws_sub;
	}
		
}
