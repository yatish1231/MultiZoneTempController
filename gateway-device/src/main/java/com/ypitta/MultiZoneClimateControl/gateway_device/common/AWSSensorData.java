package com.ypitta.MultiZoneClimateControl.gateway_device.common;

import java.time.LocalDateTime;

public class AWSSensorData {
	
	private double temp;

	private LocalDateTime timestamp;
	
	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public AWSSensorData() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AWSSensorData(double temp) {
		super();
		this.temp = temp;
	}


	public double getTemp() {
		return temp;
	}

	public void setTemp(double temp) {
		this.temp = temp;
	}
	
	
	
}
