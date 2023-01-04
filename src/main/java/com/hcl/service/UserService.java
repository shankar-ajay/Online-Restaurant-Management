package com.hcl.service;

import java.util.List;

import com.hcl.dto.UserDTO;
import com.hcl.entity.User;
import com.hcl.exception.RestaurantException;

public interface UserService {

	public Integer addUser(UserDTO userDTO);

	public void deleteUser(Integer id);

	public UserDTO getUserDetails(Integer id) throws RestaurantException;
	
	public List<UserDTO> getAllUsers();

	public UserDTO authenticate(Integer id, String password);

	public String updateUser(UserDTO userDTO);

	public UserDTO getUserDTO(User userEntity);

	public User getUserEntity(UserDTO userDTO);

}