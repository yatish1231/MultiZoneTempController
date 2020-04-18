package com.ypitta.MultiZoneClimateControl.gateway_device.common;

import com.google.gson.*;
public class DataUtil {
	/*
	 * This class is responsible for handling the conversion of SensorData and ActuatorData object to and from JSON.
	 */
	private Gson conv;
	public DataUtil() {
		
		super();
		this.conv= new Gson();
		// TODO Auto-generated constructor stub
	}
	
	public String toUbidotsJsonfromSensorData(SensorData data) {
		UbidotsData ubi = new UbidotsData(data);
		String sen =  this.conv.toJson(ubi);
		return sen;
	}
	
	public AWSSensorData toAWSSensorDatafromJson(String sen_data) {
		
		return this.conv.fromJson(sen_data, AWSSensorData.class);
	}
	
	public String toJsonfromAWSSensorData(AWSSensorData data) {
		return this.conv.toJson(data);
	}
	
	public AWSActuatorData toAWSActuatorDatafromJson(String act_data) {
		return this.conv.fromJson(act_data, AWSActuatorData.class);
		
	}
	
	public SensorData toSensorDataFromJsonFile(String sen_Data) {
	
		return this.conv.fromJson(sen_Data, SensorData.class);
	}
	
	public ActuatorData toActuatorDataFromJsonFile(String act_data) {
		
		return this.conv.fromJson(act_data, ActuatorData.class);
	}
	
	public String JsonFromSensorData(SensorData sen) {
		return this.conv.toJson(sen);
	}
	
	public String JsonFromActuatorData(ActuatorData act) {
		return this.conv.toJson(act);
	}
	
	public class UbidotsData{
		
		private String timestamp;
		private double value;
		
		public UbidotsData() {
			
		}
		public UbidotsData(SensorData data) {
			this.timestamp = data.getTimeStamp();
			this.value  = data.getCurValue();
		}
		public String getTimeStamp() {
			return timestamp;
		}
		public void setTimeStamp(String timeStamp) {
			this.timestamp = timeStamp;
		}
		public double getCurValue() {
			return value;
		}
		public void setCurValue(double curValue) {
			this.value = curValue;
		}
		
	}
}
