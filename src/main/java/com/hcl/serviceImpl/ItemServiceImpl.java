package com.hcl.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hcl.dto.ItemDTO;
import com.hcl.entity.Item;
import com.hcl.exception.RestaurantException;
import com.hcl.repository.ItemRepository;
import com.hcl.service.ItemService;


@Service
@Transactional
public class ItemServiceImpl implements ItemService{

	@Autowired
	ItemRepository repo;
	
	//To add item in the menu.
	
	@Override
	public void addItem(ItemDTO item){
		repo.save(this.getItemEntity(item));
	}
	
	//To get a particular item by Id in the menu.
	
	@Override
	public ItemDTO getItemById(Integer id) throws RestaurantException {
		Optional<Item> op=repo.findById(id);
		Item item=op.orElseThrow(()->new RestaurantException("Item Not Found"));
		return this.getItemDTO(item);
	}
	
	//To get the whole menu.
	
	@Override
	public List<ItemDTO> getAllItems(){
		Iterable<Item> itr=repo.findAll();
		List<ItemDTO> items=new ArrayList<ItemDTO>();
		itr.forEach(i->items.add(this.getItemDTO(i)));
		return items;
	}
	
	//To delete an item from the menu.
	
	@Override
	public void deleteItemById(Integer id) throws RestaurantException {
		Optional<Item> op=repo.findById(id);
		Item item=op.orElseThrow(()->new RestaurantException("Item Not Found"));
		repo.delete(item);
	}
	
	//To update any items details.
	
	@Override
	public String updateItem(ItemDTO item){
		repo.save(this.getItemEntity(item));
		return "Item updated Successfully";
	}
	
	//Utility function to perform conversion.
	
	@Override
	public ItemDTO getItemDTO(Item item) {
		ItemDTO itemDTO=new ItemDTO();
		itemDTO.setItemId(item.getItemId());
		itemDTO.setItemName(item.getItemName());
		itemDTO.setPrice(item.getPrice());
		
		return itemDTO;
	}
	
	//Utility function to perform conversion.
	
	@Override
	public Item getItemEntity(ItemDTO itemDTO) {
		Item item=new Item();
		item.setItemId(itemDTO.getItemId());
		item.setItemName(itemDTO.getItemName());
		item.setPrice(itemDTO.getPrice());
		
		return item;
	}
}
