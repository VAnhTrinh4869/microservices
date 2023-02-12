package com.anhtv.orderservice.service;

import com.anhtv.orderservice.dto.InventoryResponse;
import com.anhtv.orderservice.dto.OrderLineItemsRequest;
import com.anhtv.orderservice.dto.OrderRequest;
import com.anhtv.orderservice.model.Order;
import com.anhtv.orderservice.model.OrderLineItems;
import com.anhtv.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final WebClient webClient;

    @Transactional
    public void createOrder(OrderRequest orderRequest) {
        List<OrderLineItems> orderLineItemsList = orderRequest.getOrderLineItemsRequestList().stream()
                .map(this::toOrderLineItems)
                .toList();

        Order order = Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .orderLineItemsList(orderLineItemsList)
                .build();

        List<String> skuCodes = order.getOrderLineItemsList().stream()
                .map(OrderLineItems::getSkuCode)
                .toList();

        // check whether all item is in stock
        InventoryResponse[] allInventories = webClient.get()
                .uri("http://localhost:8082/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        boolean isAllInStock = Arrays.stream(allInventories).allMatch(InventoryResponse::isInStock);
        if (isAllInStock){
            orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("Product isnt in stock");
        }
    }

    private OrderLineItems toOrderLineItems(OrderLineItemsRequest orderLineItemsRequest) {
        return OrderLineItems.builder()
                .skuCode(orderLineItemsRequest.getSkuCode())
                .price(orderLineItemsRequest.getPrice())
                .quantity(orderLineItemsRequest.getQuantity())
                .build();
    }

}
