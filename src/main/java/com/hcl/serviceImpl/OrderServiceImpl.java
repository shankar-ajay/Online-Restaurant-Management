package com.hcl.serviceImpl;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hcl.dto.OrderDTO;
import com.hcl.entity.Item;
import com.hcl.entity.Order;
import com.hcl.entity.User;
import com.hcl.exception.RestaurantException;
import com.hcl.repository.ItemRepository;
import com.hcl.repository.OrderRepository;
import com.hcl.repository.UserRepository;
import com.hcl.service.OrderService;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	@Autowired
	UserRepository uRepo;
	@Autowired
	OrderRepository repo;
	@Autowired
	ItemRepository iRepo;
	
	//To get the List of All the orders done by user.
	
	@Override
	public List<OrderDTO> getAllOrders(Integer userId) throws RestaurantException {
	
    	List<Order> orders=repo.findByUserId(userId);
		List<OrderDTO> orderDTOs=new LinkedList<OrderDTO>();
		
		orders.forEach(o->{
			if(o.getStatus().equals("Paid"))
				orderDTOs.add(this.getOrderDTO(o));
			});
        return orderDTOs;
    }
	
	
	//To get the list of all unpaid orders in the cart.
	
	@Override
	public List<OrderDTO> getAllUnPaidOrders(Integer userId) throws RestaurantException {
		
    	List<Order> orders=repo.findByUserId(userId);
		List<OrderDTO> orderDTOs=new LinkedList<OrderDTO>();
		
		for(Order o:orders)
		{
			if(o.getStatus()!=null)
			{
				if(o.getStatus().equalsIgnoreCase("UnPaid"))
					orderDTOs.add(this.getOrderDTO(o));
			}
			
		}
        return orderDTOs;
    }
	

	//To add items to the cart.
	
	@Override
	public void create(Integer userId,OrderDTO orderDTO) throws RestaurantException {
    	
		
		Optional<User> op=uRepo.findById(userId);
    	User user=op.orElseThrow(()->new RestaurantException("User Not Found"));
    	
    	Item item=iRepo.findByItemName(orderDTO.getName());
    	Order order=this.getOrderEntity(orderDTO);
    	order.setDate(LocalDate.now());
    	order.setPrice(item.getPrice());
    	order.setUser(user);
    	order.setStatus("UnPaid");
    	repo.save(order);
    	
    }
	
	//To get Orders of a specific user.
	
	@Override
	public List<Order> getUserSpecificOrders(Integer userId) {
		List<Order> orders=repo.findByUserId(userId);
		List<Order> paidOrders=new LinkedList<>();
		orders.forEach(o->{
			if(o.getStatus().equals("Paid"))
				paidOrders.add(o);
		});
		return paidOrders;
	}

	//To delete items from the user's cart.
	
	@Override
	public void deleteFromCart(Integer orderId) throws RestaurantException {
		Optional<Order> op=repo.findById(orderId);
		Order order=op.orElseThrow(()->new RestaurantException("No Such Order Exists"));
		order.setStatus("Removed");
		repo.save(order);
	}
    
	//Utility methods to perform Entity to DTO conversion.
	
    @Override
	public OrderDTO getOrderDTO(Order orderEntity)
	{
		OrderDTO orderDTO=new OrderDTO();
		orderDTO.setId(orderEntity.getId());
		orderDTO.setDate(orderEntity.getDate());
		orderDTO.setName(orderEntity.getName());
		orderDTO.setPrice(orderEntity.getPrice());
		orderDTO.setQuantity(orderEntity.getQuantity());
		orderDTO.setStatus(orderEntity.getStatus());
		orderDTO.setUserId(orderEntity.getUser().getId());
		
		
		return orderDTO;
	}

  //Utility methods to perform DTO to Entity conversion.
    
	@Override
	public Order getOrderEntity(OrderDTO orderDTO) throws RestaurantException
	{
		Order orderEntity=new Order();
		orderEntity.setId(orderDTO.getId());
		orderEntity.setDate(orderDTO.getDate());
		orderEntity.setName(orderDTO.getName());
		orderEntity.setPrice(orderDTO.getPrice());
		orderEntity.setQuantity(orderDTO.getQuantity());
		orderEntity.setStatus(orderDTO.getStatus());
		Optional<User> op = uRepo.findById(orderDTO.getUserId());
		User user = op.orElseThrow(() -> new RestaurantException("User Not Found"));
		orderEntity.setUser(user);
		
		
		return orderEntity;
	}


	
}
