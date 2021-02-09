package com.ashwin.micro.order.testdata;

import com.ashwin.micro.order.client.catalog.RemoteItemTO;
import com.ashwin.micro.order.client.catalog.RemoteItemTOBuilder;
import com.ashwin.micro.order.client.customer.RemoteAddressTOBuilder;
import com.ashwin.micro.order.client.customer.RemoteCustomerTO;
import com.ashwin.micro.order.client.customer.RemoteCustomerTOBuilder;
import com.ashwin.micro.order.domain.Order;
import com.ashwin.micro.order.domain.OrderPosition;
import com.ashwin.micro.order.domain.builders.AddressBuilder;
import com.ashwin.micro.order.domain.builders.CustomerBuilder;
import com.ashwin.micro.order.domain.builders.OrderBuilder;
import com.ashwin.micro.order.domain.builders.OrderPositionBuilder;
import com.ashwin.micro.order.service.*;

public class TestdataProvider {

  public static Order provideOrder() {
    return OrderBuilder.order()
      .withOrderId("2019/08/1231")
      .withCustomer(
        CustomerBuilder.customer()
          .withName("Cecilia Chapman")
          .withCode("5637401")
          .withVat("563-7401")
          .build()
      )
      .withShippingAddress(
        AddressBuilder.address()
          .withCity("Mankato Mississippi")
          .withStreet("711-2880 Nulla St.")
          .withZip("96522")
          .withState("USA")
          .build()
      )
      .build();
  }

  public static OrderPosition provideOrderPosition() {
    return OrderPositionBuilder.orderPosition()
      .withCode("TT01")
      .withAmount(2)
      .withName("Test Training")
      .withPrice(1000.0)
      .build();
  }

  public static RemoteItemTO provideRemoteItemTO() {
    return RemoteItemTOBuilder.item()
      .withCode("TT01")
      .withName("Test Training (TT01)")
      .withDescription("Very interesting training")
      .withPrice(1000.0)
      .build();
  }

  public static RemoteCustomerTO provideRemoteCustomerTO() {
    return RemoteCustomerTOBuilder.customer()
      .withName("Cecilia Chapman")
      .withCode("5637401")
      .withVat("563-7401")
      .withBillingAddress(
        RemoteAddressTOBuilder.address()
          .withCity("Mankato Mississippi")
          .withStreet("711-2880 Nulla St.")
          .withZip("96522")
          .withState("USA")
          .build()
      )
      .withShippingAddress(
        RemoteAddressTOBuilder.address()
          .withCity("Mankato Mississippi")
          .withStreet("711-2880 Nulla St.")
          .withZip("96522")
          .withState("USA")
          .build()
      )
      .build();
  }

  public static OrderTO provideOrderTO() {
    return OrderTOBuilder.orderTO()
      .withOrderId("2019/08/1231")
      .withCustomer(
        CustomerDataBuilder.customerData()
          .withName("Cecilia Chapman")
          .withCode("5637401")
          .withVat("5637-401")
          .build()
      )
      .withShippingAddress(
        AddressDataBuilder.addressData()
          .withCity("Mankato Mississippi")
          .withStreet("711-2880 Nulla St.")
          .withZip("96522")
          .withState("USA")
          .build()
      )
      .build();
  }

  public static OrderPositionTO provideOrderPositionTO() {
    return OrderPositionTOBuilder.orderPositionTO()
      .withCode("TT01")
      .withAmount(2)
      .withName("Test Training")
      .withPrice(1000.0)
      .build();
  }
}
