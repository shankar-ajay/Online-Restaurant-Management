package com.hcl.repository;

import org.springframework.data.repository.CrudRepository;

import com.hcl.entity.User;

public interface UserRepository extends CrudRepository<User, Integer>{

	public User findByIdAndPassword(int id,String password);
}
