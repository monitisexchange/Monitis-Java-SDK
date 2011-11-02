package org.monitis.utils;

import java.io.IOException;
import java.util.Properties;

public class Constants {
	
	private Constants() {
		super();
	}
	
	public final static String SERVER_URL;
	public final static String API_KEY;
	public final static String SECRET_KEY;
	
	static{
		Properties conf = new Properties(); ;
		try {
			conf.load(Constants.class.getClassLoader().getResourceAsStream("conf.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		SERVER_URL = conf.getProperty("serverUrl");
		API_KEY = conf.getProperty("apikey");
		SECRET_KEY = conf.getProperty("secretkey");
	}
	
	public final static String CUSTOM_MONITOR_API_URL = SERVER_URL+"/customMonitorApi";
	
	public final static String API_URL = SERVER_URL+"/api";
	
	public final static String API_VERSION = "2";
	
	public static final String ENCODING = "UTF-8"; 
	
	public final static Integer AGENT_CPU_DATA_TYPE_ID = 7;
	public final static Integer AGENT_LOAD_AVERAGE_DATA_TYPE_ID = 6;
	public final static Integer AGENT_MEMORY_DATA_TYPE_ID = 3;
	public final static Integer AGENT_DRIVE_DATA_TYPE_ID = 2;
	public final static Integer AGENT_PROCESS_DATA_TYPE_ID = 1;
	public final static Integer TEST_DATA_TYPE_ID = 0;
	
	public static String NEW_LINE = System.getProperty("line.separator");
	
	
}
