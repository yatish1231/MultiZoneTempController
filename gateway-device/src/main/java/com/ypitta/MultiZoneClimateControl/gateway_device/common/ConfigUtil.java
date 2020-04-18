package com.ypitta.MultiZoneClimateControl.gateway_device.common;
import org.ini4j.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
/*
 * 	This class is to parse configuration files
    loadconfig() method loads the configuration file into a local variable
    getValue() method takes in section, key parameters and returns the value
 */
public class ConfigUtil {
	
	private static String path = "config\\gatewayConfig.props";
	private Wini ini;
	public ConfigUtil() {
		super();
		this.loadConfig(ConfigUtil.path);
		// TODO Auto-generated constructor stub
	}
	
	public boolean loadConfig(String path) {
		try {
			this.ini = new Wini(new File(path));
			return true;
			
		} catch (InvalidFileFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} 
	}
	public String getValue(String section, String key){
		return this.ini.get(section,key);
	}
	public Boolean hasConfig() {
		return !this.ini.isEmpty();
	}
	public Boolean hasProperty(String obj) {
		try {
			this.ini.get(obj);
			return true;
		}
		catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		}
	public static void main(String[] args) {
		ConfigUtil obj = new ConfigUtil();
		System.out.println(obj.getValue("smtp.cloud", "host"));
	}
}
