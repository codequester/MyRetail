package com.external.api.products;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *  @author Shankar Govindarajan
 */

@Document(collection="product")
class Product {
	
	@Id
	private String id;
	private String description;
	
	public Product() {}
	
	public Product(String id, String description) {
		this.id = id;
		this.description = description;
	}
	
		public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}


}
