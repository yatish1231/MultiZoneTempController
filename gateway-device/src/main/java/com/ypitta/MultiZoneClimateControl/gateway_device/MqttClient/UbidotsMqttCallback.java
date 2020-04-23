package com.ypitta.MultiZoneClimateControl.gateway_device.MqttClient;

import java.util.logging.Logger;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.ypitta.MultiZoneClimateControl.gateway_device.common.*;

public class UbidotsMqttCallback implements MqttCallback{
	
	private AWSUpdateThingShadow thingShowConn;
	private DataUtil util;
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	public UbidotsMqttCallback(AWSUpdateThingShadow shadow) {
		super();
		this.util = new DataUtil();
		this.thingShowConn = shadow;
	}
	

	public UbidotsMqttCallback() {
		super();
		// TODO Auto-generated constructor stub
	}


	public void connectionLost(Throwable cause) {
		cause.printStackTrace();
		LOGGER.info("Connection lost with ubidots");
		
	}
	
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		// TODO Auto-generated method stub
		String rcv_msg = new String(message.getPayload());
		LOGGER.info("recieved data :\n" + rcv_msg);
		UbidotsDataReceived data = this.util.toUbidotsReceivedFromJson(rcv_msg);
		LOGGER.info("POST data to shadow...");
		this.thingShowConn.postDataToShadow(data.getValue());
	}

	public void deliveryComplete(IMqttDeliveryToken token) {
		
		
	}

}
