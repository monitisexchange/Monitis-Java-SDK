package org.monitis.enums;

public enum NotificationPeriod {
	always(1),
	specifiedTime(2),
	specifiedDays(3);
	
	public final int id; 
	
	NotificationPeriod(int id){
		this.id =  id;
	}
}
