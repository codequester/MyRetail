package com.external.api.products;

import org.springframework.data.repository.Repository;

/**
 *  @author Shankar Govindarajan
 */

@org.springframework.stereotype.Repository
interface ProductRepository extends Repository<Product, String> {
	Product findOne(String productId);
	Product save(Product product);
}
