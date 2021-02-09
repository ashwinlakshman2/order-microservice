package com.ashwin.micro.order.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ashwin.micro.order.config.ConfigProperties;
import com.ashwin.micro.order.domain.Order;
import com.ashwin.micro.order.domain.OrderPosition;
import com.ashwin.micro.order.domain.builders.AddressBuilder;
import com.ashwin.micro.order.domain.builders.CustomerBuilder;
import com.ashwin.micro.order.domain.builders.OrderBuilder;
import com.ashwin.micro.order.domain.builders.OrderPositionBuilder;
import com.ashwin.micro.order.repository.OrderRepository;
import com.ashwin.micro.order.service.customers.CustomerTO;
import com.ashwin.micro.order.service.customers.ReadCustomerService;
import com.ashwin.micro.order.service.items.ReadItemService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {

  @Autowired
  private ConfigProperties properties;

  @Autowired
  private ReadCustomerService customerService;

  @Autowired
  private ReadItemService itemService;

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private DozerBeanMapper beanMapper;

  public OrderTO createOrder(OrderCreationTO orderTO) {

    CustomerTO customerTO = Optional.ofNullable(customerService.getCustomerByCode(orderTO.getCustomerCode()))
      .orElseThrow(() -> new OrderCreationException("Customer " + orderTO.getCustomerCode() + " not found"));

    Order order = OrderBuilder.order()
      .withCustomer(CustomerBuilder.customer()
        .withCode(customerTO.getCode())
        .withName(customerTO.getName())
        .withVat(customerTO.getVat())
        .build())
      .withShippingAddress(AddressBuilder.address()
        .withCity(customerTO.getShippingAddress().getCity())
        .withState(customerTO.getShippingAddress().getState())
        .withStreet(customerTO.getShippingAddress().getStreet())
        .withZip(customerTO.getShippingAddress().getZip())
        .build())
      .withOrderId(generateOrderId())
      .withPositions(orderTO.getPositions().stream()
        .map(this::createOrderPosition)
        .filter(item -> item != null)
        .collect(Collectors.toSet()))
      .build();

    order = orderRepository.save(order);

    return beanMapper.map(order, OrderTO.class);
  }

  public List<OrderTO> getAllOrders() {
    return orderRepository.findAll().stream()
      .map(order -> beanMapper.map(order, OrderTO.class))
      .collect(Collectors.toList());

  }

  public OrderTO getOrderByOrderId(String orderId) {
    return Optional.ofNullable(orderRepository.findByOrderId(orderId))
      .map(order -> beanMapper.map(order, OrderTO.class))
      .orElse(null);

  }

  private OrderPosition createOrderPosition(OrderCreationPositionTO positionTO) {
    return Optional.ofNullable(itemService.getItemByCode(positionTO.getCode()))
      .map(item -> OrderPositionBuilder.orderPosition()
        .withAmount(positionTO.getAmount())
        .withCode(item.getCode())
        .withName(item.getName())
        .withPrice(item.getPrice())
        .build())
      .orElseThrow(() -> new OrderCreationException("Item " + positionTO.getCode() + " not found"));
  }

  private String generateOrderId() {
    return DateTimeFormatter.ofPattern("yyyyMMddHHmm")
      .format(LocalDateTime.now()) + RandomStringUtils.randomNumeric(5);
  }

}
