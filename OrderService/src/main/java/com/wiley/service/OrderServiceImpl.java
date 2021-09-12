package com.wiley.service;


import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wiley.bean.InventoryItem;
import com.wiley.bean.InventoryList;
import com.wiley.bean.OrderItem;
import com.wiley.bean.Orders;
import com.wiley.bean.Product;
import com.wiley.bean.ProductList;
import com.wiley.bean.UserBean;
import com.wiley.persistence.OrderDao;
import com.wiley.persistence.OrderItemDao;
import com.wiley.persistence.UserDao;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private UserDao userDao;
	@Autowired
	OrderDao orderDao;
	@Autowired
	OrderItemDao orderItemDao;
	@Autowired
	private ProductService productService;
	@Autowired
	private InventoryService inventoryService;
	
	@Override
	public boolean checkUser(String email, boolean isNew) {
		UserBean user = userDao.findByEmail(email);
		if(isNew && user.getName() != null)
			return false;
		else if(!isNew && user.getName() == null)
			return false;
		return true;
	}
	
	@Override
	public boolean insertUser(UserBean user){
		return userDao.save(user) != null;
	}
	
	@Override
	public UserBean fetchUser(String email){
		return userDao.findByEmail(email);
	}
	
	@Override
	public List<Product> getAllProducts() {
		ProductList productList = productService.getProducts();
		List<Product> products = productList.getProducts();
		return products;
	}
	
	@Override
	public List<InventoryItem> getAllItems(){
		InventoryList inventoryList = inventoryService.getInventories();
		List<InventoryItem> items = inventoryList.getItems();
		return items;
	}
	
	@Override
	public Orders createOrder(Orders orders) {
		ProductList productList = productService.getProducts();
		List<Product> products = productList.getProducts();
		InventoryList inventoryList = inventoryService.getInventories();
		List<InventoryItem> items = inventoryList.getItems();
		for(OrderItem order : orders.getItems()) {
			Optional<Product> product = products.stream().filter(e->e.getId()==order.getProductId()).findAny();
			if(product.isEmpty()) {
				System.out.println("Product Empty");
				return null;
			}
			System.out.println(product.get());
			Optional<InventoryItem> item = items.stream().filter(e->e.getProductCode().equals(product.get().getCode())).findAny();
			if(item.isEmpty() || order.getQuantity() > item.get().getAvailableQuantity()) {
				System.out.println(item.isEmpty() + "Item Empty");
				return null;
			}
			order.setProductPrice(new BigDecimal(product.get().getPrice()));
		}
		orders.getItems().forEach(e->orderItemDao.save(e));
		return orderDao.save(orders);
	}

	@Override
	public Orders findOrderById(Long id) {
		return orderDao.getById(id);
	}

}
