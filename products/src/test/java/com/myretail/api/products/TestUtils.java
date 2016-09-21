package com.myretail.api.products;

import java.util.HashMap;
import java.util.Map;

import org.bson.Document;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

	static ResponseEntity<String> buildDummyExternalApiResponse()
	{
		ResponseEntity<String> dummyResponse = new ResponseEntity<String>("{\"id\":\"1234\",\"description\":\"Test Product\"}",HttpStatus.OK);
		return dummyResponse;
	}
	
	static String getDummyExternalApiUrl() {
		return "http://test.com/products/{id}";
	}
	
	static Map<Object, Object> buildDummyToken() {
		Map<Object, Object> tokenMap = new HashMap<>();
		tokenMap.put("access_token", "ASDFEerf1234e");
		return tokenMap;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	static HttpEntity buildDummyRequest() {
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.add("Authorization", String.format("Bearer %s", "ASDFEerf1234e"));
		HttpEntity httpRequest = new HttpEntity(headers);
	    return httpRequest;
	}
	
	static Document buildTestDocument(String id, String price, String currencyCode)
	{
		return new Document().append("_id", id)
						.append("currentPrice",new Document()
												.append("value",price)
												.append("code", currencyCode));
	}
}
