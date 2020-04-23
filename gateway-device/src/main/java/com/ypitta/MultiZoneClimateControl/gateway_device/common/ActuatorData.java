package com.ypitta.MultiZoneClimateControl.gateway_device.common;

import java.time.LocalDateTime;

public class ActuatorData {
	
	private String name;
	private String command;
	private String zoneId;
	private LocalDateTime timestamp;
	private double reportedTemp;
	public String getName() {
		return name;
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getZoneId() {
		return zoneId;
	}
	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	public double getReportedTemp() {
		return reportedTemp;
	}
	public void setReportedTemp(double reportedTemp) {
		this.reportedTemp = reportedTemp;
	}
	
	
}
