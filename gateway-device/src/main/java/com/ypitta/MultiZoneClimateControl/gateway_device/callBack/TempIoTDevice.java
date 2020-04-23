package com.ypitta.MultiZoneClimateControl.gateway_device.callBack;

import com.amazonaws.services.iot.client.AWSIotDevice;
import com.amazonaws.services.iot.client.AWSIotDeviceProperty;

public class TempIoTDevice extends AWSIotDevice{

	public TempIoTDevice(String thingName) {
		super(thingName);
		// TODO Auto-generated constructor stub
	}
	@AWSIotDeviceProperty
	private String Current_Value;

	public String getCurrent_Value() {
		return Current_Value;
	}

	public void setCurrent_Value(String current_Value) {
		Current_Value = current_Value;
	}
	
	
}
