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
import org.springframework.web.bind.annotation.RestController;

import com.hcl.dto.UserDTO;
import com.hcl.entity.User;
import com.hcl.exception.RestaurantException;
import com.hcl.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userv;
	
	@PutMapping("/register")
	public ResponseEntity<Integer> addUser(@RequestBody UserDTO user){
		System.out.println(user);
		int id=userv.addUser(user);
		return  new ResponseEntity<Integer>(id,HttpStatus.OK);
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserDTO> getUserDetails(@PathVariable Integer userId,HttpSession session) throws RestaurantException{
		
		ResponseEntity<UserDTO> re = null;
		if(session!=null)
		{
			String type=((UserDTO) session.getAttribute("User")).getType();
			if(type.equalsIgnoreCase("admin"))
			{
				UserDTO user=userv.getUserDetails(userId);
				re= new ResponseEntity<UserDTO>(user,HttpStatus.OK);
			}	
		}
		return re;
		
	}
	@DeleteMapping("/delete/{userId}")
	public ResponseEntity<String> deleteUserDetails(@PathVariable Integer userId,HttpSession session)
	{
		if(session!=null)
		{
			String type=((UserDTO) session.getAttribute("User")).getType();
			if(type.equalsIgnoreCase("admin"))
			{
				userv.deleteUser(userId);
				return new ResponseEntity<String>("User Details Deleted Successfully",HttpStatus.OK);
			}
		}
		return new ResponseEntity<>("Not Allowed",HttpStatus.OK);
	}
	@GetMapping("/update")
	public ResponseEntity<String> updateUserDetails(@RequestBody UserDTO user,HttpSession session){
		return new ResponseEntity<String>(userv.updateUser(user),HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> userLogin(@RequestBody UserDTO userDTO,HttpSession session){
		UserDTO user=userv.authenticate(userDTO.getUserId(), userDTO.getPassword());
		if(user!=null)
		{
			session.setAttribute("User", user);
			if(user.getType().equals("admin"))
				return new ResponseEntity<String>("Admin LogIn Successful",HttpStatus.OK);
			else
				return new ResponseEntity<String>("User LogIn Successful",HttpStatus.OK);
		}
		else
			return new ResponseEntity<String>("User Does Not Exist kindly register",HttpStatus.OK);
	}
	@GetMapping("/logout")
	public ResponseEntity<String> userLogout(HttpSession session)
	{
		String type=((UserDTO) session.getAttribute("User")).getType();
		session.invalidate();
		return new ResponseEntity<String>(type+" logged out",HttpStatus.OK);
	}
	@GetMapping("/allUsers")
	public ResponseEntity<List<UserDTO>> getAllUsers(HttpSession session){
		if(session!=null)
		{
			String type=((UserDTO) session.getAttribute("User")).getType();
			if(type.equalsIgnoreCase("admin"))
				return new ResponseEntity<List<UserDTO>>(userv.getAllUsers(),HttpStatus.OK);
		}
		return null;
	}
}
