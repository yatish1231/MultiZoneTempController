package com.ypitta.MultiZoneClimateControl.gateway_device.MqttClient;

import java.util.logging.Logger;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

public class MqttClientSubscriber implements Runnable {

	// MqttClient that has been initialized and connected to Mqtt broker
	private MqttClient connectedClient;

	private String topic = null;

	private int qos;

	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public MqttClientSubscriber(MqttClient connectedClient, String topic, int qos) {
		super();
		this.connectedClient = connectedClient;
		this.topic = topic;
		this.qos = qos;
	}

	private void subsribeToTopic(String topic, int qos) {
		try {
			if (!this.connectedClient.isConnected()) {
				this.connectedClient.connect();
			}
			LOGGER.info("\tSubscribing client" + this.connectedClient.getClientId() + "on topic...." + topic);
			this.connectedClient.subscribe(topic, qos);
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		this.subsribeToTopic(this.topic, this.qos);
	}
}
