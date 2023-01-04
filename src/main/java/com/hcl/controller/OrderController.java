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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.dto.BillDTO;
import com.hcl.dto.OrderDTO;
import com.hcl.dto.UserDTO;
import com.hcl.entity.Bill;
import com.hcl.exception.RestaurantException;
import com.hcl.service.BillService;
import com.hcl.service.OrderService;

@RestController
@RequestMapping("/{userId}/order")
public class OrderController {
	
	@Autowired
	OrderService oserv;
	@Autowired
	BillService bserv;
	
	@PutMapping("/cart")
	public ResponseEntity<String> addItemsToCart(@PathVariable Integer userId,OrderDTO order,HttpSession session) throws RestaurantException{

		if(session!=null)
		{
			String type=((UserDTO) session.getAttribute("User")).getType();
			if(type.equalsIgnoreCase("user"))
			{
				oserv.create(userId, order);
				return new ResponseEntity<String>("Order Placed successfully",HttpStatus.OK);
			}
			else
				return new ResponseEntity<String>("Not Allowed",HttpStatus.OK);
		}
		return null;
	}
	
	
	
	@PostMapping("/viewCart")
	public ResponseEntity<List<OrderDTO>> viewCart(@PathVariable Integer userId,HttpSession session) throws RestaurantException
	{
		if(session!=null)
		{
			String type=((UserDTO) session.getAttribute("User")).getType();
			if(type.equalsIgnoreCase("user"))
				return new ResponseEntity<>(oserv.getAllUnPaidOrders(userId),HttpStatus.OK);
		}
		return null;
	}
	
	
	
	@GetMapping("/orderHistory")
	public ResponseEntity<List<OrderDTO>> getorderHistory(@PathVariable Integer userId,HttpSession session) throws RestaurantException{
		
		if(session!=null)
		{
			String type=((UserDTO) session.getAttribute("User")).getType();
			if(type.equalsIgnoreCase("user"))
			{
				List<OrderDTO> orders=oserv.getAllOrders(userId);
				return new ResponseEntity<List<OrderDTO>>(orders,HttpStatus.OK);
			}
		}
		return null;
	}
	
	
	
	@PostMapping("/confirm")
	public ResponseEntity<Bill> confirmOrder(@PathVariable Integer userId,@RequestParam String confirmation,HttpSession session) throws RestaurantException{
		if(session!=null)
		{
			String type=((UserDTO) session.getAttribute("User")).getType();
			if(type.equalsIgnoreCase("user"))
			{
				if(confirmation.equalsIgnoreCase("yes"))
					return new ResponseEntity<>(bserv.addBill(oserv.getAllUnPaidOrders(userId)),HttpStatus.OK);
			}
		}
		return null;
	}
	
	
	
	@DeleteMapping("/{orderId}")
	public ResponseEntity<String> deleteItemsFromCart(@PathVariable Integer orderId,HttpSession session) throws RestaurantException
	{
		if(session!=null)
		{
			String type=((UserDTO) session.getAttribute("User")).getType();
			if(type.equalsIgnoreCase("user"))
			{
				oserv.deleteFromCart(orderId);
				return new ResponseEntity<String>("Order Removed successfully",HttpStatus.OK);
			}
			else
				return new ResponseEntity<String>("Not Allowed",HttpStatus.OK);
		}
		return null;
	}
	
	
	@GetMapping("/userOrders")
	public ResponseEntity<List<OrderDTO>> getUserSpecificOrder(@PathVariable Integer userId,HttpSession session) throws RestaurantException{
		if(session!=null)
		{
			String type=((UserDTO) session.getAttribute("User")).getType();
			if(type.equalsIgnoreCase("admin"))
			{
				List<OrderDTO> orders=oserv.getAllOrders(userId);
				return new ResponseEntity<List<OrderDTO>>(orders,HttpStatus.OK);
			}
		}
		return null;
	}
}
