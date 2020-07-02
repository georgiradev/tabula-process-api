package com.internship.tabulaprocessing.controller;

import com.internship.tabulaprocessing.dto.OrderItemPatchDto;
import com.internship.tabulaprocessing.dto.OrderItemPersistRequestDto;
import com.internship.tabulaprocessing.dto.OrderItemResponseDto;
import com.internship.tabulaprocessing.entity.OrderItem;
import com.internship.tabulaprocessing.entity.PagedResult;
import com.internship.tabulaprocessing.mapper.Mapper;
import com.internship.tabulaprocessing.mapper.PatchMapper;
import com.internship.tabulaprocessing.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Optional;

@Validated
@RestController
@RequestMapping("/order_items")
@RequiredArgsConstructor
public class OrderItemController {

  private final OrderItemService orderItemService;
  private final Mapper mapper;
  private final PatchMapper patchMapper;

  @PostMapping
  public ResponseEntity<OrderItemResponseDto> createOrderItem(
      @Valid @RequestBody OrderItemPersistRequestDto orderItemRequestDto) {

    OrderItem orderItem = mapper.orderItemRequestDtoToEntity(orderItemRequestDto);
    Optional<OrderItem> createdOrderItem = orderItemService.save(orderItem);

    if (createdOrderItem.isPresent()) {
      OrderItemResponseDto orderItemResponseDto =
          mapper.orderItemDtoToEntity(createdOrderItem.get());

      return ResponseEntity.status(HttpStatus.CREATED).body(orderItemResponseDto);
    }

    return ResponseEntity.status(HttpStatus.CONFLICT).build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<OrderItemResponseDto> getOrderItem(@PathVariable("id") @Min(1) int id) {

    OrderItem foundOrderItem = orderItemService.findById(id);
    OrderItemResponseDto orderItemResponseDto = mapper.orderItemDtoToEntity(foundOrderItem);

    return ResponseEntity.ok(orderItemResponseDto);
  }

  @GetMapping
  public ResponseEntity<PagedResult<OrderItemResponseDto>> getAllOrderItems(
      @Valid QueryParameter queryParameter) {

    Page<OrderItem> pagedResult = orderItemService.findAll(queryParameter);
    Page<OrderItemResponseDto> pagedResultDto = pagedResult.map(mapper::orderItemDtoToEntity);

    PagedResult<OrderItemResponseDto> allToDto =
        new PagedResult<>(
            pagedResultDto.toList(),
            queryParameter.getPage(),
            pagedResultDto.getTotalPages(),
            pagedResult.getTotalElements());

    return ResponseEntity.ok(allToDto);
  }

  @PutMapping("/{id}")
  public ResponseEntity<OrderItemResponseDto> updateOrderItem(
      @PathVariable("id") @Min(1) int id,
      @Valid @RequestBody OrderItemPersistRequestDto orderItemRequestDto) {

    OrderItem orderItem = mapper.orderItemRequestDtoToEntity(orderItemRequestDto);
    Optional<OrderItem> updatedOrderItem = orderItemService.update(id, orderItem);

    if (updatedOrderItem.isPresent()) {
      OrderItemResponseDto orderItemResponseDto =
          mapper.orderItemDtoToEntity(updatedOrderItem.get());

      return ResponseEntity.ok(orderItemResponseDto);
    }

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteOrderItem(@PathVariable("id") @Min(1) int id) {
    orderItemService.delete(id);

    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @PatchMapping(
      path = "/{id}",
      consumes = {"application/merge-patch+json"})
  public ResponseEntity<OrderItemResponseDto> patchOrderItem(
      @PathVariable int id,@Valid @RequestBody OrderItemPatchDto orderItemPatchDto) {

    OrderItem foundOrderItem = orderItemService.findById(id);
    OrderItem patched = patchMapper.mapObjectsToOrderItem(orderItemPatchDto, foundOrderItem);
    Optional<OrderItem> updatedOrderItem = orderItemService.update(id, patched);

    if (updatedOrderItem.isPresent()) {
      OrderItemResponseDto orderItemResponseDto =
          mapper.orderItemDtoToEntity(updatedOrderItem.get());

      return ResponseEntity.ok(orderItemResponseDto);
    }

    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
  }
}
