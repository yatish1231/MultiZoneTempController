package com.ypitta.MultiZoneClimateControl.gateway_device.common;

public class UbidotsDataReceived {
	
	private double value;
	
	private long timestamp;
	
	private Object context;
	
	private long created_at;

	
	public UbidotsDataReceived() {
		super();
		// TODO Auto-generated constructor stub
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}


	public Object getContext() {
		return context;
	}

	public void setContext(Object context) {
		this.context = context;
	}

	public long getCreated_at() {
		return created_at;
	}

	public void setCreated_at(long created_at) {
		this.created_at = created_at;
	}
	
	
}
