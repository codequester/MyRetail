package com.myretail.api.products;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *  @author Shankar Govindarajan
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=RepositoryContext.class)
public class ProductRepositoryTest {

	@Autowired
	private ProductRepository productRepository;
	
	@Test
	public void findOne_Valid_Document() {
		Product product = productRepository.findOne("100");
		assertNotNull(product);
		assertTrue("100".equals(product.getId()));
		assertTrue(12.35 == product.getCurrentPrice().getValue());
		assertTrue("USD".equals(product.getCurrentPrice().getCode().name()));
	}
	
	@Test
	public void findOne_Non_Existent_Document() {
		Product product = productRepository.findOne("512340");
		assertNull(product);
	}
	
	@Test
	public void save_Valid_Document() {
		Product product = new Product();
		product.setId("123456");
		product.setCurrentPrice(new Money(25.00,CurrencyCode.USD));
		assertNotNull(productRepository.save(product));
		Product product1 = productRepository.findOne("123456");
		assertNotNull(product1);
	}
}
