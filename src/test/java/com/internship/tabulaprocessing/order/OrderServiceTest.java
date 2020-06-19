package com.internship.tabulaprocessing.order;

import com.internship.tabulaprocessing.entity.Order;
import com.internship.tabulaprocessing.provider.OrderProvider;
import com.internship.tabulaprocessing.repository.OrderRepository;
import com.internship.tabulaprocessing.service.OrderService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    public void testGetById() {
        Order order = OrderProvider.getOrderInstance();

        when(orderRepository.findById(1)).thenReturn(Optional.of(order));
        Optional<Order> result = Optional.ofNullable(orderService.getOneById(1));

        Assertions.assertThat(result).isNotNull();
        result.ifPresent(value -> assertEquals(order, value));
    }

    @Test
    public void testGetOrderByIdShouldFail() {
        Order order = OrderProvider.getOrderInstance();

        when(orderRepository.findById(order.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> orderService.getOneById(order.getId()));
    }

    @Test
    void testDeleteOrder() {
        Order order = OrderProvider.getOrderInstance();

        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
        orderService.delete(order.getId());

        verify(orderRepository, times(1)).deleteById(order.getId());
    }

    @Test
    void testDeleteOrderShouldFail() {
        when(orderRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> orderService.delete(1));
    }

    @Test
    void testUpdateOrder() {
        Order order = OrderProvider.getOrderInstance();

        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
        given(orderRepository.save(order)).willReturn(order);
        Optional<Order> updatedOrder = Optional.ofNullable(orderService.update(order, 1));

        Assertions.assertThat(updatedOrder).isNotNull();
        verify(orderRepository).save(any(Order.class));
    }

    @Test
    void testUpdateOrderShouldFailBecauseOfWrongId() {
        Order order = OrderProvider.getOrderInstance();

        when(orderRepository.findById(order.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> orderService.update(order, 1));
    }


    @Test
    void testFindAllOrders() {
        List<Order> orders = OrderProvider.getOrdersInstance();
        Page<Order> paging = new PageImpl<>(orders);

        when(orderRepository.findAll(any(Pageable.class))).thenReturn(paging);

        assertEquals(orders, orderService.findAll(1));
    }

    @Test
    void testFindAllOrdersButNoContentFound() {
        Page<Order> paging = Page.empty();

        when(orderRepository.findAll(any(Pageable.class))).thenReturn(paging);

        assertThrows(EntityNotFoundException.class, () -> orderService.findAll(1));
    }
}