package com.myretail.api.products;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

/**
 *  @author Shankar Govindarajan
 */

@Configuration
@ComponentScan(excludeFilters = {
									@Filter(type=FilterType.ASSIGNABLE_TYPE,classes=Application.class),
									@Filter(type=FilterType.ASSIGNABLE_TYPE,classes=RepositoryContext.class),
									@Filter(type=FilterType.ANNOTATION,classes=Service.class),
									@Filter(type=FilterType.ANNOTATION,classes=Repository.class)
								})
public class TestApplication {

	@Bean
	public RestTemplate mockRestTemplate() {
		return Mockito.mock(RestTemplate.class);
	}

	@Bean
	public ProductRepository mockProductRepository(){
		return Mockito.mock(ProductRepository.class);
	}
	
	@Bean
	public ProductService mockProductService() {
		return Mockito.mock(ProductService.class);
	}
}
