package com.seongho.memberordersystem.controller;

import com.seongho.memberordersystem.dto.OrderRequestDto;
import com.seongho.memberordersystem.dto.OrderResponseDto;
import com.seongho.memberordersystem.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController{
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/orders")
    public OrderResponseDto createOrder(@RequestBody  OrderRequestDto requestDto, HttpSession session){
        Long memberId = (Long) session.getAttribute("memberId");
        return orderService.createOrder(requestDto, memberId);
    }

    @DeleteMapping("/orders/{orderId}")
    public String cancelOrder(HttpSession session,@PathVariable Long orderId){
        Long memberId = (Long) session.getAttribute("memberId");
        return orderService.cancelOrder(memberId, orderId);
    }

    @GetMapping("/orders/{orderId}")
    public OrderResponseDto getOrder(HttpSession session,@PathVariable Long orderId){
        Long memberId = (Long) session.getAttribute("memberId");
        return orderService.getOrder(memberId, orderId);
    }

    @GetMapping("/orders")
    public Page<OrderResponseDto> getOrders(HttpSession session, Pageable pageable){
        Long memberId = (Long) session.getAttribute("memberId");
        return orderService.getOrders(memberId, pageable);
    }
}
