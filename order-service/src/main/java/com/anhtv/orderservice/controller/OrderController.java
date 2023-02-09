package com.anhtv.orderservice.controller;

import com.anhtv.orderservice.dto.OrderRequest;
import com.anhtv.orderservice.model.Order;
import com.anhtv.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createOrder(@RequestBody OrderRequest orderRequest) {
        orderService.createOrder(orderRequest);
        return "Order placed successfully.";
    }
}
