package com.wiley.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.wiley.bean.InventoryItem;
import com.wiley.bean.InventoryList;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

public class InventoryServiceImpl implements InventoryService {


	@Autowired
	private RestTemplate restTemplate;
	
	@Override
	@CircuitBreaker(name="products",fallbackMethod = "getProductFallBack")
	public InventoryList getInventories() {
		InventoryList inventories = restTemplate.getForObject("http://inventory-service/inventories", InventoryList.class);
		return inventories;
	}
	
	public InventoryList getProductFallBack() {
		return new InventoryList(new ArrayList<InventoryItem>());
	}

}
