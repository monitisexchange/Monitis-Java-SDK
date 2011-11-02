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
public class DriveMonitor extends InternalMonitor{

	public DriveMonitor(String apiKey, String secretKey){
		super(apiKey, secretKey, Constants.API_URL, InternalMonitor.Type.DRIVE);
	}
	
	public DriveMonitor() {
		super(InternalMonitor.Type.DRIVE);
	}

	public enum DriveMonitorAction{
		addDriveMonitor,
		editDriveMonitor,
		driveInfo,
		agentDrives,
		driveResult,
		topdrive;
	}
	
	protected Enum getAction(MonitorAction action)throws MonitisException{
		switch(action){
			case getMonitors: return DriveMonitorAction.agentDrives;	
			case getMonitorInfo: return  DriveMonitorAction.driveInfo;
			case getMonitorResults: return  DriveMonitorAction.driveResult;
		}
		throw new MonitisException("Action is not supported");
	}
	
	public Response addMonitor(String agentkey, String name, String tag, 
			String driveLetter, Integer freeLimit) throws MonitisException{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("agentkey", agentkey);
		params.put("name", StringUtils.urlEncode(name));
		params.put("tag", StringUtils.urlEncode(tag));
		params.put("driveLetter", driveLetter);
		params.put("freeLimit", freeLimit);
		
		Response resp = makePostRequest(DriveMonitorAction.addDriveMonitor, params);
		return resp;
	}
	
	public Response editMonitor(Integer testId, String name, String tag,
			Integer freeLimit)  throws MonitisException{
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("testId", testId);
		params.put("name", StringUtils.urlEncode(name));
		params.put("tag", StringUtils.urlEncode(tag));
		params.put("freeLimit", freeLimit);
		
		Response resp = makePostRequest(DriveMonitorAction.editDriveMonitor, params);
		return resp;
	}
	
	public Response getTops(String tag, Integer limit, boolean detailedResults, OutputType output) throws MonitisException {
		return getTops(DriveMonitorAction.topdrive, tag, limit, detailedResults, output);
	}
	
	public static void main(String[] args) {
		DriveMonitor monitor;
		try {
			Response resp = null;
			monitor = new DriveMonitor();
			HashMap<String, String> testparams = new HashMap<String, String>();
			testparams.put("aa", "bbb");
			testparams.put("cccc", "dddd");
			testparams.put("eeee", "ffff");
			resp = monitor.addMonitor("rustam_pc", "ngaspardrive_c", "rd tag", "r", 5);
			Integer monitorId = new JSONObject(resp.getResponseText()).getJSONObject("data").getInt("testId");
			System.out.println(resp.getResponseText());
			resp = monitor.editMonitor(monitorId, "ngaspary_drive_c", "rred_ta g", 2);
			System.out.println(resp.getResponseText());
			resp = monitor.getMonitorInfo(monitorId, OutputType.XML);
			System.out.println(resp.getResponseText());
			resp = monitor.getMonitors(672, OutputType.XML);
			System.out.println(resp.getResponseText());
			resp = monitor.getTops(null, 10, true, OutputType.XML);
			System.out.println(resp.getResponseText());
			resp = monitor.getMonitorResults(monitorId, 2011, 8, 4, 0,  OutputType.XML);
			System.out.println(resp.getResponseText());
			resp = monitor.deleteMonitors(new Integer[]{monitorId});
			System.out.println(resp.getResponseText());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
