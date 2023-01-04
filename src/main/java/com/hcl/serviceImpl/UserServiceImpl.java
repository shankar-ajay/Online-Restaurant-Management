package com.hcl.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hcl.dto.UserDTO;
import com.hcl.entity.User;
import com.hcl.exception.RestaurantException;
import com.hcl.repository.UserRepository;
import com.hcl.service.OrderService;
import com.hcl.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository repo;
	@Autowired
	OrderService orderservice;

	//To register user in the database
	
	@Override
	public Integer addUser(UserDTO userDTO) {
		
		User user=this.getUserEntity(userDTO);
		user.setType("user");
		repo.save(user);
		
		return  user.getId();
	}

	//To perform delete user operation by admin.
	@Override
	public void deleteUser(Integer id) {
		repo.deleteById(id);
	}

	
	//To get user details based on UserId.
	
	@Override
	public UserDTO getUserDetails(Integer id) throws RestaurantException {
		Optional<User> op = repo.findById(id);
		User user = op.orElseThrow(() -> new RestaurantException("User Not Found"));

		return this.getUserDTO(user);
	}
	
	//To authenticate user on login.
	
	@Override
	public UserDTO authenticate(Integer id,String password)
	{
		return this.getUserDTO(repo.findByIdAndPassword(id, password));
	}
	
	
	//To update user details.
	
	@Override
	public String updateUser(UserDTO userDTO) {
		User user=this.getUserEntity(userDTO);
		System.out.println(repo.save(user));
		repo.save(user);
		return "User Details Updated Successfully";
	}
	
	//Utility methods to perform conversion from Entity to DTO.
	
	@Override
	public UserDTO getUserDTO(User userEntity)
	{
		UserDTO userDTO=new UserDTO();
		userDTO.setUserId(userEntity.getId());
		userDTO.setEmailId(userEntity.getEmailId());
		userDTO.setUserName(userEntity.getUserName());
		userDTO.setPassword(userEntity.getPassword());
		userDTO.setType(userEntity.getType());
		return userDTO;
	}

	//Utility methods to perform conversion from DTO to Entity.
	
	@Override
	public User getUserEntity(UserDTO userDTO)
	{
		User userEntity=new User();
		userEntity.setId(userDTO.getUserId());
		userEntity.setEmailId(userDTO.getEmailId());
		userEntity.setUserName(userDTO.getUserName());
		userEntity.setPassword(userDTO.getPassword());
		userEntity.setType(userDTO.getType());
		return userEntity;
	}

	//To get all the registered users.
	
	@Override
	public List<UserDTO> getAllUsers() {
		Iterable<User> itr=repo.findAll();
		List<UserDTO> users=new ArrayList<UserDTO>();
		itr.forEach(u->users.add(this.getUserDTO(u)));
		return users;
	}
}
