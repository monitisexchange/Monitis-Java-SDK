package org.monitis.enums;

public enum ComparingMethod {
	equals("equal"),
	greater("more than"),
	less("les than");
	
	public final String urlName;
	
	ComparingMethod(String urlName){
		this.urlName = urlName;
	}
	
}
