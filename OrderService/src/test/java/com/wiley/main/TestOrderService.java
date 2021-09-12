package com.wiley.main;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.wiley.bean.Orders;
import com.wiley.service.OrderService;
import com.wiley.service.OrderServiceImpl;

class TestOrderService {
	
	OrderService orderService;

	@BeforeEach
	void setUp() throws Exception {
		orderService = new OrderServiceImpl();
	}

	@AfterEach
	void tearDown() throws Exception {
		orderService = null;
	}
	
	static Stream<Arguments> checkEmailIds(){
		return Stream.of(
				Arguments.arguments("abc@gmail.com",false,true),
				Arguments.arguments("xyz@gmail.com",false,true),
				Arguments.arguments("lmn@gmail.com",true,true)
				);
	}

	@ParameterizedTest
	@MethodSource("checkEmailIds")
	@Test
	void testCheckUser(String email, boolean isNew, boolean expected) {
		assertEquals(expected, orderService.checkUser(email, isNew));
	}

	@Test
	void testInsertUser() {
		System.out.println("Not yet implemented");
	}

	@Test
	void testFetchUser() {
		assertEquals(true, orderService.fetchUser("abc@gmail.com")!=null);
	}

	@Test
	void testCreateOrder() {
		assertEquals(null,orderService.createOrder(new Orders(10L,"abc@gmail.com","abcd lane",new ArrayList<>())));
	}

	@Test
	void testFindOrderById() {
		assertEquals(null,orderService.findOrderById(40L));
	}

}
