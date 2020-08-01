package com.monka.channeldetails.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.monka.channeldetails.model.OrderDetailsResponse;
import com.monka.channeldetails.repository.OrderRepository;

@RestController
public class OrderController {
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);
	
	private OrderRepository orderRepository;
	
	@Autowired
	public OrderController(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}
	
	@GetMapping("/orders/{orderId}")
	public ResponseEntity<OrderDetailsResponse> getOrderDetails(@PathVariable String orderId) {
		OrderDetailsResponse detailsResponse = null;
		HttpStatus httpStatus = null;
		try {
			detailsResponse = orderRepository.getOrder(orderId);
			httpStatus = HttpStatus.OK;
		} catch(Exception e) {
			LOGGER.error("exception fetching error message", e);
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			detailsResponse = defaultMessage();
		}
		
		return new ResponseEntity<OrderDetailsResponse>(detailsResponse, httpStatus);
	}
	
	@GetMapping("/orders/connect")
	public ResponseEntity<OrderDetailsResponse> getConnect(@RequestParam(required=false) String url) {
		OrderDetailsResponse detailsResponse = null;
		HttpStatus httpStatus = null;
		try {
			RestTemplate restTemplate = new RestTemplate();
			String message = restTemplate.getForObject(url, String.class);
			detailsResponse = new OrderDetailsResponse();
			detailsResponse.setOrderDate("today");
			detailsResponse.setOrderDescription(message);
			detailsResponse.setOrderNumber(99999);
			httpStatus = HttpStatus.OK;
		} catch(Exception e) {
			LOGGER.error("exception fetching error message", e);
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			detailsResponse = defaultMessage();
		}
		
		return new ResponseEntity<OrderDetailsResponse>(detailsResponse, httpStatus);
	}
	
	private OrderDetailsResponse defaultMessage() {
		OrderDetailsResponse response = new OrderDetailsResponse();
		response.setOrderNumber(00000);
		response.setOrderDate("00/00/0000");
		response.setOrderDescription("Please try later");
		return response;
	}

}
