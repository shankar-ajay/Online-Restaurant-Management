package com.hcl.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.hcl.entity.Order;

public interface OrderRepository extends CrudRepository<Order,Integer>{

	public List<Order> findByUserId(Integer userId);
}
