package org.monitis.api.notification;

import java.util.HashMap;

import org.monitis.api.APIObject;
import org.monitis.beans.Response;
import org.monitis.enums.ComparingMethod;
import org.monitis.enums.MonitorType;
import org.monitis.enums.NotificationPeriod;
import org.monitis.enums.OutputType;
import org.monitis.exception.MonitisException;
import org.monitis.utils.Constants;
import org.monitis.utils.StringUtils;

public class NotificationRule extends APIObject {

	public enum NotificationRuleAction{
		addNotificationRule,
		deleteNotificationRule,
		getNotificationRules
	}
	
	public NotificationRule(String apiKey, String secretKey){
		super(apiKey, secretKey, Constants.API_URL);
	}
	
	public NotificationRule(){
		super(Constants.API_URL);
	}
	
	public Response getNotificationRules(Integer monitorId, MonitorType monitorType, OutputType output) throws MonitisException{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("monitorId", monitorId);
		params.put("output", output);
		params.put("monitorType", monitorType.toString());
		return makeGetRequest(NotificationRuleAction.getNotificationRules, params);
	}
	
	public Response addNotificationRule(Integer monitorId, MonitorType monitorType, 
			NotificationPeriod period, Integer weekdayFrom, Integer weekdayTo, String timeFrom, String timeTo,
			String contactGroup, Integer contactId, 
			int failureCount, int notifyBackup, int continuousAlerts, int minFailedLocationCount, 
			String paramName, Object paramValue, ComparingMethod comparingMethod) throws MonitisException{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("monitorId", monitorId);
		params.put("monitorType", monitorType.toString());
		params.put("period", period.toString());
		switch(period){
			case specifiedDays: 
				params.put("weekdayFrom", weekdayFrom);
				params.put("weekdayTo", weekdayTo);
			case specifiedTime:
				params.put("timeFrom", timeFrom);
				params.put("timeTo",timeTo);
				break;
		}
		if(contactGroup != null) params.put("contactGroup", StringUtils.urlEncode(contactGroup));
		if(contactId != null) params.put("contactId", contactId);
		params.put("failureCount", failureCount);
		params.put("notifyBackup", notifyBackup);
		params.put("continuousAlerts", continuousAlerts);
		
		if(monitorType.equals(MonitorType.custom)){
			params.put("paramName", StringUtils.urlEncode(paramName));
			params.put("paramValue", StringUtils.urlEncode(paramValue.toString()));
			params.put("comparingMethod", comparingMethod.toString());
		}else if(monitorType.equals(MonitorType.external) || monitorType.equals(MonitorType.transaction) || monitorType.equals(MonitorType.fullPageLoad)){
			params.put("minFailedLocationCount", minFailedLocationCount);
		}
		return makePostRequest(NotificationRuleAction.addNotificationRule, params);
	}
	
	public Response deleteNotificationRule(Integer monitorId, MonitorType monitorType, Integer[] contactIds) throws MonitisException{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("monitorId", monitorId);
		params.put("monitorType", monitorType.toString());
		params.put("contactIds", StringUtils.join(contactIds, ","));
		return makePostRequest(NotificationRuleAction.deleteNotificationRule, params);
	}
	
		
	public static void main(String[] args) {
		try {
			NotificationRule rule = new NotificationRule();
			Response resp;
			Integer monitorId = 988;
			MonitorType monitorType = MonitorType.load;
			Integer contactId = 10;
			
			resp = rule.addNotificationRule(monitorId, monitorType, NotificationPeriod.specifiedDays, 2, 5, "05:20:00", "08:30:00", 
				null, contactId, 1, 1, 0, 3, "priority", 1, ComparingMethod.greater);  	
			System.out.println(resp);
			
			resp = rule.getNotificationRules(monitorId, monitorType, OutputType.XML);
			System.out.println(resp);

			resp = rule.deleteNotificationRule(monitorId, monitorType, new Integer[]{contactId});
			System.out.println(resp);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
