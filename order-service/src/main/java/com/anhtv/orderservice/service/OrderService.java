package com.anhtv.orderservice.service;

import com.anhtv.orderservice.dto.OrderLineItemsRequest;
import com.anhtv.orderservice.dto.OrderRequest;
import com.anhtv.orderservice.model.Order;
import com.anhtv.orderservice.model.OrderLineItems;
import com.anhtv.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional
    public void createOrder(OrderRequest orderRequest) {
        List<OrderLineItems> orderLineItemsList = orderRequest.getOrderLineItemsRequestList().stream()
                .map(this::toOrderLineItems)
                .toList();

        Order order = Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .orderLineItemsList(orderLineItemsList)
                .build();
        orderRepository.save(order);
    }

    private OrderLineItems toOrderLineItems(OrderLineItemsRequest orderLineItemsRequest) {
        return OrderLineItems.builder()
                .skuCode(orderLineItemsRequest.getSkuCode())
                .price(orderLineItemsRequest.getPrice())
                .quantity(orderLineItemsRequest.getQuantity())
                .build();
    }

}
