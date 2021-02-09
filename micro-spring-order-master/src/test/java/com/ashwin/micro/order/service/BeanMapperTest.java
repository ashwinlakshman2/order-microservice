package com.ashwin.micro.order.service;

import org.dozer.DozerBeanMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.ashwin.micro.order.config.ApplicationConfig;
import com.ashwin.micro.order.domain.Order;
import com.ashwin.micro.order.domain.OrderPosition;
import com.ashwin.micro.order.service.OrderPositionTO;
import com.ashwin.micro.order.service.OrderTO;

import java.util.stream.Collectors;

import static com.ashwin.micro.order.testdata.TestdataProvider.provideOrder;
import static com.ashwin.micro.order.testdata.TestdataProvider.provideOrderPosition;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class})
public class BeanMapperTest {

  @Autowired
  private DozerBeanMapper beanMapper;

  @Test
  public void shouldMapOrderIntoOrderTo() {
    Order order = provideOrder();
    OrderPosition orderPosition = provideOrderPosition();
    order.getPositions().add(orderPosition);
    OrderTO orderTO = beanMapper.map(order, OrderTO.class);

    assertThat(orderTO).isNotNull();
    assertThat(orderTO.getOrderId()).isEqualTo(order.getOrderId());
    assertThat(orderTO.getShippingAddress()).isNotNull();
    assertThat(orderTO.getShippingAddress().getCity()).isEqualTo(order.getShippingAddress().getCity());
    assertThat(orderTO.getCustomer()).isNotNull();
    assertThat(orderTO.getCustomer().getCode()).isEqualTo(order.getCustomer().getCode());
    assertThat(order.getPositions()).hasSize(order.getPositions().size());
    assertThat(orderTO.getPositions().stream().map(OrderPositionTO::getCode).collect(Collectors.toList()))
      .containsOnly(orderPosition.getCode());
  }

}
