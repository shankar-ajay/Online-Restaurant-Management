package com.hcl.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//Entity class for User Table.

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="userId")
	private Integer id;
	@Column(unique=true)
	private String userName;
	@Column(unique=true)
	private String emailId;
	private String password;
	private String type;

//	@OneToMany(cascade=CascadeType.ALL)
//	@JoinColumn(name="order_id")
//	private List<Order> orders;
}
