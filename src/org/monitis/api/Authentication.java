package org.monitis.api;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import org.monitis.beans.Response;
import org.monitis.enums.OutputType;
import org.monitis.exception.MonitisException;
import org.monitis.utils.Constants;
import org.monitis.utils.StringUtils;


public class Authentication extends APIObject {
	
	public enum AuthenticationAction{
		apikey,
		secretkey,
		userkey,
		authToken
	}
	
	public Authentication(){
		//super(Constants.CUSTOM_MONITOR_API_URL);
	}
	
	public Response getApiKey(String userName, String password, OutputType output) throws MonitisException{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("output", output);
		params.put("userName", StringUtils.urlEncode(userName));
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(password.getBytes());
			BigInteger hash = new BigInteger(1, md.digest());
			password = hash.toString(16);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		params.put("password", StringUtils.urlEncode(password));
		return makeGetRequest(AuthenticationAction.apikey, params);
	}
	
	public Response getSecretKey(OutputType output) throws MonitisException{
		return makeGetRequest(AuthenticationAction.secretkey, output);
	}
	
	public Response getToken(String apikey, String secretkey, OutputType output) throws MonitisException{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("output", output);
		params.put("apikey", StringUtils.urlEncode(apikey));
		params.put("secretkey", StringUtils.urlEncode(secretkey));
		return makeGetRequest(AuthenticationAction.authToken, params);
	}
	
	public Response getUserKey(String userName, String password, OutputType output) throws MonitisException{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("output", output);
		params.put("userName", StringUtils.urlEncode(userName));
		params.put("password", StringUtils.urlEncode(password));
		return makeGetRequest(AuthenticationAction.userkey, params);
	}
	
	public static void main(String[] args) throws MonitisException {
		Authentication auth = new Authentication();
		Response resp = auth.getApiKey("usermail", "pass", OutputType.XML);
		System.out.println(resp);
		resp = auth.getSecretKey(OutputType.XML);
		System.out.println(resp);
		resp = auth.getToken(Constants.API_KEY, Constants.SECRET_KEY, OutputType.XML);
		System.out.println(resp);
		resp = auth.getUserKey("usermail", "pass",  OutputType.XML);
		System.out.println(resp);
		
	}
}
