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
public class ProcessMonitor extends InternalMonitor{

	public ProcessMonitor(String apiKey, String secretKey) {
		super(apiKey, secretKey, Constants.API_URL, InternalMonitor.Type.PROCESS);
	}
	
	public ProcessMonitor() {
		super(InternalMonitor.Type.PROCESS);
	}
	
	public enum ProcessMonitorAction{
		addProcessMonitor,
		editProcessMonitor,
		agentProcesses,
		processInfo,
		processResult,
		topProcessByCPUUsage(),
		topProcessByMemoryUsage(),
		topProcessByVirtMemoryUsage();
	}
	
	protected Enum getAction(MonitorAction action) throws MonitisException{
		switch(action){
			case getMonitors: return ProcessMonitorAction.agentProcesses;	
			case getMonitorInfo: return  ProcessMonitorAction.processInfo;
			case getMonitorResults: return  ProcessMonitorAction.processResult;
		}
		throw new MonitisException("Action is not supported");
	}
	
	public Response addMonitor(String agentkey, String name, String tag, 
			String processName, Integer cpuLimit, Integer memoryLimit,
			Integer virtualMemoryLimit) throws MonitisException{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("agentkey", agentkey);
		params.put("name", StringUtils.urlEncode(name));
		params.put("tag", StringUtils.urlEncode(tag));
		params.put("processName", StringUtils.urlEncode(processName));
		params.put("cpuLimit", cpuLimit);
		params.put("memoryLimit", memoryLimit);
		params.put("virtualMemoryLimit", virtualMemoryLimit);
		Response resp = makePostRequest(ProcessMonitorAction.addProcessMonitor, params);
		return resp;
	}
	
	public Response editMonitor(Integer testId, String name, String tag,
			Integer cpuLimit, Integer memoryLimit,
			Integer virtualMemoryLimit)  throws MonitisException{
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("testId", testId);
		params.put("name", StringUtils.urlEncode(name));
		params.put("tag", StringUtils.urlEncode(tag));
		params.put("cpuLimit", cpuLimit);
		params.put("memoryLimit", memoryLimit);
		params.put("virtualMemoryLimit", virtualMemoryLimit);
		
		Response resp = makePostRequest(ProcessMonitorAction.editProcessMonitor, params);
		return resp;
	}
	
	public Response getTopsByCPUUsage(String tag, Integer limit, boolean detailedResults, OutputType output) throws MonitisException {
		return getTops(ProcessMonitorAction.topProcessByCPUUsage, tag, limit, detailedResults, output);
	}
	
	
	public Response getTopsByMemoryUsage(String tag, Integer limit, boolean detailedResults, OutputType output) throws MonitisException {
		return getTops(ProcessMonitorAction.topProcessByMemoryUsage, tag, limit, detailedResults, output);
	}
	
	
	public Response getTopsByVirtMemoryUsage(String tag, Integer limit, boolean detailedResults, OutputType output) throws MonitisException {
		return getTops(ProcessMonitorAction.topProcessByVirtMemoryUsage, tag, limit, detailedResults, output);
	}
	
	
	public static void main(String[] args) {
		ProcessMonitor monitor;
		try {
			Response resp = null;
			monitor = new ProcessMonitor();
			HashMap<String, String> testparams = new HashMap<String, String>();
			testparams.put("aa", "bbb");
			testparams.put("cccc", "dddd");
			testparams.put("eeee", "ffff");
			resp = monitor.addMonitor("ngaspary_pc", "premon_skype_Pross", "Process_tag", "skype89", 10, 10, 10);
			Integer monitorId = new JSONObject(resp.getResponseText()).getJSONObject("data").getInt("testId");
			System.out.println(resp.getResponseText());
			resp = monitor.editMonitor(monitorId, "premon_skype_Process", "Process_tag", 10, 40, 50);
			System.out.println(resp.getResponseText());
			resp = monitor.getMonitorResults(monitorId, 2011, 8, 4, 400, null, OutputType.XML);
			System.out.println(resp.getResponseText());
			resp = monitor.getMonitorInfo(monitorId, OutputType.XML);
			System.out.println(resp.getResponseText());
			resp = monitor.getMonitors(576, OutputType.XML);
			System.out.println(resp.getResponseText());
			resp = monitor.getTopsByCPUUsage(null, null, true, OutputType.XML);
			System.out.println(resp.getResponseText());
			resp = monitor.getTopsByMemoryUsage(null, null, true, OutputType.XML);
			System.out.println(resp.getResponseText());
			resp = monitor.getTopsByVirtMemoryUsage(null, null, true, OutputType.XML);
			System.out.println(resp.getResponseText());
			resp = monitor.deleteMonitors(new Integer[]{monitorId});
			System.out.println(resp.getResponseText());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
