package com.myretail.api.products;

/**
 *  @author Shankar Govindarajan
 */

class ErrorResponse {
	private String message;
	
	ErrorResponse() {}
	
	ErrorResponse(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
