package org.monitis.api.monitor;

import java.util.HashMap;

import org.json.JSONObject;
import org.monitis.beans.Response;
import org.monitis.enums.MonitorAction;
import org.monitis.enums.OutputType;
import org.monitis.enums.Platform;
import org.monitis.exception.MonitisException;
import org.monitis.utils.Constants;
import org.monitis.utils.StringUtils;

/**
 * 
 * @author ngaspary
 *
 */
public class MemoryMonitor extends InternalMonitor{

	public MemoryMonitor(String apiKey, String secretKey){
		super(apiKey, secretKey, Constants.API_URL, InternalMonitor.Type.MEMORY);
	}
	
	public MemoryMonitor(){
		super(InternalMonitor.Type.MEMORY);
	}
	
	public enum MemoryMonitorAction{
		addMemoryMonitor,
		editMemoryMonitor,
		agentMemory,
		memoryInfo,
		memoryResult,
		topmemory;
	}
	
	protected Enum getAction(MonitorAction action) throws MonitisException{
		switch(action){
			case getMonitors: return MemoryMonitorAction.agentMemory;	
			case getMonitorInfo: return  MemoryMonitorAction.memoryInfo;
			case getMonitorResults: return  MemoryMonitorAction.memoryResult;
		}
		throw new MonitisException("Action is not supported");
	}
		
	
	public Response addMonitor(String agentkey, String name, String tag, Platform platform,
			Float freeLimit, Float freeVirtualLimit, 
			Float freeSwapLimit, Float bufferedLimit, 
			Float cachedLimit) throws MonitisException{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("agentkey", agentkey);
		params.put("name", StringUtils.urlEncode(name));
		params.put("tag", StringUtils.urlEncode(tag));
		params.put("platform", platform);
		if (Platform.WINDOWS.equals(platform)) {
			params.put("freeLimit", freeLimit);
			params.put("freeVirtualLimit", freeVirtualLimit);
			params.put("freeSwapLimit", freeSwapLimit);
		} else if (Platform.LINUX.equals(platform)) {
			params.put("freeLimit", freeLimit);
			params.put("freeSwapLimit", freeSwapLimit);
			params.put("bufferedLimit", bufferedLimit);
			params.put("cachedLimit", cachedLimit);
		}
		else if (Platform.OPENSOLARIS.equals(platform) ){
			params.put("freeLimit", freeLimit);
			params.put("freeSwapLimit", freeSwapLimit);
		}
		
		Response resp = makePostRequest(MemoryMonitorAction.addMemoryMonitor, params);
		return resp;
	}
	
	public Response editMonitor(Integer testId, String name, String tag, Platform platform,
			Float freeLimit, Float freeVirtualLimit, 
			Float freeSwapLimit, Float bufferedLimit, 
			Float cachedLimit)  throws MonitisException{
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("testId", testId);
		params.put("name", StringUtils.urlEncode(name));
		params.put("tag", StringUtils.urlEncode(tag));
		params.put("platform", platform);
		if (Platform.WINDOWS.equals(platform)) {
			params.put("freeLimit", freeLimit);
			params.put("freeVirtualLimit", freeVirtualLimit);
			params.put("freeSwapLimit", freeSwapLimit);
		} else if (Platform.LINUX.equals(platform)) {
			params.put("freeLimit", freeLimit);
			params.put("freeSwapLimit", freeSwapLimit);
			params.put("bufferedLimit", bufferedLimit);
			params.put("cachedLimit", cachedLimit);
		}
		else if (Platform.OPENSOLARIS.equals(platform) ){
			params.put("freeLimit", freeLimit);
			params.put("freeSwapLimit", freeSwapLimit);
		}
		params.put("freeLimit", freeLimit);
		
		Response resp = makePostRequest(MemoryMonitorAction.editMemoryMonitor, params);
		return resp;
	}
	
	public Response getTops(String tag, Integer limit, boolean detailedResults, OutputType output) throws MonitisException {
		return getTops(MemoryMonitorAction.topmemory, tag, limit, detailedResults, output);
	}
	
	public static void main(String[] args) {
		MemoryMonitor monitor;
		try {
			Response resp = null;
			monitor = new MemoryMonitor();
			HashMap<String, String> testparams = new HashMap<String, String>();
			testparams.put("aa", "bbb");
			testparams.put("cccc", "dddd");
			testparams.put("eeee", "ffff");
			resp = monitor.addMonitor("ngaspary_pc", "memory@ngaspary_pc", "rd_tag", Platform.WINDOWS, (float)10.6, (float)20.5, (float)30.2, (float)40.0, (float)50.0); 
			Integer monitorId = new JSONObject(resp.getResponseText()).getJSONObject("data").getInt("testId");
			resp = monitor.editMonitor(monitorId, "memory@ngaspary_pc_ol", "Deafult", Platform.LINUX, (float)10.6, (float)20.5, (float)30.2, (float)40.0, (float)50.0);
			System.out.println(resp);
			resp = monitor.getMonitorResults(monitorId, 2011, 8, 4, 240, null, OutputType.XML);
			System.out.println(resp);
			resp = monitor.getMonitorInfo(monitorId, OutputType.XML);
			System.out.println(resp);
			resp = monitor.getMonitors(576, OutputType.XML);
			System.out.println(resp);
			resp = monitor.getTops("ad", null, true, OutputType.XML);
			System.out.println(resp);
			resp = monitor.deleteMonitors(new Integer[]{monitorId});
			System.out.println(resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
