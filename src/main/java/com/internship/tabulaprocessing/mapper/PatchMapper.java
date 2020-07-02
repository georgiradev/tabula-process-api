package com.internship.tabulaprocessing.mapper;

import com.internship.tabulaprocessing.dto.*;
import com.internship.tabulaprocessing.entity.Process;
import com.internship.tabulaprocessing.entity.*;
import com.internship.tabulaprocessing.service.CompanyService;
import com.internship.tabulaprocessing.service.MediaService;
import com.internship.tabulaprocessing.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;

@RequiredArgsConstructor
@org.mapstruct.Mapper(
    componentModel = "spring",
    uses = {OrderService.class, Mapper.class, MediaService.class, CompanyService.class},
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class PatchMapper {

  @Autowired private OrderService orderService;

  @Autowired private MediaService mediaService;

  @Autowired private CompanyService companyService;

  @Autowired private Mapper mapper;

  public abstract Order patchOrder(OrderPatchRequestDTO dto, @MappingTarget Order order);

  public abstract TimeOffType mapObjectsToTimeOffType(
      TimeOffTypeRequestDto data, @MappingTarget TimeOffType timeOffType);

  public abstract Media mapObjectsToMedia(MediaRequestDto data, @MappingTarget Media media);

  public abstract MediaExtra mapObjectsToMediaExtra(
      MediaExtraRequestDto data, @MappingTarget MediaExtra mediaExtra);

  public abstract Company mapObjectsToCompany(
      CompanyPatchDto companyPatchDto, @MappingTarget Company company);

  @Mapping(source = "orderId", target = "order.id")
  @Mapping(source = "mediaId", target = "media.id")
  public OrderItem mapObjectsToOrderItem(
      OrderItemPatchDto orderItemPatchDto, @MappingTarget OrderItem currentOrderItem) {
    OrderItem patchedOrderItem = new OrderItem();

    if (orderItemPatchDto == null) {
      return currentOrderItem;
    }

    if (orderItemPatchDto.getHeight() != null) {
      patchedOrderItem.setHeight(orderItemPatchDto.getHeight());
    } else {
      patchedOrderItem.setHeight(currentOrderItem.getHeight());
    }

    if (orderItemPatchDto.getWidth() != null) {
      patchedOrderItem.setWidth(orderItemPatchDto.getWidth());
    } else {
      patchedOrderItem.setWidth(currentOrderItem.getWidth());
    }

    if (orderItemPatchDto.getCount() != null) {
      patchedOrderItem.setCount(orderItemPatchDto.getCount());
    } else {
      patchedOrderItem.setCount(currentOrderItem.getCount());
    }

    patchedOrderItem.setPricePerPiece(currentOrderItem.getPricePerPiece());

    if (orderItemPatchDto.getNote() != null) {
      patchedOrderItem.setNote(orderItemPatchDto.getNote());
    } else {
      patchedOrderItem.setNote(currentOrderItem.getNote());
    }

    if (orderItemPatchDto.getMediaId() != null) {
      MediaDto foundMedia = mediaService.getOne(orderItemPatchDto.getMediaId());
      patchedOrderItem.setMedia(mapper.convertToMediaEntity(foundMedia));
    } else {
      patchedOrderItem.setMedia(currentOrderItem.getMedia());
    }

    if (orderItemPatchDto.getOrderId() != null) {
      Order foundOrder = orderService.getOneById(orderItemPatchDto.getOrderId());
      patchedOrderItem.setOrder(foundOrder);
    } else {
      patchedOrderItem.setOrder(currentOrderItem.getOrder());
    }

    return patchedOrderItem;
  }

  @Mapping(source = "companyId", target = "company.id")
  public Customer mapObjectsToCustomer(
      CustomerPatchDto customerPatchDto, @MappingTarget Customer currentCustomer) {

    if (customerPatchDto == null) {
      return currentCustomer;
    }

    Customer patchedCustomer = new Customer();
    patchedCustomer.setId(currentCustomer.getId());

    if (customerPatchDto.getAccountId() != null) {
      patchedCustomer.setAccountId(customerPatchDto.getAccountId());
    } else {
      patchedCustomer.setAccountId(currentCustomer.getAccountId());
    }

    if (customerPatchDto.getCompanyId() != null) {
      Company foundCompany = companyService.find(customerPatchDto.getCompanyId());
      patchedCustomer.setCompany(foundCompany);
    } else {
      patchedCustomer.setCompany(currentCustomer.getCompany());
    }

    if (customerPatchDto.getPhone() != null) {
      patchedCustomer.setPhone(customerPatchDto.getPhone());
    } else {
      patchedCustomer.setPhone(currentCustomer.getPhone());
    }
    patchedCustomer.setOrders(currentCustomer.getOrders());

    return patchedCustomer;
  }

  public abstract Process mapObjectsToProcess(
      ProcessRequestDto data, @MappingTarget Process process);

  public abstract TrackingHistory patchTrackingHistory(
      TrackingHistoryRequestDTO requestDTO, @MappingTarget TrackingHistory trackingHistory);
}
