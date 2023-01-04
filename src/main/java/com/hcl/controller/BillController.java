package com.hcl.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.dto.UserDTO;
import com.hcl.entity.Bill;
import com.hcl.service.BillService;

@RestController
@RequestMapping("/bill")
public class BillController {

	@Autowired
	BillService bserv;
	
	@GetMapping("/todaysBills")
	public ResponseEntity<List<Bill>> todaysBills(HttpSession session){
		if(session!=null)
		{
			String type=((UserDTO) session.getAttribute("User")).getType();
			if(type.equalsIgnoreCase("admin"))
				return new ResponseEntity<List<Bill>>(bserv.getAllBillsGeneratedToday(),HttpStatus.OK);
		}
		return null;
	}
	
	@GetMapping("/totalMonthlySale")
	public ResponseEntity<Double> totalMonthlySale(HttpSession session){
		if(session!=null)
		{
			String type=((UserDTO) session.getAttribute("User")).getType();
			if(type.equalsIgnoreCase("admin"))
			{
				return new ResponseEntity<Double>(bserv.totalMonthlySale(),HttpStatus.OK);
			}
		}
		return null;
				
	}
}
