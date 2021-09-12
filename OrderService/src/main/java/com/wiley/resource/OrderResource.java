package com.wiley.resource;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.wiley.bean.InventoryItem;
import com.wiley.bean.OrderItem;
import com.wiley.bean.Orders;
import com.wiley.bean.Product;
import com.wiley.bean.UserBean;
import com.wiley.service.OrderService;

@RestController
public class OrderResource {

	@Autowired
	OrderService orderService;
	
	@CrossOrigin
	@PostMapping(path = "/login",produces = "Application/json",consumes="Application/json")
	UserBean userLogin(@RequestBody UserBean user) {
			if(!orderService.checkUser(user.getEmail(), false))
				return null;
			if(!(orderService.fetchUser(user.getEmail()).getPassword().equals(user.getPassword())))
				return null;
		return orderService.fetchUser(user.getEmail());
	}
	
	@CrossOrigin
	@PostMapping(path = "/signUp",consumes="Application/json")
	boolean newUser(@RequestBody UserBean user) {
			if(!orderService.checkUser(user.getEmail(), true))
				return false;
		return orderService.insertUser(user);
	}
	
	@CrossOrigin
	@GetMapping(path = "/products",produces = "Application/json")
	List<Product> getAllProducts(){
		return orderService.getAllProducts();
	}
	
	@CrossOrigin
	@GetMapping(path = "/items",produces = "Application/json")
	List<InventoryItem> getAllItems(){
		return orderService.getAllItems();
	}
	
	@CrossOrigin
	@PostMapping(path="/addItem",consumes="Application/json")
	public boolean addPurchase(@RequestBody OrderItem item, HttpSession session) {
		System.out.println(item);
		Optional<Product> product = getAllProducts().stream().filter(e->e.getId()==item.getProductId()).findAny();
		if(product.isEmpty())
			return false;
		Optional<InventoryItem> inventoryItem = getAllItems().stream().filter(e->e.getProductCode().equals(product.get().getCode())).findAny();
		if(inventoryItem.isEmpty() || inventoryItem.get().getAvailableQuantity() < item.getQuantity()) {
			return false;
		}
		List<OrderItem> orderItems = (List<OrderItem>) session.getAttribute("items");
		if(orderItems == null) 
			orderItems = new ArrayList<>();
		Optional<OrderItem> existingItem = orderItems.stream().filter(e->e.getProductId()==item.getProductId()).findAny();
		if(existingItem.isPresent()) {
			existingItem.get().setQuantity(existingItem.get().getQuantity()+item.getQuantity());
			return true;
		}
		orderItems.add(item);
		session.removeAttribute("items");
		session.setAttribute("items", orderItems);
		System.out.println(orderItems);
		return true;
	}
	
	@CrossOrigin
	@GetMapping(path = "/fetchItems",produces = "Application/json")
	List<OrderItem> getCurrentItems(HttpSession session){
		return (List<OrderItem>) session.getAttribute("items");
	}
	
	@CrossOrigin
	@GetMapping(path = "/fetchTotal",produces = "Application/json")
	double fetchTotal(@RequestBody List<OrderItem> items) {
		List<OrderItem> orderItems = items;
		double total = 0;
		for(OrderItem item : orderItems) {
			Product product = getAllProducts().stream().filter(e->e.getId()==item.getProductId()).findAny().get();
			Optional<InventoryItem> inventoryItem = getAllItems().stream().filter(e->e.getProductCode().equals(product.getCode())).findAny();
			if(inventoryItem.isEmpty() || inventoryItem.get().getAvailableQuantity() < item.getQuantity()) {
				return 0;
			}
			total += item.getQuantity()*product.getPrice();
		}
		return total;
	}
	
	@CrossOrigin
	@PostMapping(path = "/order",produces = "Application/json",consumes="Application/json")
	Orders createOrder(@RequestBody Orders order, HttpSession session){
		if(order == null || order.getItems().stream().filter(e->e.getQuantity()<0).findAny().isPresent()) {
			return null;
		}
		Orders newOrder = orderService.createOrder(order);
		if(newOrder == null)
			return null;
		session.removeAttribute("items");
		session.setAttribute("items", newOrder.getItems());
		session.setAttribute("order", newOrder);
		return newOrder;
	}
	
	@CrossOrigin
	@GetMapping(path = "/order/id/{id}",produces = "Application/json")
	ResponseEntity<Orders> findOrderById(@PathVariable("id") Long id) {
		Orders order = orderService.findOrderById(id);
		if(order != null)
			return ResponseEntity.status(HttpStatus.OK).body(order);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}
	
	@CrossOrigin
	@GetMapping(path = "/fetchOrder",produces = "Application/json")
	Orders fetchOrder(HttpSession session) {
		return (Orders) session.getAttribute("order");
	}
}
