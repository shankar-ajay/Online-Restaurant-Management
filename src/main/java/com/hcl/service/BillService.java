package com.hcl.service;

import java.time.LocalDate;
import java.util.List;

import com.hcl.dto.BillDTO;
import com.hcl.dto.OrderDTO;
import com.hcl.entity.Bill;
import com.hcl.exception.RestaurantException;

public interface BillService {

	public List<Bill> getAllBillsGeneratedToday();
	
	public Double totalMonthlySale();
	
	public Bill addBill(List<OrderDTO> order) throws RestaurantException;

	public BillDTO getBillDTO(Bill billEntity);

	public Bill getBillEntity(BillDTO billDTO);

}