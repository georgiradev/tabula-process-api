package com.internship.tabulaprocessing.provider;

import com.internship.tabulaprocessing.entity.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class OrderProvider {

    public static Order getOrderInstance() {
        // set the customer
        Customer customer = new Customer();
        customer.setId(1);
        customer.setAccountId(1);
        customer.setPhone("0877722846");

        // set the order
        Order order = new Order();
        LocalDateTime currentDateTime = LocalDateTime.now();
        Timestamp timeStamp = Timestamp.valueOf(currentDateTime);

        order.setId(1);
        order.setCustomer(customer);
        order.setDateTimeCreated(timeStamp);
        order.setNote("Some message");
        order.setPrice(BigDecimal.valueOf(12.32));

        return order;
    }

    public static List<Order> getOrdersInstance() {
        return Collections.singletonList(getOrderInstance());
    }
}
