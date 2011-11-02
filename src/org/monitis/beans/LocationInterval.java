package org.monitis.beans;

/**
 * 
 * @author ngaspary
 *
 */
public class LocationInterval {
	private int location;
	private int interval;
	
	/**
	 * 
	 * @param location
	 * @param interval
	 */
	public LocationInterval(int location, int interval) {
		super();
		this.location = location;
		this.interval = interval;
	}
	/**
	 * @return the interval
	 */
	public int getInterval() {
		return interval;
	}
	/**
	 * @param interval the interval to set
	 */
	public void setInterval(int interval) {
		this.interval = interval;
	}
	/**
	 * @return the location
	 */
	public int getLocation() {
		return location;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(int location) {
		this.location = location;
	}
	
	public String toString(){
		return location+"-"+interval;
	}
	
}
