package com.wiley.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.wiley.bean.Product;
import com.wiley.bean.ProductList;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

public class ProductServiceImpl implements ProductService {

	@Autowired
	private RestTemplate restTemplate;
	
	@Override
	@CircuitBreaker(name="products",fallbackMethod = "getProductFallBack")
	public ProductList getProducts() {
		ProductList products =  restTemplate.getForObject("http://product-service/products", ProductList.class);
		return products;
	}

	public ProductList getProductFallBack() {
		return new ProductList(new ArrayList<Product>());
	}
	
}
