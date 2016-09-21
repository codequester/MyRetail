package com.external.api.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 *  @author Shankar Govindarajan
 */

@Service
class ProductService {
	
	@Autowired
	ProductRepository productRepository;
	
	Product getProductById(String productId) {
		Product product =  productRepository.findOne(productId);
		Assert.notNull(product,"Product not found for id -" + productId);
		return product;
	}

}
