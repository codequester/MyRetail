package com.myretail.api.products;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestClientException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

/**
 *  @author Shankar Govindarajan
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestApplication.class,DelegatingWebMvcConfiguration.class})
@WebAppConfiguration
public class ProductControllerTest {
	
    private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(
    		MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Autowired
	private ProductService mockProductService;
	
	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		Mockito.reset(mockProductService);
	}
	
	@Test
	public void getProductById_Valid_Product_Id() throws Exception {
		String dummyProductId = "1234";
		when(mockProductService.getProductById(dummyProductId)).thenReturn(TestUtils.buildDummyProduct());
		MockHttpServletRequestBuilder requestBuilder = get("/products/{id}",dummyProductId);
		mockMvc.perform(requestBuilder)
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.id").value("1234"));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void getProductById_Non_Existent_Id() throws Exception {
		String dummyProductId = "12345";
		when(mockProductService.getProductById(dummyProductId)).thenThrow(NoSuchProductException.class);
		MockHttpServletRequestBuilder requestBuilder = get("/products/{id}",dummyProductId);
		mockMvc.perform(requestBuilder)
			.andExpect(status().isNotFound())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.message").value("Product Not Found"));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void getProductById_Unavailable_External_Service() throws Exception {
		String dummyProductId = "12345";
		when(mockProductService.getProductById(dummyProductId)).thenThrow(RestClientException.class);
		MockHttpServletRequestBuilder requestBuilder = get("/products/{id}",dummyProductId);
		mockMvc.perform(requestBuilder)
			.andExpect(status().isServiceUnavailable())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.message").value("Service not avaialble - Unable to fetch the product description"));
	}

	@Test
	public void updateProductPrice_Valid_Product_Id_Valid_Price() throws Exception {
		String dummyProductId = "1234";
		Money dummyPrice = new Money(25.00, CurrencyCode.USD);
		ObjectWriter jsonWriter = new ObjectMapper().writer();
		String requestBody = jsonWriter.writeValueAsString(dummyPrice);
		when(mockProductService.updateProductPrice(dummyProductId,dummyPrice)).thenReturn(TestUtils.buildDummyProduct());
		MockHttpServletRequestBuilder requestBuilder = put("/products/{id}",dummyProductId).contentType(APPLICATION_JSON_UTF8).content(requestBody);
		mockMvc.perform(requestBuilder)
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.message").value("Price updated sucessfully!"));
	}
		
	@Test
	public void updateProductPrice_Valid_Product_Id_No_Price() throws Exception {
		String dummyProductId = "12345";
		Money dummyPrice = new Money(25.00, CurrencyCode.USD);
		when(mockProductService.updateProductPrice(dummyProductId,dummyPrice)).thenReturn(TestUtils.buildDummyProduct());
		MockHttpServletRequestBuilder requestBuilder = put("/products/{id}",dummyProductId);
		mockMvc.perform(requestBuilder)
			.andExpect(status().isBadRequest())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.message").value("Invalid Price - 'value' should be greater than 0 and 'code' should be a valid currency code"));
	}

}
