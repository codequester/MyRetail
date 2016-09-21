package com.myretail.api.products;

import java.util.Collections;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *  @author Shankar Govindarajan
 */

@RestController
public class ProductController {
	
	@Autowired
	private ProductService productService; 
	
	@RequestMapping(value="/products/{id}", method=RequestMethod.GET)
	@ResponseBody
	public Product getProductById(@PathVariable String id) {
		return productService.getProductById(id);
	}
	
	@RequestMapping(value="/products/{id}", method=RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<Map<String, String>> updateProductPrice(@PathVariable String id, @RequestBody @Valid Money productPrice) {
		productService.updateProductPrice(id, productPrice);
		return ResponseEntity.status(HttpStatus.OK).body(Collections.singletonMap("message", "Price updated sucessfully!"));
	}

}
