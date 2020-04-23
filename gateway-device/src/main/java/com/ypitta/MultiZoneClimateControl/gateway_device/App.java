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
		Zone zone4 = new Zone("Hall2",AWSIotQos.QOS1);
		Zone zone5 = new Zone("Bedroom2",AWSIotQos.QOS1);
		Zone zone6 = new Zone("Studyroom2",AWSIotQos.QOS1);
		Thread t1 = new Thread(zone);
		Thread t2 = new Thread(zone2);
		Thread t3 = new Thread(zone3);
		Thread t4 = new Thread(zone4);
		Thread t5 = new Thread(zone5);
		Thread t6 = new Thread(zone6);
//		t1.start();
//		t2.start();
//		t3.start();
		t4.start();
		t5.start();
		t6.start();
	}
	
}
