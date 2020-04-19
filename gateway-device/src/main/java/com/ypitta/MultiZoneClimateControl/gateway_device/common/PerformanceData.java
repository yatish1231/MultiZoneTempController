package com.ypitta.MultiZoneClimateControl.gateway_device.common;

public class PerformanceData {
	
	private double cpu;
	
	private double memory;

	public PerformanceData() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public PerformanceData(double cpu, double memory) {
		super();
		this.cpu = cpu;
		this.memory = memory;
	}


	public double getCpu() {
		return cpu;
	}

	public void setCpu(double cpu) {
		this.cpu = cpu;
	}

	public double getMemory() {
		return memory;
	}

	public void setMemory(double memory) {
		this.memory = memory;
	}
	
	
}
