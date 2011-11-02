package org.monitis.api.monitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;
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
public class InternalPingMonitor extends InternalMonitor{

	public InternalPingMonitor(String apiKey, String secretKey){
		super(apiKey, secretKey, Constants.API_URL, InternalMonitor.Type.PING);
	}
	
	public InternalPingMonitor() {
		super(InternalMonitor.Type.PING);
	}

	public enum InternalPingMonitorAction{
		addInternalPingMonitor,
		editInternalPingMonitor,
		agentPingTests,
		internalPingInfo,
		internalPingResult,
		topInternalPing;
	}
	
	protected Enum getAction(MonitorAction action) throws MonitisException {
		switch(action){
			case getMonitors: return InternalPingMonitorAction.agentPingTests;	
			case getMonitorInfo: return  InternalPingMonitorAction.internalPingInfo;
			case getMonitorResults: return  InternalPingMonitorAction.internalPingResult;
		}
		throw new MonitisException("Action is not supported");
	}
	
	public Response addMonitor(int userAgentId, String name, String tag, 
			String url, int packetsCount, int maxLost,
			int timeout, int packetsSize) throws MonitisException{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("userAgentId", userAgentId);
		params.put("name", StringUtils.urlEncode(name));
		params.put("tag", StringUtils.urlEncode(tag));
		params.put("url", StringUtils.urlEncode(url));
		params.put("packetsCount", packetsCount);
		params.put("maxLost", maxLost);
		params.put("packetsSize", packetsSize);
		params.put("timeout", timeout);
		
		Response resp = makePostRequest(InternalPingMonitorAction.addInternalPingMonitor, params);
		return resp;
	}
	
	public Response editMonitor(Integer testId, String name, String tag,
			int packetsCount, int maxLost,
			int timeout, int packetsSize)  throws MonitisException{
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("testId", testId);
		params.put("name", StringUtils.urlEncode(name));
		params.put("tag", StringUtils.urlEncode(tag));
		params.put("timeout", timeout);
		params.put("packetsCount", packetsCount);
		params.put("maxLost", maxLost);
		params.put("packetsSize", packetsSize);
		
		Response resp = makePostRequest(InternalPingMonitorAction.editInternalPingMonitor, params);
		return resp;
	}
	
	public Response getTops(String tag, Integer limit, boolean detailedResults, OutputType output) throws MonitisException {
		return getTops(InternalPingMonitorAction.topInternalPing, tag, limit, detailedResults, output);
	}
	
	
	public static void main(String[] args) {
		InternalPingMonitor monitor;
		try {
			Response resp = null;
			monitor = new InternalPingMonitor();
			HashMap<String, String> testparams = new HashMap<String, String>();
			testparams.put("aa", "bbb");
			testparams.put("cccc", "dddd");
			testparams.put("eeee", "ffff");
			resp = monitor.addMonitor(1097, "monitorus_ping", "monitorus", "mon.itor.us", 5, 2, 10000, 56);
			System.out.println(resp.getResponseText());
			Integer monitorId = new JSONObject(resp.getResponseText()).getJSONObject("data").getInt("testId");			
			resp = monitor.editMonitor(monitorId, "monitorus_pingik", "monitorus", 10, 5, 8000, 89);
			System.out.println(resp.getResponseText());
			resp = monitor.getMonitorResults(monitorId, 2011, 8, 4, 400, null, OutputType.JSON);
			System.out.println(resp.getResponseText());
			resp = monitor.getMonitorInfo(monitorId, OutputType.XML);
			System.out.println(resp.getResponseText());
			resp = monitor.getMonitors(672, OutputType.XML);
			System.out.println(resp.getResponseText());
			resp = monitor.getTops(null, null, false, OutputType.XML);
			System.out.println(resp.getResponseText());
			resp = monitor.deleteMonitors(new Integer[]{monitorId});
			System.out.println(resp.getResponseText());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
