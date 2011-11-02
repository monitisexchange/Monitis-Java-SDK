package org.monitis.api.monitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;
import org.monitis.beans.LocationInterval;
import org.monitis.beans.Response;
import org.monitis.enums.MonitorAction;
import org.monitis.enums.OutputType;
import org.monitis.exception.MonitisException;
import org.monitis.utils.Constants;
import org.monitis.utils.StringUtils;

/**
 * 
 * @author ngaspary
 *
 */
public class InternalHttpMonitor extends InternalMonitor{

	public InternalHttpMonitor(String apiKey, String secretKey) {
		super(apiKey, secretKey, Constants.API_URL, InternalMonitor.Type.HTTP);
	}
	
	public InternalHttpMonitor() {
		super(InternalMonitor.Type.HTTP);
	}

	public enum InternalHttpMonitorAction{
		addInternalHttpMonitor,
		editInternalHttpMonitor,
		agentHttpTests,
		internalHttpInfo,
		internalHttpResult,
		topInternalHTTP;
	}
	
	protected Enum getAction(MonitorAction action) throws MonitisException{
		switch(action){
			case getMonitors: return InternalHttpMonitorAction.agentHttpTests;
			case getMonitorInfo: return  InternalHttpMonitorAction.internalHttpInfo;
			case getMonitorResults: return  InternalHttpMonitorAction.internalHttpResult;
		}
		throw new MonitisException("Action is not supported");
	}
	
	public static final Integer HTTP_GET = 0;
	
	public static final Integer HTTP_POST = 1;
	
	public static final Integer HTTP_HEAD = 2;
	
	public Response addMonitor(int userAgentId, String name, String tag, 
			String url, int httpMethod, 
			String contentMatchString, int contentMatchFlag, String postData,
			String userAuth, String passAuth,
			int loadFull, int redirect, 
			Integer timeout, Integer overSSL) throws MonitisException{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("userAgentId", userAgentId);
		params.put("name", StringUtils.urlEncode(name));
		params.put("tag", StringUtils.urlEncode(tag));
		params.put("url", StringUtils.urlEncode(url));
		params.put("httpMethod", httpMethod);
		params.put("contentMatchString", StringUtils.urlEncode(contentMatchString));
		params.put("contentMatchFlag", contentMatchFlag);
		params.put("userAuth", StringUtils.urlEncode(userAuth));
		params.put("passAuth", StringUtils.urlEncode(passAuth));
		params.put("postData", StringUtils.urlEncode(postData));
		params.put("timeout", timeout);
		params.put("overSSL", overSSL);
		params.put("redirect", redirect);
		params.put("loadFull", loadFull);
		
		Response resp = makePostRequest(InternalHttpMonitorAction.addInternalHttpMonitor, params);
		return resp;
	}
	
	public Response editMonitor(Integer testId, String name, String tag,
			int httpMethod,
			String urlParams, String postData,
			String userAuth, String passAuth, int loadFull, int redirect, 
			List<LocationInterval> locations,
			String contentMatchString, 
			Integer timeout, Integer maxValue, Integer overSSL) throws MonitisException{
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("testId", testId);
		params.put("name", StringUtils.urlEncode(name));
		params.put("tag", StringUtils.urlEncode(tag));
		params.put("locations", StringUtils.join(locations, ","));
		params.put("contentMatchString", StringUtils.urlEncode(contentMatchString));
		params.put("timeout", timeout);
		params.put("maxValue", maxValue);
		params.put("urlParams", StringUtils.urlEncode(urlParams));
		params.put("userAuth", StringUtils.urlEncode(userAuth));
		params.put("passAuth", StringUtils.urlEncode(passAuth));
		params.put("postData", StringUtils.urlEncode(postData));
		params.put("httpMethod", httpMethod);
		params.put("overSSL", overSSL);
		params.put("redirect", redirect);
		params.put("loadFull", loadFull);
		
		Response resp = makePostRequest(InternalHttpMonitorAction.editInternalHttpMonitor, params);
		return resp;
	}
	
	public Response getTops(String tag, Integer limit, OutputType output) throws MonitisException {
		return getTops(InternalHttpMonitorAction.topInternalHTTP, tag, limit, false, output);
	}

	public static void main(String[] args) {
		InternalHttpMonitor monitor;
		try {
			Response resp = null;
			monitor = new InternalHttpMonitor();
			List<Integer> locs = new ArrayList<Integer>();
			locs.add(1);
			locs.add(5);
			HashMap<String, String> testparams = new HashMap<String, String>();
			testparams.put("aa", "bbb");
			testparams.put("cccc", "dddd");
			testparams.put("eeee", "ffff");
			resp = monitor.addMonitor(2466, "blogmonitorus_http ok", "monitorus", "blog.mon.itor.us", HTTP_GET, "error", 0, "", "user", "pass", 1, 1, 10000, 1);
			System.out.println(resp.getResponseText());
			Integer monitorId = new JSONObject(resp.getResponseText()).getJSONObject("data").getInt("testId");
			List<LocationInterval> locIntervals = new ArrayList<LocationInterval>();
			locIntervals.add(new LocationInterval(1,5));
			locIntervals.add(new LocationInterval(5,10));
			resp = monitor.editMonitor(monitorId, "blogmonitorus_htpu", "monitorus ok", HTTP_POST, "urlpa=url;val", "post=ooo", "user", "pass", 0, 0, locIntervals, "contentMatchString", 20000, 560, 0);
			System.out.println(resp.getResponseText());
			resp = monitor.getMonitorResults(monitorId, 2011, 8, 4, 240, null, OutputType.XML);
			System.out.println(resp.getResponseText());
			resp = monitor.getMonitorInfo(monitorId, OutputType.XML);
			System.out.println(resp.getResponseText());
			resp = monitor.getMonitors(672, OutputType.XML);
			System.out.println(resp.getResponseText());
			resp = monitor.getTops(null, null, OutputType.XML);
			System.out.println(resp.getResponseText());
			resp = monitor.deleteMonitors(new Integer[]{monitorId});
			System.out.println(resp.getResponseText());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
