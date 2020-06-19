package com.internship.tabulaprocessing.controller;


import com.internship.tabulaprocessing.dto.OrderRequestDto;
import com.internship.tabulaprocessing.dto.OrderResponseDto;
import com.internship.tabulaprocessing.entity.Order;
import com.internship.tabulaprocessing.mapper.Mapper;
import com.internship.tabulaprocessing.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Validated
@RestController
@RequestMapping("/orders")
public class OrderController {

    private OrderService orderService;

    private Mapper mapper;

    @Autowired
    public OrderController(OrderService orderService, Mapper mapper) {
        this.orderService = orderService;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    public HttpEntity get(@PathVariable(name = "id") Integer id) {
        Order order = orderService.getOneById(id);
        return ResponseEntity.ok(mapper.orderToOrderResponseDto(order));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getAllByPage(
            @RequestParam(defaultValue = "0") int pageNo) {

        List<Order> allOrders = orderService.findAll(pageNo);
        List<OrderResponseDto> allToDto = new ArrayList<>();
        for (Order orders : allOrders) {
            allToDto.add(mapper.orderToOrderResponseDto(orders));
        }
        return ResponseEntity.ok(allToDto);
    }

    @PostMapping
    public HttpEntity create(@RequestBody @Valid OrderRequestDto orderRequestDto) {

        Order order = mapper.orderRequestDtoToOrder(orderRequestDto);
        Order createdOrder = orderService.create(order, order.getCustomer().getId());
        return ResponseEntity.ok(mapper.orderToOrderResponseDto(createdOrder));
    }

    @PutMapping("/{id}")
    public HttpEntity update(@PathVariable("id") Integer id, @RequestBody @Valid OrderRequestDto orderRequestDto) {

        Order order = mapper.orderRequestDtoToOrder(orderRequestDto);
        Order updatedOrder = orderService.update(order, id);
        return ResponseEntity.ok(mapper.orderToOrderResponseDto(updatedOrder));
    }

    @DeleteMapping("/{id}")
    public HttpEntity delete(@PathVariable("id") Integer id) {

        orderService.delete(id);
        return ResponseEntity.ok("Deleted successfully");
    }
}
