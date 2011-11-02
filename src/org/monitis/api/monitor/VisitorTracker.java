package org.monitis.api.monitor;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.monitis.beans.Response;
import org.monitis.enums.MonitorAction;
import org.monitis.enums.OutputType;
import org.monitis.exception.MonitisException;
import org.monitis.utils.Constants;

public class VisitorTracker extends BaseMonitor{

	public enum VisitorTrackingAction{
		visitorTrackingTests(),
		visitorTrackingInfo(),
		visitorTrackingResults();
	}
	
	public VisitorTracker(){
		super();
	}
	public VisitorTracker(String apiKey, String secretKey){
		super(apiKey, secretKey, Constants.API_URL);
	}
	
	protected Enum getAction(MonitorAction action) throws MonitisException {
		switch(action){
			case getMonitors: return VisitorTrackingAction.visitorTrackingTests;	
			case getMonitorInfo: return VisitorTrackingAction.visitorTrackingInfo;
			case getMonitorResults: return VisitorTrackingAction.visitorTrackingResults;
		}
		throw new MonitisException("Action is not supported");
	}
	
	protected String getMonitorIdString(){
		return "siteId";
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Response resp;
			VisitorTracker monitor = new VisitorTracker();
			resp = monitor.getMonitors(OutputType.JSON);
			System.out.println(resp);
			Integer monitorId = new JSONArray(resp.getResponseText()).getInt(0);
			resp = monitor.getMonitorInfo(monitorId, OutputType.JSON);
			System.out.println(resp);
			List<Integer> locIds = new ArrayList<Integer>();
			locIds.add(12);
			locIds.add(19);
			resp = monitor.getMonitorResults(monitorId, 2011, 5, 16, 240, locIds, OutputType.XML);
			System.out.println(resp);
		}catch (Exception e) {
			e.printStackTrace();
		}

	}

}
