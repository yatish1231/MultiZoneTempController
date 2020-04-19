package com.ypitta.MultiZoneClimateControl.gateway_device.MqttClient;

import java.util.logging.Logger;

import javax.net.ssl.SSLSocketFactory;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttConnect;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import com.ypitta.MultiZoneClimateControl.gateway_device.common.SensorData;
import com.ypitta.MultiZoneClimateControl.gateway_device.common.DataUtil;
import com.ypitta.MultiZoneClimateControl.gateway_device.common.CertManagementUtil;

public class MqttClientConnector implements MqttCallback{
	/*
	 * This class can be used to create an Mqtt client instance. The constructor takes clientId and name of the server as parameters. 
	 */
	private int qos = 2;
	private MqttClient client ;
	private String id;
	private String pass;
	private String broker;
	private boolean ssl = false;
	private String filename;
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private MqttMessage rcvMsg;
	private boolean isConn;
	
	public MqttClientConnector(String id, String broker) {
		super();
		this.id = id;
		this.broker = broker;
		
	}
	
	public MqttClientConnector(String id, String broker, String pass, String filename) {
		this.ssl = true;
		this.id = id;
		this.broker = broker;
		this.filename = filename;
		this.pass = pass;
	}
	public int getQos() {
		return qos;
	}

	public void setQos(int qos) {
		this.qos = qos;
	}

	public MqttClient getClient() {
		return client;
	}

	public void setClient(MqttClient client) {
		this.client = client;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBroker() {
		return broker;
	}

	public void setBroker(String broker) {
		this.broker = broker;
	}

	
	public void connectMqttClient() {
		/*
		 * this method can be used to connect the Mqtt client to the server
		 */
		try {
			LOGGER.info("\tConnecting to MQTT broker.......");
			MqttConnectOptions options = new MqttConnectOptions();
			options.setCleanSession(true);
			this.client = new MqttClient(this.broker, this.id, new MemoryPersistence());
			this.client.setCallback(this);
			this.client.connect(options);
			
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void ConnectMqttClientSetCallBack(MqttCallback callback) {
		
		try {
			LOGGER.info("\tConnecting to MQTT broker.......");
			MqttConnectOptions options = new MqttConnectOptions();
			options.setKeepAliveInterval(15);
			options.setConnectionTimeout(30);
//			options.setAutomaticReconnect(true);
			options.setCleanSession(true);
			this.client = new MqttClient(this.broker, this.id, new MemoryPersistence());
			this.client.setCallback(callback);
			this.client.connect(options);
			
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void connectMqttClientOverSSL() {
		if(this.ssl) {
		try {
			SSLSocketFactory fact =  CertManagementUtil.getInstance().loadCertificate(this.filename);
			MqttConnectOptions options = new MqttConnectOptions();
			options.setUserName(this.pass);
			options.setSocketFactory(fact);
			options.setCleanSession(true);
			this.client = new MqttClient(this.broker, this.id, new MemoryPersistence());
			this.client.setCallback(this);
			this.client.connect(options);
			this.isConn = this.client.isConnected();
		}
		catch(Exception e) {
			e.printStackTrace();
			}
		}
		else {
			LOGGER.info("SSL flag false");
		}
	}
	
	
	public void ConnectMqttClientOverSSLSetCallBack(MqttCallback callback) {
		
		if(this.ssl) {
		try {
			SSLSocketFactory fact =  CertManagementUtil.getInstance().loadCertificate(this.filename);
			MqttConnectOptions options = new MqttConnectOptions();
			options.setUserName(this.pass);
			options.setSocketFactory(fact);
			options.setCleanSession(true);
			this.client = new MqttClient(this.broker, this.id, new MemoryPersistence());
			this.client.setCallback(callback);
			this.client.connect(options);
			this.isConn = this.client.isConnected();
		}
		catch(Exception e) {
			e.printStackTrace();
			}
		}
		else {
			LOGGER.info("SSL flag false");
		}
	}
	

	public boolean isConn() {
		return isConn;
	}
	
	
	public void setConn(boolean isConn) {
		this.isConn = isConn;
	}
	
	
	public void subscribe(String topic, int qos) {
		/*
		 * this method can be used to subscribe the Mqtt client to subscribe to topic
		 */
		this.qos = qos;
		try {
			LOGGER.info("\tSubscribing to MQTT topic :" + topic);
			this.client.subscribe(topic, this.qos);
			
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void connectionLost(Throwable cause) {
		// TODO Auto-generated method stub
		cause.printStackTrace();
	}

	public MqttMessage getRcvMsg() {
		return rcvMsg;
	}

	public void setRcvMsg(MqttMessage rcvMsg) {
		this.rcvMsg = rcvMsg;
	}
	
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		/*
		 * This method is handles the arrived message. It is a blocking functions. It is implemented form the 
		 * MqttCallBack interface
		 */
		// TODO Auto-generated method stub
		System.out.println(this.client.getClientId());
		this.setRcvMsg(message);
		String msg = new String(message.getPayload());
		System.out.println("-------------------------------------------------------------------------------");
		LOGGER.info("\nRecieved message "+ msg);
		DataUtil util = new DataUtil();
//		SensorData data = util.toSensorDataFromJsonFile(msg);
//		String json = util.JsonFromSensorData(data);
//		LOGGER.info("\nJson after converting to SensorData and back :" + json);
		System.out.println("-------------------------------------------------------------------------------\n");
	}

	public void deliveryComplete(IMqttDeliveryToken token) {
		LOGGER.info("Message delivered");
		
	}
	
}
