package com.external.api.products;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *  @author Shankar Govindarajan
 */

@RestController
public class ProductController {
	
	@Autowired
	ProductService productService;

	@RequestMapping(value="/products/{id}", method=RequestMethod.GET)
	public Product getProductDetails(@PathVariable String id) {
		return productService.getProductById(id);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	Map<String,String> handleException() {
		return Collections.singletonMap("message", "Product Not Found");
	}
	
}
