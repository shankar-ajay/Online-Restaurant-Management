package com.hcl.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

	private Integer id;
	private LocalDate date;
	private String name;
	private Integer quantity;
	private Double price;
	private String status;
	private Integer userId;
//	private List<ProductOrderedDTO> productsOrdered;
}
