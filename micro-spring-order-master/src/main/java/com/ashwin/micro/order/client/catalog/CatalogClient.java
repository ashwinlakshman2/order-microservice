package com.ashwin.micro.order.client.catalog;

import java.util.List;

public interface CatalogClient {

  public List<RemoteItemTO> getAllItems();

  public RemoteItemTO getItemByCode(String code);

}
