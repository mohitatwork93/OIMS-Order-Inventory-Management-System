package com.drawpicaa.oims.service.impl;

import com.drawpicaa.oims.dto.OrderItemRequestDTO;
import com.drawpicaa.oims.dto.OrderItemResponse;
import com.drawpicaa.oims.dto.OrderRequestDTO;
import com.drawpicaa.oims.dto.OrderResponse;
import com.drawpicaa.oims.entity.Order;
import com.drawpicaa.oims.entity.OrderItem;
import com.drawpicaa.oims.entity.OrderStatus;
import com.drawpicaa.oims.entity.Product;
import com.drawpicaa.oims.exception.InsufficientStockException;
import com.drawpicaa.oims.exception.InvalidOrderStateException;
import com.drawpicaa.oims.exception.OrderNotFoundException;
import com.drawpicaa.oims.exception.ProductNotFoundException;
import com.drawpicaa.oims.repository.OrderRepository;
import com.drawpicaa.oims.repository.ProductRepository;
import com.drawpicaa.oims.service.OrderService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository){
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }


    @Override
    public OrderResponse createOrder(OrderRequestDTO orderRequestDTO) {

        Order order = new Order();
        order.setUserId(orderRequestDTO.getUserId());
        order.setUserName(orderRequestDTO.getUserName());

        List<OrderItem> itemList = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (OrderItemRequestDTO itemRequestDTO : orderRequestDTO.getItems()){

            Product product = productRepository.findById(itemRequestDTO.getProductId())
                    .orElseThrow(()-> new ProductNotFoundException(itemRequestDTO.getProductId()));

            if (product.getQuantity() < itemRequestDTO.getQuantity()) {
                throw new InsufficientStockException(
                        product.getName(),
                        product.getQuantity(),
                        itemRequestDTO.getQuantity()
                );
            }

            product.setQuantity(
                    product.getQuantity() - itemRequestDTO.getQuantity()
            );

            BigDecimal subTotal = product.getPrice().multiply(BigDecimal.valueOf(itemRequestDTO.getQuantity()));

            OrderItem item = OrderItem.builder()
                            .product(product)
                            .quantity(itemRequestDTO.getQuantity())
                            .priceAtPurchase(product.getPrice())
                            .subTotal(subTotal)
                            .order(order)
                            .build();

            itemList.add(item);

            totalAmount = totalAmount.add(subTotal);
        }

        order.setItems(itemList);
        order.setTotalAmount(totalAmount);

        Order savedorder = orderRepository.save(order);

        return orderToResponse(savedorder);
    }

    @Override
    public List<OrderResponse> getAllOrders() {

        List<Order> orders = orderRepository.findAll();

        List<OrderResponse> responses = new ArrayList<>();
        for (Order order : orders){
            OrderResponse orderResponse = orderToResponse(order);
            responses.add(orderResponse);
        }
        return responses;
    }

    @Override
    public OrderResponse findByOrderId(String orderId){

        if(!orderRepository.existsByOrderId(orderId)){
            throw new OrderNotFoundException(orderId);
        }
        Order order = orderRepository.findByOrderId(orderId);

        return orderToResponse(order);
    }

    @Transactional
    @Override
    public void cancelOrder(String orderId) {
        if(!orderRepository.existsByOrderId(orderId)){
            throw new OrderNotFoundException(orderId);
        }

        Order order = orderRepository.findByOrderId(orderId);

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new InvalidOrderStateException(
                    "Only PENDING orders can be cancelled"
            );
        }

        for (OrderItem item : order.getItems()) {
            Product product = item.getProduct();
            product.setQuantity(
                    product.getQuantity() + item.getQuantity()
            );
        }

        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

    @Transactional
    @Override
    public void completeOrder(String orderId) {
        if(!orderRepository.existsByOrderId(orderId)){
            throw new OrderNotFoundException(orderId);
        }

        Order order = orderRepository.findByOrderId(orderId);

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new InvalidOrderStateException(
                    "Only PENDING orders can be marked as Completed"
            );
        }

        order.setStatus(OrderStatus.COMPLETED);
        orderRepository.save(order);
    }


    private OrderResponse orderToResponse(Order order){

        List<OrderItem> orderItems = order.getItems();
        List<OrderItemResponse> responses = new ArrayList<>();

        for (OrderItem orderItem : orderItems){
            OrderItemResponse orResponse = new OrderItemResponse(
                    orderItem.getProduct().getName(),
                    orderItem.getQuantity(),
                    orderItem.getPriceAtPurchase(),
                    orderItem.getSubTotal()
            );
            responses.add(orResponse);
        }

        OrderResponse response = new OrderResponse(
                order.getOrderId(),
                order.getUserName(),
                order.getOrderDate(),
                order.getStatus(),
                order.getTotalAmount(),
                responses
        );

        return response;
    }


}
