package org.monitis.api.monitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;
import org.monitis.beans.LocationInterval;
import org.monitis.beans.Response;
import org.monitis.beans.URLObject;
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
public class ExternalMonitor extends BaseMonitor{

	public ExternalMonitor(String apiKey, String secretKey){
		super(apiKey, secretKey, Constants.API_URL);
	}
	
	public ExternalMonitor() {
		// TODO Auto-generated constructor stub
	}

	public enum ExternalMonitorAction{
		addExternalMonitor,
		editExternalMonitor,
		deleteExternalMonitor,
		tests,
		tags,
		tagtests,
		testinfo,
		testresult,
		topexternal,
		testsLastValues,
		locations,
		suspendExternalMonitor,
		activateExternalMonitor;
	}
	
	public Response addMonitor(String name, String tag, List<Integer> locationIds,
			String url, String type, Integer interval,
			String contentMatchString, Integer detailedTestType, 
			Integer contentMatchFlag, String postData, String basicAuthUser, String basicAuthPass,
			HashMap<String, String> testParams,
			Integer timeout, Integer overSSL, Float uptimeSLA, Float responseSLA) throws MonitisException{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("name", StringUtils.urlEncode(name));
		params.put("tag", StringUtils.urlEncode(tag));
		params.put("locationIds", StringUtils.join(locationIds, ","));
		params.put("url", StringUtils.urlEncode(url));
		params.put("type", type);
		params.put("interval", interval);
		if(contentMatchString != null)params.put("contentMatchString", StringUtils.urlEncode(contentMatchString));
		if(contentMatchFlag != null)params.put("contentMatchFlag", contentMatchFlag);
		if(basicAuthUser != null)params.put("basicAuthUser", StringUtils.urlEncode(basicAuthUser));
		if(basicAuthPass != null)params.put("basicAuthPass", StringUtils.urlEncode(basicAuthPass));
		if(postData != null)params.put("postData", StringUtils.urlEncode(postData));
		if(testParams != null)params.put("params", URLObject.mapToURLString(testParams));
		params.put("detailedTestType", detailedTestType);
		if(timeout != null)params.put("timeout", timeout);
		if(uptimeSLA != null)params.put("uptimeSLA", uptimeSLA);
		if(responseSLA != null)params.put("responseSLA", responseSLA);
		if(overSSL != null)params.put("overSSL", overSSL);
		
		Response resp = makePostRequest(ExternalMonitorAction.addExternalMonitor, params);
		return resp;
	}
	
	public Response deleteMonitors(Integer [] testIds) throws MonitisException {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("testIds", StringUtils.join(testIds, ","));
		Response resp = makePostRequest(ExternalMonitorAction.deleteExternalMonitor, params);
		return resp;
	}
	
	public Response editMonitor(Integer testId, String name, String tag,
			List<LocationInterval> locationIds,
			String url, String contentMatchString, 
			Integer timeout, Integer maxValue, Float uptimeSLA, Float responseSLA) throws MonitisException{
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("testId", testId);
		params.put("name", StringUtils.urlEncode(name));
		params.put("tag", StringUtils.urlEncode(tag));
		params.put("locationIds", StringUtils.urlEncode(StringUtils.join(locationIds, ",")));
		params.put("url", StringUtils.urlEncode(url));
		if(contentMatchString != null)params.put("contentMatchString", StringUtils.urlEncode(contentMatchString));
		if(timeout != null)params.put("timeout", timeout);
		if(maxValue != null)params.put("maxValue", maxValue);
		if(uptimeSLA != null)params.put("uptimeSLA", uptimeSLA);
		if(responseSLA != null)params.put("responseSLA", responseSLA);
		Response resp = makePostRequest(ExternalMonitorAction.editExternalMonitor, params);
		return resp;
	}
	
	protected Enum getAction(MonitorAction action)throws MonitisException{
		switch(action){
			case getMonitors: return ExternalMonitorAction.tests;	
			case getMonitorInfo: return  ExternalMonitorAction.testinfo;
			case getMonitorResults: return  ExternalMonitorAction.testresult;
			case suspendMonitors: return ExternalMonitorAction.suspendExternalMonitor;
			case activateMonitors: return ExternalMonitorAction.activateExternalMonitor;
		}
		throw new MonitisException("Action is not supported");
	}
	
	protected String getMonitorIdString(){
		return "testId";
	}
	
	public Response getTops(String tag, Integer limit, OutputType output) throws MonitisException {
		return getTops(ExternalMonitorAction.topexternal, tag, limit, false, output);
	}
	
	public Response getTags(OutputType output) throws MonitisException {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("output", output);
		return makeGetRequest(ExternalMonitorAction.tags, params);
	}
	
	public Response getTagTests(String tag, OutputType output) throws MonitisException {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("tag", StringUtils.urlEncode(tag));
		params.put("output", output);
		return makeGetRequest(ExternalMonitorAction.tagtests, params);
	}
	
	public Response getLocations(OutputType output) throws MonitisException {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("output", output);
		return makeGetRequest(ExternalMonitorAction.locations, params);
	}
	
	public Response getSnapshot(OutputType output, List<Integer> locationIds) throws MonitisException {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("output", output);
		if (locationIds != null){
			params.put("locationIds", StringUtils.join(locationIds, ","));
		}
		return makeGetRequest(ExternalMonitorAction.testsLastValues, params);
	}
		
	public static void main(String[] args) {
		ExternalMonitor monitor;
		try {
			Response resp = null;
			monitor = new ExternalMonitor();
			List<Integer> locs = new ArrayList<Integer>();
			locs.add(1);
			
			HashMap<String, String> testparams = new HashMap<String, String>();
			testparams.put("aa", "bbb");
			testparams.put("cccc", "dddd");
			testparams.put("eeee", "ffff");
			resp = monitor.addMonitor("nununu.com", "tag 1", locs, "nunu8.com", "http", 30, "", 1, null, null, null, null, testparams, 5000, null, null, null);
			System.out.println(resp);
			Integer monitorId = new JSONObject(resp.getResponseText()).getJSONObject("data").getInt("testId");
			List<LocationInterval> locIntervals = new ArrayList<LocationInterval>();
			locIntervals.add(new LocationInterval(1,5));
			locIntervals.add(new LocationInterval(5,10));
			resp = monitor.editMonitor(monitorId, "nn nnnn", "def uuu", locIntervals, "nunan.com", "", null, 560, null, null);
			System.out.println(resp);
			resp = monitor.getMonitorInfo(monitorId, OutputType.XML);
			System.out.println(resp);
			resp = monitor.getMonitors(OutputType.XML);
			System.out.println(resp);
			resp = monitor.getTags(OutputType.JSON);
			System.out.println(resp);
			resp = monitor.getTagTests("Default", OutputType.XML);
			System.out.println(resp);
			resp = monitor.suspendMonitors(new Integer[]{1500324}, "aaaa");
			System.out.println(resp);
			resp = monitor.activateMonitors(new Integer[]{1500324}, "Default");
			System.out.println(resp);
			resp = monitor.getTops(null, 3, OutputType.XML);
			System.out.println(resp);
			List<Integer> locIds = new ArrayList<Integer>();
			locIds.add(4);
			locIds.add(1);
			resp = monitor.getMonitorResults(monitorId, 2011, 5, 13, 0, locIds, OutputType.XML);
			System.out.println(resp);
			resp = monitor.getLocations(OutputType.XML);
			System.out.println(resp);
			resp = monitor.getSnapshot(OutputType.XML, locIds);
			System.out.println(resp);
			resp = monitor.deleteMonitors(new Integer[]{monitorId});
			System.out.println(resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
