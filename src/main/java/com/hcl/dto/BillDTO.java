package com.hcl.dto;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillDTO {

	private Integer billId;
	private Double total;
	private List<OrderDTO> orders;
	private UserDTO user;
}
