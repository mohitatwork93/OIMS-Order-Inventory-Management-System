package com.drawpicaa.oims.controller;

import com.drawpicaa.oims.dto.OrderRequestDTO;
import com.drawpicaa.oims.dto.OrderResponse;
import com.drawpicaa.oims.entity.Order;
import com.drawpicaa.oims.service.impl.OrderServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderServiceImpl orderService;

    public OrderController(OrderServiceImpl orderService){
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequestDTO requestDTO){
        OrderResponse response = orderService.createOrder(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders(){
        List<OrderResponse> responses = orderService.getAllOrders();
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> GetOrderById(@PathVariable String orderId){
        OrderResponse response = orderService.findByOrderId(orderId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PutMapping("/cancel/{orderId}")
    public ResponseEntity<String> cancelOrder(@PathVariable String orderId){
        orderService.cancelOrder(orderId);
        return ResponseEntity.ok("Order cancelled successfully");
    }

    @PutMapping("/complete/{orderId}")
    public ResponseEntity<String> completeOrder(@PathVariable String orderId){
        orderService.completeOrder(orderId);
        return ResponseEntity.ok("Order Completed successfully");
    }

}
