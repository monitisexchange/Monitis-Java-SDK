package org.monitis.api.monitor;

import java.util.HashMap;

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
public class CPUMonitor extends InternalMonitor{

	public CPUMonitor(String apiKey, String secretKey){
		super(apiKey, secretKey, Constants.API_URL, InternalMonitor.Type.CPU);
	}
	
	public CPUMonitor() {
		super(InternalMonitor.Type.CPU);
	}

	public enum CPUMonitorAction{
		addCPUMonitor,
		editCPUMonitor,
		CPUInfo,
		cpuResult,
		agentCPU,
		topcpu;
	}
	
	protected Enum getAction(MonitorAction action) throws MonitisException{
		switch(action){
			case getMonitors: return CPUMonitorAction.agentCPU;	
			case getMonitorInfo: return  CPUMonitorAction.CPUInfo;
			case getMonitorResults: return  CPUMonitorAction.cpuResult;
		}
		throw new MonitisException("Action is not supported");
	}

	public Response addMonitor(String agentkey, String name, String tag, 
			Integer usedMax, Integer kernelMax,
			Integer niceMax, Integer idleMin, Integer ioWaitMax) throws MonitisException{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("agentkey", agentkey);
		params.put("name", StringUtils.urlEncode(name));
		params.put("tag", StringUtils.urlEncode(tag));
		params.put("usedMax", usedMax);
		params.put("kernelMax", kernelMax);
		if (niceMax != null){
			params.put("niceMax", niceMax);
			params.put("idleMin", idleMin);
			params.put("ioWaitMax", ioWaitMax);
		}
		
		Response resp = makePostRequest(CPUMonitorAction.addCPUMonitor, params);
		return resp;
	}
	
	public Response editMonitor(Integer testId, String name, String tag,
			Integer usedMax, Integer kernelMax,
			Integer niceMax, Integer idleMin, Integer ioWaitMax)  throws MonitisException{
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("testId", testId);
		params.put("name", StringUtils.urlEncode(name));
		params.put("tag", StringUtils.urlEncode(tag));
		params.put("usedMax", usedMax);
		params.put("kernelMax", kernelMax);
		params.put("niceMax", "bbbbt"+niceMax);
		params.put("idleMin", idleMin);
		params.put("ioWaitMax", ioWaitMax);
		
		Response resp = makePostRequest(CPUMonitorAction.editCPUMonitor, params);
		return resp;
	}
	
	public Response getTops(String tag, Integer limit, boolean detailedResults, OutputType output) throws MonitisException {
		return getTops(CPUMonitorAction.topcpu, tag, limit, detailedResults, output);
	}
	
	public static void main(String[] args) {
		CPUMonitor monitor;
		try {
			Response resp = null;
			monitor = new CPUMonitor();

			HashMap<String, String> testparams = new HashMap<String, String>();
			testparams.put("aa", "bbb");
			testparams.put("cccc", "dddd");
			testparams.put("eeee", "ffff");
			resp = monitor.addMonitor("ngaspary_pc", "cpu@ngaspary_pc", "cpu_tag", 10, 10, 10, 10, 10);
			Integer monitorId = new JSONObject(resp.getResponseText()).getJSONObject("data").getInt("testId");
			System.out.println(resp);
			resp = monitor.editMonitor(monitorId, "cpu@ngaspary_pcc", "Defaulot o", 100, 100, 100, 0, 100);
			System.out.println(resp);
			resp = monitor.getMonitorInfo(monitorId, OutputType.XML);
			System.out.println(resp);
			resp = monitor.getMonitors(672, OutputType.XML);
			System.out.println(resp);
			resp = monitor.getTops(null, null, true, OutputType.XML);
			System.out.println(resp);
			resp = monitor.getMonitorResults(monitorId, 2011, 8, 4, 0,  OutputType.XML);
			System.out.println(resp);
			resp = monitor.deleteMonitors(new Integer[]{monitorId});
			System.out.println(resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
