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

public class FullpageloadMonitor extends BaseMonitor{

	public enum FullpageloadAction{
		fullPageLoadLocations,
		fullPageLoadTests,
		fullPageLoadTestInfo,
		fullPageLoadTestResult,	
		addFullPageLoadMonitor,	
		editFullPageLoadMonitor,
		deleteFullPageLoadMonitor,
		suspendTransactionMonitor,
		activateTransactionMonitor,
		topFullpage,
		fullPageLoadSnapshot
	}
	
	public FullpageloadMonitor(){
		super();
	}
	
	public FullpageloadMonitor(String apiKey, String secretKey){
		super(apiKey, secretKey, Constants.API_URL);
	}
	
	protected Enum getAction(MonitorAction action)throws MonitisException{
		switch(action){
			case getMonitors: return FullpageloadAction.fullPageLoadTests;	
			case getMonitorInfo: return  FullpageloadAction.fullPageLoadTestInfo;
			case getMonitorResults: return  FullpageloadAction.fullPageLoadTestResult;
			case suspendMonitors: return FullpageloadAction.suspendTransactionMonitor;
			case activateMonitors: return FullpageloadAction.activateTransactionMonitor;
			case deleteMonitor: return FullpageloadAction.deleteFullPageLoadMonitor;
		}
		throw new MonitisException("Action is not supported");
	}
	
	protected String getMonitorIdString(){
		return "monitorId";
	}
	
	public Response addMonitor(String name, String tag, String url, List<Integer> locationIds,
			 Integer checkInterval, Integer timeout, Integer uptimeSLA, Integer responseSLA) throws MonitisException{
		return addEditMonitor(null, name, tag, url, locationIds, checkInterval, timeout, uptimeSLA, responseSLA);
	}
	
	public Response editMonitor(Integer monitorId, String name, String tag, String url, List<Integer> locationIds,
			 Integer checkInterval, Integer timeout, Integer uptimeSLA, Integer responseSLA) throws MonitisException{
		return addEditMonitor(monitorId, name, tag, url, locationIds, checkInterval, timeout, uptimeSLA, responseSLA);
	}
	
	public Response addEditMonitor(Integer monitorId, String name, String tag, String url, List<Integer> locationIds,
			 Integer checkInterval, Integer timeout, Integer uptimeSLA, Integer responseSLA) throws MonitisException{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("name", StringUtils.urlEncode(name));
		params.put("tag", StringUtils.urlEncode(tag));
		params.put("url", url);
		params.put("locationIds", StringUtils.join(locationIds, ","));
		params.put("checkInterval", checkInterval);
		params.put("timeout", timeout);
		if (uptimeSLA != null) params.put("uptimeSLA", uptimeSLA);
		if (responseSLA != null) params.put("responseSLA", responseSLA);
		Response resp;
		if (monitorId != null) {
			params.put("monitorId", monitorId);
			resp = makePostRequest(FullpageloadAction.editFullPageLoadMonitor, params);
		}else{
			resp = makePostRequest(FullpageloadAction.addFullPageLoadMonitor, params);
		}
		return resp;
	}
	

	public Response getTops(String tag, Integer limit, OutputType output) throws MonitisException {
		return getTops(FullpageloadAction.topFullpage, tag, limit, false, output);
	}
	
	public Response getLocations(OutputType output) throws MonitisException {
		return makeGetRequest(FullpageloadAction.fullPageLoadLocations, output);
	}
	
	
	public Response getSnapshot(OutputType output, List<Integer> locationIds) throws MonitisException {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("output", output);
		if (locationIds != null){
			params.put("locationIds", StringUtils.join(locationIds, ","));
		}
		return makeGetRequest(FullpageloadAction.fullPageLoadSnapshot, params);
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			FullpageloadMonitor monitor = new FullpageloadMonitor();
			Response resp;
			List<Integer> locIds = new ArrayList<Integer>();
			locIds.add(1);
			locIds.add(19);
			resp = monitor.addMonitor("torus_", "Monioot", "http://mon.itor.us", locIds, 15, 3000, 95, 1800);
			System.out.println(resp);
			Integer monitorId = new JSONObject(resp.getResponseText()).getJSONObject("data").getInt("testId");
			resp = monitor.editMonitor(monitorId, "monitorusetr_ful likiko", "Google", "http://mon.itor.usik", locIds, 16, 3000, 95, 1800);
			System.out.println(resp);
			resp = monitor.getMonitors(OutputType.JSON);
			System.out.println(resp);
			resp = monitor.getMonitorInfo(monitorId, OutputType.XML);
			System.out.println(resp);
			resp = monitor.getMonitorResults(monitorId, 2011, 6, 8, 240, locIds, OutputType.XML);
			System.out.println(resp);
			resp = monitor.activateMonitors(new Integer[]{monitorId}, null);
			System.out.println(resp);
			resp = monitor.suspendMonitors(new Integer[]{monitorId}, null);
			System.out.println(resp);
			resp = monitor.getTops(null, null, OutputType.XML);
			System.out.println(resp);
			resp = monitor.getLocations(OutputType.XML);
			System.out.println(resp);
			resp = monitor.getSnapshot(OutputType.JSON, null);
			System.out.println(resp);
			resp = monitor.deleteMonitors(new Integer[]{monitorId});
			System.out.println(resp);
			
		}catch (Exception e) {
			e.printStackTrace();
		}

	}

}
