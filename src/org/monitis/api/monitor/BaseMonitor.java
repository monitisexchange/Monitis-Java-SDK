package org.monitis.api.monitor;

import java.util.HashMap;
import java.util.List;

import org.monitis.api.APIObject;
import org.monitis.beans.Response;
import org.monitis.enums.MonitorAction;
import org.monitis.enums.OutputType;
import org.monitis.exception.MonitisException;
import org.monitis.utils.StringUtils;

/**
 * 
 * @author ngaspary
 *
 */
public abstract class BaseMonitor extends APIObject{
	
	public BaseMonitor(String apiKey, String secretKey, String apiUrl){
		super(apiKey, secretKey, apiUrl);
	}
	
	public BaseMonitor(){
		super();
	}
	
	public BaseMonitor(String apiUrl){
		super(apiUrl);
	}
	
	public Response getMonitorInfo(Integer monitorId, OutputType output) throws MonitisException {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put(getMonitorIdString(), monitorId);
		params.put("output", output);
		Response resp = makeGetRequest(getAction(MonitorAction.getMonitorInfo), params);
		return resp;
	}
	
	public Response getMonitors(OutputType output) throws MonitisException {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("output", output);
		Response resp = makeGetRequest(getAction(MonitorAction.getMonitors), params);
		return resp;
	}
	
	public Response getMonitorResults(int monitorId, int year, int month, int day, int timezone, OutputType output) throws MonitisException {
		return getMonitorResults(monitorId, year, month, day, timezone, null, output);
	}
	
	public Response getMonitorResults(int monitorId, int year, int month, int day, 
			Integer timezone, List<Integer> locationIds, OutputType output) throws MonitisException {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put(getMonitorIdString(), monitorId);
		params.put("year", year);
		params.put("month", month);
		params.put("day", day);
		if (timezone != null){
			params.put("timezone", timezone);
		}
		params.put("output", output);
		if (locationIds != null){
			params.put("locationIds", StringUtils.join(locationIds, ","));
		}
		Response resp = makeGetRequest(getAction(MonitorAction.getMonitorResults), params);
		return resp;
	}
	
	public Response deleteMonitors(Integer[] monitorIds) throws MonitisException {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("monitorId", StringUtils.join(monitorIds, ","));
		Response resp = makePostRequest(getAction(MonitorAction.deleteMonitor), params);
		return resp;
	}
	
	public Response suspendMonitors(Integer[] monitorIds, String tag) throws MonitisException {
		HashMap<String, Object> params = new HashMap<String, Object>();
		if(monitorIds != null) params.put("monitorIds", StringUtils.join(monitorIds, ","));
		if(tag != null)params.put("tag", StringUtils.urlEncode(tag));
		Response resp = makePostRequest(getAction(MonitorAction.suspendMonitors), params);
		return resp;
	}
	
	public Response activateMonitors(Integer[] monitorIds, String tag) throws MonitisException {
		HashMap<String, Object> params = new HashMap<String, Object>();
		if(monitorIds != null) params.put("monitorIds", StringUtils.join(monitorIds, ","));
		if(tag != null) params.put("tag", StringUtils.urlEncode(tag));
		Response resp = makePostRequest(getAction(MonitorAction.activateMonitors), params);
		return resp;
	}
	
	public Response getTops(Enum action, String tag, Integer limit, boolean detailedResults, OutputType output) throws MonitisException {
		HashMap<String, Object> params = new HashMap<String, Object>();
		if(limit != null) params.put("limit", limit);
		if(tag != null) params.put("tag", StringUtils.urlEncode(tag));
		params.put("output", output);
		params.put("detailedResults", detailedResults);
		Response resp = makeGetRequest(action, params);
		return resp;
	}
	
	protected Enum getAction(MonitorAction action) throws MonitisException {
		return action;
	}
	
	protected String getMonitorIdString(){
		return "monitorId";
	}
	

}
