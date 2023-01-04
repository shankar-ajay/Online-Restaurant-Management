package com.hcl.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


//Entity Class for Bill table.

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bill {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer billId;
	private LocalDate date;
	private Double total;
	@OneToMany(cascade=CascadeType.MERGE)
	@JoinColumn(name="billId")
	private List<Order> order;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="userId")
	private User user;
	
}
