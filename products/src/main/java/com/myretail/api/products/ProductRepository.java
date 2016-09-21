package com.myretail.api.products;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *  @author Shankar Govindarajan
 */

interface ProductRepository extends MongoRepository<Product, String> {
}
