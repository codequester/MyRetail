package com.myretail.api.products;

import java.io.IOException;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *  @author Shankar Govindarajan
 */

@Service
class ProductService {
	
	private static final Logger logger = LogManager.getLogger(ProductService.class);
	
	private String productServiceHostUrl;
	
	private RestTemplate restTemplate;
	
	private ProductRepository productRepository;
	
	@Autowired
	ProductService(RestTemplate restTemplate, ProductRepository productRepository, @Value("${product.service.url}") String productServiceHostUrl) {
		this.restTemplate = restTemplate;
		this.productRepository = productRepository;
		this.productServiceHostUrl = productServiceHostUrl;
	}
	
	Product getProductById(String productId)
	{
		Product product = getProductFromRepo(productId);
		product.setName(getProductDescription(productId));
		return product;
	}
	
	Product updateProductPrice(String productId, Money productPrice)
	{
		Product product = getProductFromRepo(productId);
		product.setCurrentPrice(productPrice);
		return productRepository.save(product);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private String getProductDescription(String productId)
	{
		String description = "";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", String.format("Bearer %s", getAuthorizationTokenForRestService()));
        HttpEntity httpRequest = new HttpEntity(headers);

        HttpEntity<String> response = restTemplate.exchange(productServiceHostUrl+"/external-api/products/{id}", HttpMethod.GET, httpRequest, String.class, productId);
		String jsonResponse = response.getBody();
		Assert.notNull(jsonResponse,"Empty or null result from service");
		try {
			Map<String, Object> map = new ObjectMapper().readValue(jsonResponse, Map.class);
			Assert.notEmpty(map,"Empty or null result from service");
			description =  map.get("description")+"";
		}
		catch (IOException e) {
			logger.error("Error while processing the response to get the product description-" + e.getMessage());
		}
		return description;
	}
	
	private Product getProductFromRepo(String productId)
	{
		Product product = productRepository.findOne(productId);
		if(product == null)
			throw new NoSuchProductException();
		return product;
	}
	
   @SuppressWarnings("unchecked")
    private String getAuthorizationTokenForRestService() {
	   MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
       parameters.add("client_id", "my-client-with-secret");
       parameters.add("client_secret", "secret");
       parameters.add("grant_type", "client_credentials");

	   HttpHeaders headers = new HttpHeaders();
	   headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);      

	   HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(parameters, headers);
	   Map<Object, Object> authToken = restTemplate.postForObject(productServiceHostUrl + "/external-api/oauth/token", request, Map.class); 	      
       Assert.notNull(authToken.get("access_token"),"Error calling Product Service token endpoint. Token response received: "+ authToken);      
       return (String) authToken.get("access_token");
    }
}
