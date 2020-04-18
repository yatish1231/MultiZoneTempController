package com.ypitta.MultiZoneClimateControl.gateway_device;

import com.amazonaws.services.iot.client.AWSIotQos;

/**
 * Hello world!
 *
 */
public class App 
{
 
	
	public static void main(String[] args) {
		
		System.setProperty("java.util.logging.SimpleFormatter.format","%1$tF %1$tT %4$s %2$s %5$s%6$s%n");
		Zone zone = new Zone("testZone1", AWSIotQos.QOS1);
		Zone zone2 = new Zone("testZone3", AWSIotQos.QOS1);
		Zone zone3 = new Zone("testZone4", AWSIotQos.QOS1);
		Thread t1 = new Thread(zone);
		Thread t2 = new Thread(zone2);
		Thread t3 = new Thread(zone3);
		t1.start();
		t2.start();
		t3.start();
	}
	
}
