package com.drawpicaa.oims.repository;

import com.drawpicaa.oims.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findByOrderId(String orderId);

    boolean existsByOrderId(String orderId);

    List<Order> findByUserIdOrderByOrderDateDesc(Long userId);
}
