package com.myretail.api.products;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

/**
 *  @author Shankar Govindarajan
 */

@Service
class ProductService {
	
	private String productServiceUrl;
	
	private RestTemplate restTemplate;
	
	private ProductRepository productRepository;
	
	@Autowired
	ProductService(RestTemplate restTemplate, ProductRepository productRepository, @Value("${product.service.url}") String productServiceUrl) {
		this.restTemplate = restTemplate;
		this.productRepository = productRepository;
		this.productServiceUrl = productServiceUrl;
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
	
	@SuppressWarnings("unchecked")
	private String getProductDescription(String productId)
	{
		HashMap<String,String> externalApiResult = restTemplate.getForObject(productServiceUrl, HashMap.class, productId);
		Assert.notEmpty(externalApiResult,"Empty or null result from service");
		return externalApiResult.get("description");
	}
	
	private Product getProductFromRepo(String productId)
	{
		Product product = productRepository.findOne(productId);
		if(product == null)
			throw new NoSuchProductException();
		return product;
	}
}
