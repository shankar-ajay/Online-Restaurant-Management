package com.hcl.service;

import java.util.List;

import com.hcl.dto.OrderDTO;
import com.hcl.entity.Order;
import com.hcl.exception.RestaurantException;

public interface OrderService {

	public List<OrderDTO> getAllOrders(Integer userId) throws RestaurantException;

	public void create(Integer userId,OrderDTO order) throws RestaurantException;

	public OrderDTO getOrderDTO(Order orderEntity);

	public Order getOrderEntity(OrderDTO orderDTO) throws RestaurantException;

	public List<OrderDTO> getAllUnPaidOrders(Integer userId) throws RestaurantException;

	public void deleteFromCart(Integer orderId) throws RestaurantException;
	
	public List<Order> getUserSpecificOrders(Integer userId);

}