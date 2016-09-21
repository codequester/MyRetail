package com.myretail.api.products;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *  @author Shankar Govindarajan
 */

@Document(collection="product")
class Product {
	
	@Id
	private String id;
	private String name;
	private Money currentPrice;
	
	public String getId() {
		return id;
	}
	void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	void setName(String name) {
		this.name = name;
	}
	public Money getCurrentPrice() {
		return currentPrice;
	}
	void setCurrentPrice(Money currentPrice) {
		this.currentPrice = currentPrice;
	}
}
