package com.hcl.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.dto.ItemDTO;
import com.hcl.dto.UserDTO;
import com.hcl.exception.RestaurantException;
import com.hcl.service.ItemService;

@RestController
@RequestMapping("/Items")
public class ItemController {

	@Autowired
	ItemService iserv;
	
	//Successful Execution
	@GetMapping("/find/{itemId}")
	public ResponseEntity<ItemDTO> getItem(@PathVariable Integer itemId,HttpSession session) throws RestaurantException{
		if(session!=null)
		{
			String type=((UserDTO) session.getAttribute("User")).getType();
			if(type.equalsIgnoreCase("admin"))
				return new ResponseEntity<ItemDTO>(iserv.getItemById(itemId),HttpStatus.OK);
		}
		return null;
	}
	
	
	@PostMapping("/add")
	public ResponseEntity<String> addItem(@RequestBody ItemDTO item,HttpSession session){
		if(session!=null)
		{
			String type=((UserDTO) session.getAttribute("User")).getType();
			if(type.equalsIgnoreCase("admin"))
			{
				iserv.addItem(item);
				return new ResponseEntity<String>("Item added Successfully",HttpStatus.OK);
			}
		}
		return null;
	}
	
	
	
	@PostMapping("/update")
	public ResponseEntity<String> updateItem(@RequestBody ItemDTO item,HttpSession session){
		if(session!=null)
		{
			String type=((UserDTO) session.getAttribute("User")).getType();
			if(type.equalsIgnoreCase("admin"))
			{
				return new ResponseEntity<String>(iserv.updateItem(item),HttpStatus.OK);
			}
		}
		return null;
		
	}
	
	
	@DeleteMapping("/delete/{itemId}")
	public ResponseEntity<String> deleteItem(@PathVariable Integer itemId,HttpSession session) throws RestaurantException{
		
		if(session!=null)
		{
			String type=((UserDTO) session.getAttribute("User")).getType();
			if(type.equalsIgnoreCase("admin"))
			{
				iserv.deleteItemById(itemId);
				return new ResponseEntity<String>("Item Deleted Successfully",HttpStatus.OK);
			}
		}
		return null;
		
	}
	
	
	//Successful Execution
	@GetMapping("/menu")
	public ResponseEntity<List<ItemDTO>> getAllItems(HttpSession session){
		
		List<ItemDTO> menu=iserv.getAllItems();
		return new ResponseEntity<List<ItemDTO>>(menu,HttpStatus.OK);
	}
}
