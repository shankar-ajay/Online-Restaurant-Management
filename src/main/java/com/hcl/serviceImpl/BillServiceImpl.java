package com.hcl.serviceImpl;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hcl.dto.BillDTO;
import com.hcl.dto.OrderDTO;
import com.hcl.dto.UserDTO;
import com.hcl.entity.Bill;
import com.hcl.entity.Order;
import com.hcl.entity.User;
import com.hcl.exception.RestaurantException;
import com.hcl.repository.BillRepository;
import com.hcl.repository.OrderRepository;
import com.hcl.repository.UserRepository;
import com.hcl.service.BillService;

@Service
@Transactional
public class BillServiceImpl implements BillService {

	@Autowired
	BillRepository repo;
	@Autowired
	UserRepository urepo;
	@Autowired
	OrderRepository orepo;
	
	//To addBill to the database on confirmation of order by a user.
	
	@Override
	public Bill addBill(List<OrderDTO> orderDTOs) throws RestaurantException {
		
		Bill bill=new Bill();
		bill.setDate(LocalDate.now());
		Optional<User> op1=urepo.findById(orderDTOs.get(0).getUserId());
		bill.setUser(op1.orElseThrow(()->new RestaurantException("User Not Found")));
		
		List<Order> orders=new LinkedList<Order>();

		if (!orderDTOs.isEmpty()) {
			orders = orderDTOs.stream()
					.map(o->{o.setStatus("Paid");
						return orepo.save(this.getOrderEntity(o));})
					.collect(Collectors.toList());
		}
		
		bill.setOrder(orders);
		repo.save(bill);
		
		bill.setTotal(this.getTotalPricePerBill(bill.getBillId()));
		repo.save(bill);
		
		return bill;
	}
	
	//To get total of each bill.
	
	public Double getTotalPricePerBill(Integer billId) throws RestaurantException
	{
		Double total=0.0;
		Optional<Bill> op=repo.findById(billId);
		Bill bill=op.orElseThrow();
		List<Order> orders=bill.getOrder();
		for(Order o:orders)
			total+=o.getPrice()*o.getQuantity();
		return total;
	}
	
	//To get all Bills generated Today
	
	@Override
	public List<Bill> getAllBillsGeneratedToday() {
		return repo.findByDate(LocalDate.now());
	}

	//To get Total monthly sale.
	
	@Override
	public Double totalMonthlySale() {
		Double total=0.0;
		List<Bill> bills=StreamSupport.stream(repo.findAll().spliterator(), false).collect(Collectors.toList());
		for(Bill b:bills)
		{
			if(b.getDate().getMonthValue()==LocalDate.now().getMonthValue())
				total+=b.getTotal();
		}
		return total;
	}
	
	//Utility functions to perform conversion.
	
	@Override
	public BillDTO getBillDTO(Bill billEntity) {
		BillDTO billDTO=new BillDTO();
		billDTO.setBillId(billEntity.getBillId());
		billDTO.setUser(this.getUserDTO(billEntity.getUser()));
		List<Order> order = billEntity.getOrder();
		List<OrderDTO> orderDTOs = new LinkedList<>();		
		System.out.println("Order Entity ()->"+order);

		if (!order.isEmpty()) {
			orderDTOs = order.stream()
					.map(o->this.getOrderDTO(o))
					.collect(Collectors.toList());
		}
		
		return billDTO;
	}
	
	@Override
	public Bill getBillEntity(BillDTO billDTO)  {
		Bill billEntity=new Bill();
		billEntity.setBillId(billDTO.getBillId());
		billEntity.setUser(this.getUserEntity(billDTO.getUser()));
		List<OrderDTO> orderDTOs =billDTO.getOrders();
		List<Order> order = new LinkedList<>();
			
		if (!orderDTOs.isEmpty()) 
			order = orderDTOs.stream()
					.map(o->this.getOrderEntity(o))
					.collect(Collectors.toList());
		
		return billEntity;
	}
	public OrderDTO getOrderDTO(Order orderEntity)
	{
		OrderDTO orderDTO=new OrderDTO();
		orderDTO.setId(orderEntity.getId());
		orderDTO.setDate(orderEntity.getDate());
		orderDTO.setName(orderEntity.getName());
		orderDTO.setPrice(orderEntity.getPrice());
		orderDTO.setQuantity(orderEntity.getQuantity());
		
		return orderDTO;
	}

	public Order getOrderEntity(OrderDTO orderDTO)
	{
		Order orderEntity=new Order();
		orderEntity.setId(orderDTO.getId());
		orderEntity.setDate(orderDTO.getDate());
		orderEntity.setName(orderDTO.getName());
		orderEntity.setPrice(orderDTO.getPrice());
		orderEntity.setQuantity(orderDTO.getQuantity());
		orderEntity.setStatus(orderDTO.getStatus());
		Optional<User> op = urepo.findById(orderDTO.getUserId());
		User user = op.orElseThrow();
		orderEntity.setUser(user);
		
		return orderEntity;
	}
	public UserDTO getUserDTO(User userEntity)
	{
		UserDTO userDTO=new UserDTO();
		userDTO.setUserId(userEntity.getId());
		userDTO.setEmailId(userEntity.getEmailId());
		userDTO.setUserName(userEntity.getUserName());
		userDTO.setPassword(userEntity.getPassword());
		
		return userDTO;
	}

	public User getUserEntity(UserDTO userDTO)
	{
		User userEntity=new User();
		userEntity.setId(userDTO.getUserId());
		userEntity.setEmailId(userDTO.getEmailId());
		userEntity.setUserName(userDTO.getUserName());
		userEntity.setPassword(userDTO.getPassword());
		
		
		return userEntity;
	}

	
}
