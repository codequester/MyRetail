package com.external.api.products;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *  @author Shankar Govindarajan
 */

@Component
class ProductLoader {
	
	@Autowired
	ProductRepository productRepository;
	
	
	@PostConstruct
	void init() throws Exception {
		productRepository.save(new Product("100001","The Game of Thrones - Vol1"));
		productRepository.save(new Product("100002","Art of Living (Hardcover)"));
		productRepository.save(new Product("100003","Belkin Remote Electrical Outlet"));
		productRepository.save(new Product("100004","The Game of Thrones(Blue Ray) - Vol1"));
		productRepository.save(new Product("100005","Graco Chariot Stroller"));
		productRepository.save(new Product("100006","JayBird Bluetooth Headphone"));
	}

}
