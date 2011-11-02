package org.monitis.api;

import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.TreeSet;

import org.apache.commons.httpclient.NameValuePair;
import org.monitis.beans.Response;
import org.monitis.enums.OutputType;
import org.monitis.exception.MonitisException;
import org.monitis.utils.CodingUtility;
import org.monitis.utils.Constants;
import org.monitis.utils.HttpConnector;
import org.monitis.utils.TimeUtility;

/**
 * 
 * @author ngaspary
 *
 */
public class APIObject {
	private final String apiKey;
	private final String secretKey;
	public final String apiUrl;
	
	
	public APIObject(String apiKey, String secretKey, String apiUrl){
		this.apiKey = apiKey;
		this.secretKey = secretKey;
		this.apiUrl = apiUrl;
	}
	
	public APIObject(){
		apiKey = Constants.API_KEY;
		secretKey = Constants.SECRET_KEY;
		apiUrl = Constants.API_URL;
	}
	
	public APIObject(String apiUrl){
		apiKey = Constants.API_KEY;
		secretKey = Constants.SECRET_KEY;
		this.apiUrl = apiUrl;
	}
	
	public Response makePostRequest(Enum action, HashMap<String, Object> params) throws MonitisException {
		return makePostRequest(action, params, null);
	}
	
	public Response makePostRequest(Enum action, HashMap<String, Object> params, String fileName) throws MonitisException {
		
		String url = apiUrl;
		Date curTime = TimeUtility.getNowByGMT().getTime();
		String formattedTime = TimeUtility.format(curTime, "yyyy-MM-dd HH:mm:ss");

		HashMap<String, Object> reqParams = new HashMap <String, Object>();
		reqParams.put("action", action.toString());
		reqParams.put("apikey", apiKey);
		reqParams.put("timestamp", formattedTime);
		reqParams.put("version", Constants.API_VERSION);
		if (params != null) reqParams.putAll(params);

		TreeSet<String> sortedKeys = new TreeSet <String>(String.CASE_INSENSITIVE_ORDER);
		sortedKeys.addAll(reqParams.keySet());

		StringBuilder paramValueStr = new StringBuilder();
		ArrayList < NameValuePair > queryParams = new ArrayList < NameValuePair >();
		for (String key : sortedKeys) {
			paramValueStr.append(key);
			paramValueStr.append(reqParams.get(key));
			queryParams.add(new NameValuePair(key, reqParams.get(key).toString()));
		}
		String checkSum;
		try {
			checkSum = CodingUtility.calculateRFC2104HMAC(paramValueStr.toString(), secretKey); 
		} catch (SignatureException e) {
			throw new MonitisException(e, "Error calculating checksum with the given secret key:"+secretKey);
		}
		queryParams.add(new NameValuePair("checksum", checkSum));
		Response resp = HttpConnector.sendPost(url, queryParams, fileName);
		return resp;

	}
	
	public Response makeGetRequest(Enum action, HashMap<String, Object> params) throws MonitisException {
		return makeGetRequest(action, params, null);
	}
	
	public Response makeGetRequest(Enum action, HashMap<String, Object> params, String fileName) throws MonitisException {
		
		String url = apiUrl;
		HashMap<String, Object> reqParams = new HashMap <String, Object >();
		reqParams.put("action", action.toString());
		reqParams.put("apikey", apiKey);
		reqParams.put("version", Constants.API_VERSION);
		reqParams.put("nocache", Math.random());
		if (params != null) reqParams.putAll(params);

		ArrayList < NameValuePair > queryParams = new ArrayList < NameValuePair >();
		for (String key : reqParams.keySet()) {
			queryParams.add(new NameValuePair(key, reqParams.get(key).toString()));
		}
		Response resp = HttpConnector.sendGet(url, queryParams, fileName);
		return resp;
	}
	
	public Response makeGetRequest(Enum action, OutputType output) throws MonitisException {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("output", output);
		return makeGetRequest(action, params, null);
	}
	

}
