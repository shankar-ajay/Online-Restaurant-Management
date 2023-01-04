package com.hcl.service;

import java.util.List;

import com.hcl.dto.ItemDTO;
import com.hcl.entity.Item;
import com.hcl.exception.RestaurantException;

public interface ItemService {

	void addItem(ItemDTO item);

	ItemDTO getItemById(Integer id) throws RestaurantException;

	List<ItemDTO> getAllItems();

	void deleteItemById(Integer id) throws RestaurantException;

	String updateItem(ItemDTO item);

	ItemDTO getItemDTO(Item item);

	Item getItemEntity(ItemDTO itemDTO);

}