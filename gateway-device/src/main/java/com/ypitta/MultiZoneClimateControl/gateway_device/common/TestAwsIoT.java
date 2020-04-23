package com.ypitta.MultiZoneClimateControl.gateway_device.common;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

import com.amazonaws.services.iot.client.AWSIotException;
import com.amazonaws.services.iot.client.AWSIotMessage;
import com.amazonaws.services.iot.client.AWSIotMqttClient;
import com.amazonaws.services.iot.client.AWSIotQos;
import com.fasterxml.jackson.core.StreamWriteFeature;
import com.ypitta.MultiZoneClimateControl.gateway_device.callBack.TempIoTDevice;
import com.ypitta.MultiZoneClimateControl.gateway_device.common.SampleUtil.KeyStorePasswordPair;
public class TestAwsIoT {
	
	
	public void start(){
		/**
	String clientEndpoint = "a2k03cghbd2dw4-ats.iot.us-east-1.amazonaws.com";       // replace <prefix> and <region> with your own
	String clientId = "livingRoom";                              // replace with your own client ID. Use unique client IDs for concurrent connections.
	String certificateFile = "configKeys/0a92f0a33d-certificate.pem.crt";                       // X.509 based certificate file
	String privateKeyFile = "configKeys/0a92f0a33d-private.pem.key";                        // PKCS#1 or PKCS#8 PEM encoded private key file
	KeyStorePasswordPair pair = SampleUtil.getKeyStorePasswordPair(certificateFile, privateKeyFile);
	AWSIotMqttClient client = new AWSIotMqttClient(clientEndpoint, clientId, pair.keyStore, pair.keyPassword);
	
	try {
	client.connect();
		String payload = "{\"temp\" :12}";
		client.publish(new AWSIotMessage("update/temperature/testZone2", AWSIotQos.QOS0, payload.getBytes()));
		client.publish(new AWSIotMessage("update/temperature/testZone1", AWSIotQos.QOS0, payload.getBytes()));
	} catch (AWSIotException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	
	TempIoTDevice device = new TempIoTDevice("arn:aws:iot:us-east-1:582706166291:thing/Hall2");
	long reportinterval = 1000;
	device.setReportInterval(reportinterval);
	SSLSocketFactory socket = CertManagementUtil.getInstance().loadCertificate("configKeys/a6efa0c331.pem.p12");
	URL url;
	HttpsURLConnection.setDefaultSSLSocketFactory(socket);
	try {
		url = new URL("https://arn:aws:iot:us-east-1:582706166291:8443/things/Hall2/shadow");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		
		connection.setRequestMethod("POST");
		connection.setDoOutput(true);
		PrintStream stream = new PrintStream(connection.getOutputStream());
		String msg = "{\r\n" + 
				"    \"state\": {\r\n" + 
				"        \"reported\" : {\r\n" + 
				"            \"Current_Value\" : { \"r\" :255, \"g\": 255, \"b\": 0 }    \r\n" + 
				"        }\r\n" + 
				"    }\r\n" + 
				"}";
		stream.print(msg);
		connection.connect();
		if (connection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
		BufferedReader br = new BufferedReader(new
		InputStreamReader(connection.getInputStream()));
		String line;
		while((line = br.readLine()) != null) {
		System.out.println(line);
		}
		br.close();
		}
		connection.disconnect();
		
	} catch (MalformedURLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	*/
	
		try {
			KeyStore ks = KeyStore.getInstance("PKCS12");
			FileInputStream fis = new FileInputStream("configKeys/Studyroom2.pfx");
			ks.load(fis, "1231".toCharArray());
			KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			kmf.init(ks, "1231".toCharArray());
			SSLContext sc = SSLContext.getInstance("TLS");
			sc.init(kmf.getKeyManagers(), null, null);
			URL url = new URL("https://a2k03cghbd2dw4-ats.iot.us-east-1.amazonaws.com:8443/things/Studyroom2/shadow");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			if (connection instanceof HttpsURLConnection) {
			    ((HttpsURLConnection)connection)
			         .setSSLSocketFactory(sc.getSocketFactory());
			}
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			PrintStream stream = new PrintStream(connection.getOutputStream());
			String msg = "{\"state\": {\"reported\" : {\"Current_Value\" :" + 23 + "} } }";
			stream.print(msg);
			connection.connect();
			if (connection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
			BufferedReader br = new BufferedReader(new
			InputStreamReader(connection.getInputStream()));
			String line;
			while((line = br.readLine()) != null) {
			System.out.println(line);
			}
			br.close();
			}
			connection.disconnect();
			
		} catch (UnrecoverableKeyException | KeyStoreException | NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		 catch (KeyManagementException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TestAwsIoT obj = new TestAwsIoT();
		obj.start();
	}

}
