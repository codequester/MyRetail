package com.myretail.api.products;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;

/**
 *  @author Shankar Govindarajan
 */

@ControllerAdvice
class ProductExceptionHandler {
	private static Logger logger = LogManager.getLogger(ProductExceptionHandler.class);
	
	@ExceptionHandler(NoSuchProductException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	ErrorResponse handleNoSuchProductException(NoSuchProductException e) {
		logger.error("No Such Product Exception - " + e.getMessage());
		return new ErrorResponse("Product Not Found");
	}
	
	@ExceptionHandler({MethodArgumentNotValidException.class,HttpMessageNotReadableException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	ErrorResponse handleRequestValidationExceptions(Exception e)
	{
		logger.error("Invalid Price Exception - " + e.getMessage());
		return new ErrorResponse("Invalid Price - 'value' should be greater than 0 and 'code' should be a valid currency code");
	}

	@ExceptionHandler(HttpClientErrorException.class)
	@ResponseBody
	ResponseEntity<ErrorResponse> handleServiceResponseException(HttpClientErrorException httpClientException) {
		httpClientException.printStackTrace();
		logger.error("Error while processing the response from external api -" + httpClientException.getResponseBodyAsString());
		if(httpClientException.getRawStatusCode() ==  HttpStatus.NOT_FOUND.value())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Service Error - Product not found"));
		else
			return ResponseEntity.status(httpClientException.getRawStatusCode()).body(new ErrorResponse("Service Error - "+httpClientException.getMessage()));
	}

	@ExceptionHandler(RestClientException.class)
	@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
	@ResponseBody
	ErrorResponse handleResourceNotAvailableException(Exception e) {
		logger.error("Unable to connect to the external api - " + e.getMessage());
		return new ErrorResponse("Service not avaialble - Unable to fetch the product description");
	}	
}
