package com.ypitta.MultiZoneClimateControl.gateway_device.common;

import java.time.LocalDateTime;

public class AWSActuatorData {
	
	private String mode;
	
	private String action;
	
	private String zoneId;
	
	private Long timestamp;
	
	private double reportedTemp;
	
	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public AWSActuatorData() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getZoneId() {
		return zoneId;
	}

	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public double getReportedTemp() {
		return reportedTemp;
	}

	public void setReportedTemp(double reportedTemp) {
		this.reportedTemp = reportedTemp;
	}


	
	
	
}
