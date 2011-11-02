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
public class LoadAverageMonitor extends InternalMonitor{

	public LoadAverageMonitor(String apiKey, String secretKey){
		super(apiKey, secretKey, Constants.API_URL, InternalMonitor.Type.LOAD_AVERAGE);
	}
	
	public LoadAverageMonitor(){
		super(InternalMonitor.Type.LOAD_AVERAGE);
	}
	
	public enum LoadAvgMonitorAction{
		addLoadAverageMonitor,
		editLoadAverageMonitor,
		agentLoadAvg,
		loadAvgInfo,
		loadAvgResult,
		topload1,
		topload5,
		topload15;
	}
	
	protected Enum getAction(MonitorAction action) throws MonitisException{
		switch(action){
			case getMonitors: return LoadAvgMonitorAction.agentLoadAvg;	
			case getMonitorInfo: return  LoadAvgMonitorAction.loadAvgInfo;
			case getMonitorResults: return  LoadAvgMonitorAction.loadAvgResult;
		}
		throw new MonitisException("Action is not supported");
	}
	
	public Response addMonitor(String agentkey, String name, String tag, 
			Float limit1, Float limit5, Float limit15) throws MonitisException{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("agentkey", agentkey);
		params.put("name", StringUtils.urlEncode(name));
		params.put("tag", StringUtils.urlEncode(tag));
		params.put("limit1", limit1);
		params.put("limit5", limit5);
		params.put("limit15", limit15);
		
		Response resp = makePostRequest(LoadAvgMonitorAction.addLoadAverageMonitor, params);
		return resp;
	}
	
	public Response editMonitor(Integer testId, String name, String tag,
			Float limit1, Float limit5, Float limit15)  throws MonitisException{
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("testId", testId);
		params.put("name", StringUtils.urlEncode(name));
		params.put("tag", StringUtils.urlEncode(tag));
		params.put("limit1", limit1);
		params.put("limit5", limit5);
		params.put("limit15", limit15);
		
		Response resp = makePostRequest(LoadAvgMonitorAction.editLoadAverageMonitor, params);
		return resp;
	}

	public Response getTopLoad1(String tag, Integer limit, boolean detailedResults, OutputType output) throws MonitisException {
		return getTops(LoadAvgMonitorAction.topload1, tag, limit, detailedResults, output);
	}
	
	public Response getTopLoad5(String tag, Integer limit, boolean detailedResults, OutputType output) throws MonitisException {
		return getTops(LoadAvgMonitorAction.topload5, tag, limit, detailedResults, output);
	}
	
	public Response getTopLoad15(String tag, Integer limit, boolean detailedResults, OutputType output) throws MonitisException {
		return getTops(LoadAvgMonitorAction.topload15, tag, limit, detailedResults, output);
	}
	
	public static void main(String[] args) {
		LoadAverageMonitor monitor;
		try {
			Response resp = null;
			monitor = new LoadAverageMonitor();
			
			HashMap<String, String> testparams = new HashMap<String, String>();
			testparams.put("aa", "bbb");
			testparams.put("cccc", "dddd");
			testparams.put("eeee", "ffff");
			resp = monitor.addMonitor("rustam_linux", "monitos_LoadAverage", "LoadAverage_tag", (float)10, (float)15, (float)20); 
			Integer monitorId = new JSONObject(resp.getResponseText()).getInt("data");
			System.out.println(resp.getResponseText());
			resp = monitor.editMonitor(monitorId, "monitorus_LoadAverage", "LoadAverage_tag", (float)10, (float)40, (float)50);
			System.out.println(resp.getResponseText());
			resp = monitor.getMonitorResults(monitorId, 2010, 3, 3, 400, null, OutputType.XML);
			System.out.println(resp.getResponseText());
			resp = monitor.getMonitorInfo(monitorId, OutputType.XML);
			System.out.println(resp.getResponseText());
			resp = monitor.getMonitors(3, OutputType.XML);
			System.out.println(resp.getResponseText());
			resp = monitor.getTopLoad1(null, null, true, OutputType.XML);
			System.out.println(resp.getResponseText());
			resp = monitor.getTopLoad5(null, null, true, OutputType.XML);
			System.out.println(resp.getResponseText());
			resp = monitor.getTopLoad15(null, null, true, OutputType.XML);
			System.out.println(resp.getResponseText());			
			resp = monitor.deleteMonitors(new Integer[]{monitorId});
			System.out.println(resp.getResponseText());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
