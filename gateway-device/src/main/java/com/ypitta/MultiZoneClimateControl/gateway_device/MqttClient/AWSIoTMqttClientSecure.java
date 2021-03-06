package com.ypitta.MultiZoneClimateControl.gateway_device.MqttClient;

import com.amazonaws.services.iot.client.AWSIotMqttClient;
import com.ypitta.MultiZoneClimateControl.gateway_device.common.SampleUtil;
import com.ypitta.MultiZoneClimateControl.gateway_device.common.SampleUtil.KeyStorePasswordPair;

/**
 * @author Yatish Pitta
 * 
 */
public class AWSIoTMqttClientSecure {
	
	
	private String clientEndpoint = "";       					// AWS IoT client end point
	private String clientId = "";                              // replace with your own client ID. Use unique client IDs for concurrent connections.
	private String certificateFile = "configKeys/0a92f0a33d-certificate.pem.crt";   // X.509 based certificate file
	private String privateKeyFile = "configKeys/0a92f0a33d-private.pem.key";       // Path to private key file
	private AWSIotMqttClient awsMqttClient = null;
	/**
	 * Constructor using client end point and client Id
	 * @param clientEndpoint
	 * @param clientId
	 */
	public AWSIoTMqttClientSecure(String clientEndpoint, String clientId) {
		super();
		this.clientEndpoint = clientEndpoint;
		this.clientId = clientId;
	}
	
	/**
	 * Constructor with certificate file path and private key file path
	 * @param clientEndpoint
	 * @param clientId
	 * @param certificateFile
	 * @param privateKeyFile
	 */
	public AWSIoTMqttClientSecure(String clientEndpoint, String clientId, String certificateFile,
			String privateKeyFile) {
		super();
		this.clientEndpoint = clientEndpoint;
		this.clientId = clientId;
		this.certificateFile = certificateFile;
		this.privateKeyFile = privateKeyFile;
	}

	public AWSIoTMqttClientSecure() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public AWSIotMqttClient getClientInstance() {
		
		if(this.awsMqttClient != null) {
			return this.awsMqttClient;
		}
		KeyStorePasswordPair pair = SampleUtil.getKeyStorePasswordPair(certificateFile, privateKeyFile);
		this.awsMqttClient = new AWSIotMqttClient(clientEndpoint, clientId, pair.keyStore, pair.keyPassword);
		return this.awsMqttClient;
	}
	
	public String getClientEndpoint() {
		return clientEndpoint;
	}

	public void setClientEndpoint(String clientEndpoint) {
		this.clientEndpoint = clientEndpoint;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getCertificateFile() {
		return certificateFile;
	}

	public void setCertificateFile(String certificateFile) {
		this.certificateFile = certificateFile;
	}

	public String getPrivateKeyFile() {
		return privateKeyFile;
	}

	public void setPrivateKeyFile(String privateKeyFile) {
		this.privateKeyFile = privateKeyFile;
	}  
	
	
	
}
