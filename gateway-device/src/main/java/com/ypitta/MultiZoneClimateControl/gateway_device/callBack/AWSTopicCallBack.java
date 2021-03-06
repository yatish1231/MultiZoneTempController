package com.ypitta.MultiZoneClimateControl.gateway_device.callBack;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;

import com.amazonaws.services.iot.client.AWSIotMessage;
import com.amazonaws.services.iot.client.AWSIotQos;
import com.amazonaws.services.iot.client.AWSIotTopic;
import com.ypitta.MultiZoneClimateControl.gateway_device.MqttClient.MqttClientConnector;
import com.ypitta.MultiZoneClimateControl.gateway_device.common.AWSActuatorData;
import com.ypitta.MultiZoneClimateControl.gateway_device.common.ActuatorData;
import com.ypitta.MultiZoneClimateControl.gateway_device.common.ConfigUtil;
import com.ypitta.MultiZoneClimateControl.gateway_device.common.DataUtil;
import com.ypitta.MultiZoneClimateControl.gateway_device.service.SmtpClientConnector;

public class AWSTopicCallBack extends AWSIotTopic{
	
	private MqttClientConnector publisher;
	
	private DataUtil util;
	
	private ConfigUtil util_config;
	
	private String topic_publisher;
	
	private int qos;
	
	private SmtpClientConnector smtp_client;
	
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	public AWSTopicCallBack(String topic, AWSIotQos qos, MqttClientConnector publisher,String pub_topic) {
		super(topic, qos);
		this.publisher = publisher;
		this.topic_publisher = pub_topic;
		this.util = new DataUtil();
		this.util_config = new ConfigUtil();
		this.qos = Integer.valueOf(this.util_config.getValue("mqtt.cloud", "defaultQos"));
		this.smtp_client = new SmtpClientConnector();
	}

	@Override
	public void onMessage(AWSIotMessage message) {
		
		try {
		String payload = new String(message.getPayload());
		LOGGER.info("\tRecieved message from AWS....message " + payload);
		AWSActuatorData data = this.util.toAWSActuatorDatafromJson(payload);
		ActuatorData new_data = new ActuatorData();
		new_data.setCommand(data.getAction());
		new_data.setName(data.getMode());
		LocalDateTime date = LocalDateTime.ofInstant(Instant.ofEpochMilli(data.getTimestamp()), ZoneId.systemDefault());
		new_data.setTimestamp(date);
		new_data.setZoneId(data.getZoneId());
		new_data.setReportedTemp(data.getReportedTemp());
		String msg = this.util.JsonFromActuatorData(new_data);
		
		//sending actuation to constrained device
		if(!this.publisher.getClient().isConnected()) {
			LOGGER.info("\tConnecting to Mqtt client for published to constriained device....");
			this.publisher.connectMqttClient();
		}
		LOGGER.info("\tPublishing to topic...."+this.topic_publisher+"\tmessage"+msg);
		this.publisher.getClient().publish(this.topic_publisher, msg.getBytes(), this.qos, false);
		
		//Sending email
		StringBuilder email_msg = new StringBuilder();
		email_msg.append("Time: "+ LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).toString());
		email_msg.append("\nHVAC system:" + data.getMode());
		email_msg.append("\nAction: "+ data.getAction());
		LOGGER.info("\tSending email now....");
		this.smtp_client.publishMessage("Actuation detected" ,email_msg.toString());
		
		} catch (MqttPersistenceException e) {

			e.printStackTrace();
		} catch (MqttException e) {
			
			e.printStackTrace();
		}
	}
	
	public MqttClientConnector getPublisher() {
		return publisher;
	}

	public void setPublisher(MqttClientConnector publisher) {
		this.publisher = publisher;
	}

	
}
