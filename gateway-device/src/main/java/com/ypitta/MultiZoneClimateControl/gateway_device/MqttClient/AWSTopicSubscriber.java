package com.ypitta.MultiZoneClimateControl.gateway_device.MqttClient;

import java.util.logging.Logger;

import com.amazonaws.services.iot.client.AWSIotException;
import com.ypitta.MultiZoneClimateControl.gateway_device.callBack.AWSTopicCallBack;

public class AWSTopicSubscriber implements Runnable{
	
	private AWSIoTMqttClientSecure secureClient;
	
	private AWSTopicCallBack topic;
	
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	public AWSTopicSubscriber(AWSIoTMqttClientSecure secureClient, AWSTopicCallBack topic) {
		super();
		this.secureClient = secureClient;
		this.topic = topic;
	}

	public void subscribeToAWSTopic() {
		if(secureClient != null && topic != null) {
			try {
				LOGGER.info("\tSubscribing client: "+ this.secureClient.getClientInstance().getClientId() + "on topic..."+ this.topic.getTopic());
				this.secureClient.getClientInstance().subscribe(this.topic);
			} catch (AWSIotException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		this.subscribeToAWSTopic();
	}

	public AWSIoTMqttClientSecure getSecureClient() {
		return secureClient;
	}

	public void setSecureClient(AWSIoTMqttClientSecure secureClient) {
		this.secureClient = secureClient;
	}

	public AWSTopicCallBack getTopic() {
		return topic;
	}

	public void setTopic(AWSTopicCallBack topic) {
		this.topic = topic;
	}
	
	
	
}
