package com.ypitta.MultiZoneClimateControl.gateway_device.common;
import java.time.*;
/*
 *  Class sensor data stores the sensor data.
    addValue() method takes in a values and adds the value to the previously stored value and calculates the average
 */

public class SensorData {

    private String timeStamp;
    private String name = "not set";
    private int sampleCount = 0;
    private double curValue = 0;
    private double totValue = 0;
    private double minValue = 0;
    private double maxValue = 0;
    private double avgValue = 0;
    
    
    
	public SensorData() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    public void addValue(Double newValue){
    	
    	this.sampleCount += 1;
    	this.timeStamp = String.valueOf(LocalDateTime.now());
    	this.curValue = newValue;
    	this.totValue += newValue;
    	
    	if(this.minValue>this.curValue) {
    		this.minValue = this.curValue;
    	}
    	
    	if(this.maxValue<this.curValue) {
    		this.maxValue = this.curValue;
    	}
    	
    	if(this.sampleCount>0 && this.totValue!=0) {
    		this.avgValue = this.totValue / this.sampleCount;
    	}
    	
    }
    
    public double getCurValue() {
    	return this.curValue;
    }
    public double getAvgValue() {
    	return this.avgValue;
    }
    public double getTotValue() {
    	return this.totValue;
    }
    public double getMaxValue() {
    	return this.maxValue;
    }
    public double getMinValue() {
    	return this.minValue;
    }
    public int getCount() {
    	return this.sampleCount;
    }
    public String getTimeStamp() {
    	return this.timeStamp;
    }
    public void setName(String name) {
    	this.name = name;
    }
    public String getName() {
    	return this.name;
    }
}
