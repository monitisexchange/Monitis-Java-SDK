package org.monitis.exception;

/**
 * 
 * @author ngaspary
 *
 */
public class MonitisException extends Exception {

	private String errorMsg;

	public MonitisException(Exception ex, String errorMsg) {
		this.errorMsg = errorMsg;
		initCause(ex);
	}
	
	public MonitisException(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getErrorMsg() {
		return errorMsg;
	}
}
