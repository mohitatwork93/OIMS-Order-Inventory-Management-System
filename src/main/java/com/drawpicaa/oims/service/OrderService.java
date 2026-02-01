package com.drawpicaa.oims.service;

import com.drawpicaa.oims.dto.OrderRequestDTO;
import com.drawpicaa.oims.dto.OrderResponse;

import java.util.List;

public interface OrderService {

    OrderResponse createOrder(OrderRequestDTO orderRequestDTO);

    List<OrderResponse> getAllOrders();

    OrderResponse findByOrderId(String orderId);

    void cancelOrder(String orderId);

    void completeOrder(String orderId);

}
