package com.seongho.memberordersystem.service;

import com.seongho.memberordersystem.dto.OrderRequestDto;
import com.seongho.memberordersystem.dto.OrderResponseDto;
import com.seongho.memberordersystem.entity.Order;
import com.seongho.memberordersystem.entity.Product;
import com.seongho.memberordersystem.exception.*;
import com.seongho.memberordersystem.repository.MemberRepository;
import com.seongho.memberordersystem.repository.OrderRepository;
import com.seongho.memberordersystem.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository, MemberRepository memberRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto requestDto, Long memberId){
        Order o = new Order();
        Product p = productRepository.findById(requestDto.getProductId()).orElseThrow(ProductNotFoundException::new);
        o.setProduct(p);
        if(p.getStock() < requestDto.getQuantity()){
            throw new InsufficientStockException();
        }
        p.setStock(p.getStock() - requestDto.getQuantity());
        o.setQuantity(requestDto.getQuantity());

        o.setTotalPrice(p.getPrice() * requestDto.getQuantity());
        o.setCreatedAt(LocalDateTime.now());
        o.setMember(memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new));

        Order saved = orderRepository.save(o);

        return new OrderResponseDto(saved.getOrderId(), saved.getProduct().getName(), saved.getQuantity(), saved.getTotalPrice(), saved.getCreatedAt());
    }

    @Transactional
    public String cancelOrder(Long memberId, Long orderId){
        Order o = orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
        Product p = o.getProduct();
        if(!Objects.equals(o.getMember().getMemberId(), memberId)){
            throw new OrderNotFoundException();
        }

        p.setStock(p.getStock() + o.getQuantity());
        orderRepository.delete(o);

        return "주문이 취소되었습니다.";
    }

    @Transactional(readOnly = true)
    public OrderResponseDto getOrder(Long memberId, Long orderId){
        Order o = orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
        if(!Objects.equals(memberId, o.getMember().getMemberId())){
            throw new OrderNotFoundException();
        }
        return new OrderResponseDto(o.getOrderId(), o.getProduct().getName(), o.getQuantity(), o.getTotalPrice(), o.getCreatedAt());
    }

    @Transactional(readOnly = true)
    public Page<OrderResponseDto> getOrders(Long memberId, Pageable pageable){
        Page<Order> orders = orderRepository.findByMemberMemberId(memberId, pageable);

        return orders.map(order -> new OrderResponseDto(
                order.getOrderId(),
                order.getProduct().getName(),
                order.getQuantity(),
                order.getTotalPrice(),
                order.getCreatedAt()
        ));
    }

}
