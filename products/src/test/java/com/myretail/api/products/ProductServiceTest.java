package com.myretail.api.products;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

/**
 *  @author Shankar Govindarajan
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestApplication.class)
public class ProductServiceTest {

	private ProductService productService;
	
	@Autowired
	private RestTemplate mockRestTemplate;
	
	@Autowired
	private ProductRepository mockProductRepository;
		
	@Before
	public void setup() {
		productService = new ProductService(mockRestTemplate,mockProductRepository,TestUtils.getDummyExternalApiUrl());
	}
	
	@Test
	public void getProductById_Valid_Product_Id() {
		String dummyProductId = "1234";
		when(mockRestTemplate.getForObject(TestUtils.getDummyExternalApiUrl(), HashMap.class, dummyProductId)).thenReturn(TestUtils.buildDummyExternalApiResponse());		
		when(mockProductRepository.findOne(dummyProductId)).thenReturn(TestUtils.buildDummyProduct());
		Product product = productService.getProductById(dummyProductId);
		assertNotNull(product);
		assertTrue("1234".equals(product.getId()));
		assertTrue("Test Product".equals(product.getName()));
	}
	
	@Test(expected=NoSuchProductException.class)
	public void getProductById_Non_Existent_Id() {
		String dummyProductId = "12345";
		Product dummyProduct = null;
		when(mockProductRepository.findOne(dummyProductId)).thenReturn(dummyProduct);
		productService.getProductById(dummyProductId);
	}
	
	@Test
	public void updateProductPrice_Valid_Product_id() {
		String dummyProductId = "1234";
		when(mockProductRepository.findOne(dummyProductId)).thenReturn(TestUtils.buildDummyProduct());
		when(mockProductRepository.save(any(Product.class))).thenReturn(new Product());
		Product product = productService.updateProductPrice(dummyProductId, new Money(25.00,CurrencyCode.USD));
		assertNotNull(product);
	}
	
	@Test(expected=NoSuchProductException.class)
	public void updateProductPrice_Non_Existent_Id() {
		String dummyProductId = "1234";
		Product dummyProduct = null;
		when(mockProductRepository.findOne(dummyProductId)).thenReturn(dummyProduct);
		when(mockProductRepository.save(any(Product.class))).thenReturn(new Product());
		productService.updateProductPrice(dummyProductId, new Money(25.00,CurrencyCode.USD));
	}

}
