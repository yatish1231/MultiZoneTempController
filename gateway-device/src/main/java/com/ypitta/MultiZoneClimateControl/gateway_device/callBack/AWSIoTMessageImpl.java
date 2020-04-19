package com.ypitta.MultiZoneClimateControl.gateway_device.callBack;

import java.util.logging.Logger;

import com.amazonaws.services.iot.client.AWSIotDeviceErrorCode;
import com.amazonaws.services.iot.client.AWSIotMessage;
import com.amazonaws.services.iot.client.AWSIotQos;

public class AWSIoTMessageImpl extends AWSIotMessage{
	
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	public AWSIoTMessageImpl(String topic, AWSIotQos qos, byte[] payload) {
		super(topic, qos, payload);
		// TODO Auto-generated constructor stub
	}

	public AWSIoTMessageImpl(String topic, AWSIotQos qos, String payload) {
		super(topic, qos, payload);
		// TODO Auto-generated constructor stub
	}

	public AWSIoTMessageImpl(String topic, AWSIotQos qos) {
		super(topic, qos);
		// TODO Auto-generated constructor stub
	}

	@Override
	public byte[] getPayload() {
		// TODO Auto-generated method stub
		return super.getPayload();
	}

	@Override
	public void setPayload(byte[] payload) {
		// TODO Auto-generated method stub
		super.setPayload(payload);
	}

	@Override
	public String getStringPayload() {
		// TODO Auto-generated method stub
		return super.getStringPayload();
	}

	@Override
	public void setStringPayload(String payload) {
		// TODO Auto-generated method stub
		super.setStringPayload(payload);
	}

	@Override
	public void onSuccess() {
		// TODO Auto-generated method stub
		LOGGER.info("Successfully published to AWS mqtt on topic..."+ getTopic());
	}

	@Override
	public void onFailure() {
		// TODO Auto-generated method stub
		LOGGER.info("Failed to published to AWS mqtt on topic..."+ getTopic() + "\terror message: "+ getErrorMessage());
	}

	@Override
	public void onTimeout() {
		// TODO Auto-generated method stub
		super.onTimeout();
	}

	@Override
	public String getTopic() {
		// TODO Auto-generated method stub
		return super.getTopic();
	}

	@Override
	public void setTopic(String topic) {
		// TODO Auto-generated method stub
		super.setTopic(topic);
	}

	@Override
	public AWSIotQos getQos() {
		// TODO Auto-generated method stub
		return super.getQos();
	}

	@Override
	public void setQos(AWSIotQos qos) {
		// TODO Auto-generated method stub
		super.setQos(qos);
	}

	@Override
	public AWSIotDeviceErrorCode getErrorCode() {
		// TODO Auto-generated method stub
		return super.getErrorCode();
	}

	@Override
	public void setErrorCode(AWSIotDeviceErrorCode errorCode) {
		// TODO Auto-generated method stub
		super.setErrorCode(errorCode);
	}

	@Override
	public String getErrorMessage() {
		// TODO Auto-generated method stub
		return super.getErrorMessage();
	}

	@Override
	public void setErrorMessage(String errorMessage) {
		// TODO Auto-generated method stub
		super.setErrorMessage(errorMessage);
	}

	
}
