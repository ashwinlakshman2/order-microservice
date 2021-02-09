package com.ashwin.micro.order.service;

import java.util.HashSet;
import java.util.Set;

import com.ashwin.micro.order.service.OrderCreationPositionTO;
import com.ashwin.micro.order.service.OrderCreationTO;

public final class OrderCreationTOBuilder {
  private String customerCode;
  private Set<OrderCreationPositionTO> positions = new HashSet<>();

  private OrderCreationTOBuilder() {
  }

  public static OrderCreationTOBuilder orderTO() {
    return new OrderCreationTOBuilder();
  }

  public OrderCreationTOBuilder withCustomerCode(String customerCode) {
    this.customerCode = customerCode;
    return this;
  }

  public OrderCreationTOBuilder withPositions(Set<OrderCreationPositionTO> positions) {
    this.positions = positions;
    return this;
  }

  public OrderCreationTO build() {
    OrderCreationTO orderCreationTO = new OrderCreationTO();
    orderCreationTO.setCustomerCode(customerCode);
    orderCreationTO.setPositions(positions);
    return orderCreationTO;
  }
}
