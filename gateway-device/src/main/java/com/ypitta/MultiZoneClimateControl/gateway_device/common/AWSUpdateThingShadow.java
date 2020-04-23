package com.ypitta.MultiZoneClimateControl.gateway_device.common;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.logging.Logger;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;

public class AWSUpdateThingShadow {

	private HttpURLConnection connection;

	private String zoneId;

	private String keyfile;

	private String password;

	private String endpoint = "https://a2k03cghbd2dw4-ats.iot.us-east-1.amazonaws.com:8443";

	private String shadowEndpoint;

	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	private SSLContext sslFactory;
	public AWSUpdateThingShadow(String zoneId, String endpoint) {
		super();
		this.zoneId = zoneId;
		this.keyfile = "configKeys/" + zoneId + ".pfx";
		this.password = "1231";
		this.endpoint = endpoint;
		this.shadowEndpoint = this.endpoint + "/things/" + this.zoneId + "/shadow";
	}

	/**
	 * end point
	 * "https://a2k03cghbd2dw4-ats.iot.us-east-1.amazonaws.com:8443/things/Hall2/shadow"
	 */

	public void initialize() {

		try {
			LOGGER.info("Initializing HTTPS Connection with endpoint: "+this.shadowEndpoint);
			KeyStore ks = KeyStore.getInstance("PKCS12");
			FileInputStream fis = new FileInputStream(this.keyfile);
			ks.load(fis, this.password.toCharArray());
			KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			kmf.init(ks, this.password.toCharArray());
			SSLContext sc = SSLContext.getInstance("TLS");
			sc.init(kmf.getKeyManagers(), null, null);
			this.sslFactory = sc;
			LOGGER.info("Successfully instantiated HttpsURLConnection with client: "+ this.shadowEndpoint);
			

		}catch (IOException e) {
			e.printStackTrace();
		} 
		catch (UnrecoverableKeyException | KeyStoreException | NoSuchAlgorithmException e1) {
			
			e1.printStackTrace();
		}

		catch (KeyManagementException e1) {
			
			e1.printStackTrace();
		} catch (CertificateException e) {
			
			e.printStackTrace();
		}
	}

	public void postDataToShadow(double data) {
		try {
			LOGGER.info("POST Value: "+data+" to shadow" + this.shadowEndpoint);
			URL url = new URL(this.shadowEndpoint);
			this.connection = (HttpURLConnection) url.openConnection();
			if (this.connection instanceof HttpsURLConnection) {
				((HttpsURLConnection) this.connection).setSSLSocketFactory(this.sslFactory.getSocketFactory());
			}
			this.connection.setRequestMethod("POST");
			this.connection.setDoOutput(true);
			PrintStream stream = new PrintStream(this.connection.getOutputStream());
			String msg = "{\"state\": {\"reported\" : {\"Current_Value\" :" + data + "} } }";
			stream.print(msg);
			this.connection.connect();
			if (this.connection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader(this.connection.getInputStream()));
				String line;
				while ((line = br.readLine()) != null) {
					LOGGER.info("POST Response: " + line);
				}
				br.close();
			}
			

		} catch (Exception e) {
			LOGGER.info("Failed to POST data");
			e.printStackTrace();
		}finally {
			this.connection.disconnect();
		}

	}

	public String getZoneId() {
		return zoneId;
	}

	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}

	public String getKeyfile() {
		return keyfile;
	}

	public void setKeyfile(String keyfile) {
		this.keyfile = keyfile;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	

}
