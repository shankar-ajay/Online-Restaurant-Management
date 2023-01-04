package com.hcl.repository;

import org.springframework.data.repository.CrudRepository;

import com.hcl.entity.Item;

public interface ItemRepository extends CrudRepository<Item,Integer>{

	public Item findByItemName(String itemName);
}
