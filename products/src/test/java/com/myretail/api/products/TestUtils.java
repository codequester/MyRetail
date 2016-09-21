package com.myretail.api.products;

import java.util.HashMap;

import org.bson.Document;
/**
 *  @author Shankar Govindarajan
 */

class TestUtils {

	static Product buildDummyProduct() {
		Product dummyProduct = new Product();
		dummyProduct.setId("1234");
		dummyProduct.setCurrentPrice(new Money(25.00, CurrencyCode.USD));
		return dummyProduct;
	}

	static HashMap<String,String> buildDummyExternalApiResponse()
	{
		HashMap<String,String> dummyResponse = new HashMap<String,String>();
		dummyResponse.put("id", "1234");
		dummyResponse.put("description", "Test Product");
		return dummyResponse;
	}
	
	static String getDummyExternalApiUrl() {
		return "http://test.com/products/{id}";
	}
	
	static Document buildTestDocument(String id, String price, String currencyCode)
	{
		return new Document().append("_id", id)
						.append("currentPrice",new Document()
												.append("value",price)
												.append("code", currencyCode));
	}
}
