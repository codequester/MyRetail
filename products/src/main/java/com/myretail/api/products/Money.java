package com.myretail.api.products;

/**
 *  @author Shankar Govindarajan
 */

import javax.validation.constraints.AssertTrue;

class Money {
	private double value;
	private CurrencyCode code;
	
	@SuppressWarnings("unused")
	private Money() {}
	
	Money(double value) {
		this.value = value;
		this.code = CurrencyCode.USD;
	}
	
	Money(double value, CurrencyCode code) {
		this.value = value;
		this.code = code;
	}
	
	public Double getValue() {
		return value;
	}
	
	void setValue(Double value) {
		this.value = value;
	}
	
	public CurrencyCode getCode() {
		return code;
	}
	
	void setCode(CurrencyCode code) {
		this.code = code;
	}
	
	@AssertTrue
	boolean isValid() {
		return value > 0 && code!=null;
	}
}
