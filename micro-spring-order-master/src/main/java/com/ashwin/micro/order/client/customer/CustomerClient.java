package com.ashwin.micro.order.client.customer;

import java.util.List;

public interface CustomerClient {

  public List<RemoteCustomerTO> getAllCustomers();

  public RemoteCustomerTO getCustomerByCode(String code);

}
