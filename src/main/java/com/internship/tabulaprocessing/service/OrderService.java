package com.internship.tabulaprocessing.service;

import com.internship.tabulaprocessing.entity.Order;
import com.internship.tabulaprocessing.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.NotSupportedException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    //private final CustomerService customerService;

    public Order getOneById(int id) {
        Optional<Order> orderOptional = orderRepository.findById(id);

        if (orderOptional.isEmpty()) {
            throw new EntityNotFoundException("Invalid order id : " + id);
        }

        return orderOptional.get();

    }

    public Order create(Order order, Integer customerId) throws NotSupportedException {
        //TODO
      /* Optional<Customer> customerOptional = customerService.getById(customerId)
              if (customerOptional.isEmpty()) {
            throw new EntityNotFoundException("A customer with id : " + id + " does not exist");

        }
                return orderRepository.save(order);

        */
        throw new NotSupportedException();
    }

    public Order update(Order order, int id) {

        //TODO
        Optional<Order> orderOptional = orderRepository.findById(id);

        if (orderOptional.isEmpty()) {
            throw new EntityNotFoundException("A order with id+ " + id + " does not exist");
        }
        /* Optional<Customer> customerOptional = customerService.getById(customerId)
              if (customerOptional.isEmpty()) {
            throw new EntityNotFoundException("A customer with id : " + id + " does not exist");

        }
        */
        order.setId(id);

        return orderRepository.save(order);
    }

    public void delete(int id) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isEmpty()) {
            throw new EntityNotFoundException("A order with id: " + id + " does not exist");
        }

        orderRepository.deleteById(id);
    }

    public List<Order> findAll(int pageNo) {
        Pageable paging = PageRequest.of(pageNo, 10);
        Page<Order> pagedResult = orderRepository.findAll(paging);

        if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            throw new EntityNotFoundException("No orders");
        }
    }
}
