package com.internship.tabulaprocessing.controller;

import com.internship.tabulaprocessing.dto.OrderPatchRequestDTO;
import com.internship.tabulaprocessing.dto.OrderRequestDto;
import com.internship.tabulaprocessing.dto.OrderResponseDto;
import com.internship.tabulaprocessing.dto.OrderUpdateRequestDTO;
import com.internship.tabulaprocessing.entity.Order;
import com.internship.tabulaprocessing.entity.PagedResult;
import com.internship.tabulaprocessing.mapper.Mapper;
import com.internship.tabulaprocessing.mapper.PatchMapper;
import com.internship.tabulaprocessing.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping("/orders")
public class OrderController {

  private OrderService orderService;

  private Mapper mapper;

  @Autowired private PatchMapper patchMapper;

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

  @GetMapping("/customer/{id}")
  public ResponseEntity<PagedResult<OrderResponseDto>> findAllByCustomerId(
      @PathVariable int id, @Valid QueryParameter queryParameter) {

    Page<Order> page = orderService.findAllByCustomerId(queryParameter.getPageable(),id);
    List<OrderResponseDto> allToDto =
            page.stream()
                    .map(order -> mapper.orderToOrderResponseDto(order))
                    .collect(Collectors.toList());

    return ResponseEntity.ok(
            new PagedResult<>(
                    allToDto, queryParameter.getPage(), page.getTotalPages(), page.getTotalElements()));

  }

  @GetMapping
  public ResponseEntity<PagedResult<OrderResponseDto>> getAllByPage(
      @Valid QueryParameter queryParameter) {

    Page<Order> page = orderService.findAll(queryParameter.getPageable());
    List<OrderResponseDto> allToDto =
        page.stream()
            .map(order -> mapper.orderToOrderResponseDto(order))
            .collect(Collectors.toList());

    return ResponseEntity.ok(
        new PagedResult<>(
            allToDto, queryParameter.getPage(), page.getTotalPages(), page.getTotalElements()));
  }

  @PostMapping
  public HttpEntity create(@RequestBody @Valid OrderRequestDto orderRequestDto) {

    Order order = mapper.convertToOrderEntity(orderRequestDto);

    return ResponseEntity.ok(mapper.orderToOrderResponseDto(orderService.create(order)));
  }

  @PutMapping("/{id}")
  public HttpEntity update(
      @PathVariable("id") Integer id, @RequestBody @Valid OrderUpdateRequestDTO orderRequestDto) {

    Order order = mapper.convertToOrderEntity(orderRequestDto);

    orderService.update(order, id);

    return ResponseEntity.ok(mapper.orderToOrderResponseDto(order));
  }

  @DeleteMapping("/{id}")
  public HttpEntity delete(@PathVariable("id") Integer id) {

    orderService.delete(id);
    return ResponseEntity.ok("Deleted successfully");
  }

  @PatchMapping(
      path = "/{id}",
      consumes = {"application/merge-patch+json"})
  public ResponseEntity<OrderResponseDto> patch(
      @PathVariable int id, @RequestBody OrderPatchRequestDTO orderRequestDto) {

    Order order = orderService.getOneById(id);
    Order patchedOrder = patchMapper.patchOrder(orderRequestDto, order);
    orderService.patch(patchedOrder);

    return ResponseEntity.ok(mapper.orderToOrderResponseDto(patchedOrder));
  }
}
