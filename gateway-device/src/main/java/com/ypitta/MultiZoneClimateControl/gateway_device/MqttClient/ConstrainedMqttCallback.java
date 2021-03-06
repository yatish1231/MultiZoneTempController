package com.ypitta.MultiZoneClimateControl.gateway_device.MqttClient;

import java.time.LocalDateTime;
import java.util.logging.Logger;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import com.ypitta.MultiZoneClimateControl.gateway_device.common.SensorData;
import com.amazonaws.services.iot.client.AWSIotException;
import com.amazonaws.services.iot.client.AWSIotQos;
import com.ypitta.MultiZoneClimateControl.gateway_device.callBack.AWSIoTMessageImpl;
import com.ypitta.MultiZoneClimateControl.gateway_device.common.AWSSensorData;
import com.ypitta.MultiZoneClimateControl.gateway_device.common.DataUtil;

public class ConstrainedMqttCallback implements MqttCallback{

	private AWSIoTMqttClientSecure publisher;
	
	private String publisher_topic;
	
	private DataUtil util;
	
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	public ConstrainedMqttCallback(AWSIoTMqttClientSecure publisher, String pub_topic) {
		super();
		this.publisher = publisher;
		this.publisher_topic = pub_topic;
		this.util = new DataUtil();
	}

	@Override
	public void connectionLost(Throwable cause) {
		LOGGER.info("\tMqtt connection lost with constrained device : " + this.publisher_topic);
		
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		
		String arrived = new String(message.getPayload());
		LOGGER.info("\tMqtt message arrived from: "+ topic+"\tmessage: "+ arrived);
		SensorData data = this.util.toSensorDataFromJsonFile(arrived);
		AWSSensorData new_data = new AWSSensorData(data.getCurValue());
		new_data.setTimestamp(LocalDateTime.now());
		String payload = this.util.toJsonfromAWSSensorData(new_data);
		AWSIoTMessageImpl iot_msg = new AWSIoTMessageImpl(this.publisher_topic, AWSIotQos.QOS0, payload);
		LOGGER.info("\tPublishing to AWS MQTT on topic..."+ iot_msg.getTopic() + "\tMessage : "+ iot_msg.getStringPayload());
		try {
			
			this.publisher.getClientInstance().publish(iot_msg,1000);
			
		}catch(AWSIotException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		// TODO Auto-generated method stub
		
	}

}
