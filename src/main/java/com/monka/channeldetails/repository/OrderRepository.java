package com.monka.channeldetails.repository;

import org.springframework.stereotype.Service;

import com.monka.channeldetails.model.OrderDetailsResponse;

@Service
public class OrderRepository {
	
	public OrderDetailsResponse getOrder(String orderId) {
		
		OrderDetailsResponse response = new OrderDetailsResponse();
		response.setOrderNumber(Integer.parseInt(orderId));
		response.setOrderDate("07/22/2020");
		response.setOrderDescription("Example description");
		return response;
	}

}
