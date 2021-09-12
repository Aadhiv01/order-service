package com.wiley.service;

import java.util.List;

import com.wiley.bean.InventoryItem;
import com.wiley.bean.Orders;
import com.wiley.bean.Product;
import com.wiley.bean.UserBean;

public interface OrderService {
	
	public boolean checkUser(String email, boolean isNew);
	public boolean insertUser(UserBean user);
	public UserBean fetchUser(String email);
	public Orders createOrder(Orders order);
	public Orders findOrderById(Long id);
	public List<Product> getAllProducts();
	public List<InventoryItem> getAllItems();
}
