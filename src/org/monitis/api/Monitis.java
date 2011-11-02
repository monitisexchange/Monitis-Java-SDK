package org.monitis.api;

import org.monitis.api.layout.Layout;
import org.monitis.api.monitor.Agent;
import org.monitis.api.monitor.CPUMonitor;
import org.monitis.api.monitor.CustomAgentJob;
import org.monitis.api.monitor.CustomMonitor;
import org.monitis.api.monitor.CustomUserAgent;
import org.monitis.api.monitor.DriveMonitor;
import org.monitis.api.monitor.ExternalMonitor;
import org.monitis.api.monitor.FullpageloadMonitor;
import org.monitis.api.monitor.LoadAverageMonitor;
import org.monitis.api.monitor.MemoryMonitor;
import org.monitis.api.monitor.ProcessMonitor;
import org.monitis.api.monitor.TransactionMonitor;
import org.monitis.api.monitor.VisitorTracker;
import org.monitis.api.notification.NotificationRule;
import org.monitis.api.user.Contact;
import org.monitis.api.user.SubAccount;
import org.monitis.beans.Response;
import org.monitis.enums.OutputType;
import org.monitis.exception.MonitisException;
import org.monitis.utils.Constants;

public class Monitis {
	
	public enum APIObjectType{
		authentication(Authentication.class),
		
		externalMonitor(ExternalMonitor.class),
		
		agent(Agent.class),
		cpuMonitor(CPUMonitor.class),
		loadAverageMonitor(LoadAverageMonitor.class),
		driveMonitor(DriveMonitor.class),
		processmonitor(ProcessMonitor.class),
		memoryMonitor(MemoryMonitor.class),
		
		transactionMonitor(TransactionMonitor.class),
		fullpageloadMonitor(FullpageloadMonitor.class),
		
		visitorTracker(VisitorTracker.class),
		
		customMonitor(CustomMonitor.class),
		customUserAgent(CustomUserAgent.class),
		customAgentJob(CustomAgentJob.class),
		
		layout(Layout.class),
		
		notificationRule(NotificationRule.class),
		contact(Contact.class),
		
		subAccount(SubAccount.class);
		
		public final Class<?extends APIObject> cls;
		
		APIObjectType(Class<?extends APIObject> cls){
			this.cls = cls;
		}
	}
	
	public final String apiKey;
	public final String secretKey;
	
	public Monitis(String apiKey, String secretKey) {
		this.apiKey = apiKey;
		this.secretKey = secretKey;
	}
	
	public Monitis() {
		this.apiKey = Constants.API_KEY;
		this.secretKey = Constants.SECRET_KEY;
	}

	
	public APIObject getInstanceOf(APIObjectType apiDataObjectType){
		try {
			return apiDataObjectType.cls.getConstructor(new Class[]{String.class, String.class}).newInstance(new Object[]{apiKey, secretKey});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		ExternalMonitor extmon = (ExternalMonitor)new Monitis().getInstanceOf(APIObjectType.externalMonitor);
		try {
			Response resp = extmon.getMonitors(OutputType.XML);
			System.out.println(resp);
		} catch (MonitisException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
