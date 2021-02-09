package com.ashwin.micro.order.client.catalog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ashwin.micro.order.config.ConfigProperties;

import java.util.Arrays;
import java.util.List;

@Service
@Profile("!hystrix")
public class SimpleCatalogClient implements CatalogClient {

  @Autowired
  RestTemplate restTemplate;

  @Autowired
  private ConfigProperties properties;


  @Override
  public List<RemoteItemTO> getAllItems() {
    return Arrays.asList(restTemplate.getForObject(properties.getCatalogServiceUrl(), RemoteItemTO[].class));
  }

  @Override
  public RemoteItemTO getItemByCode(String code) {
    return restTemplate.getForObject(properties.getCatalogServiceUrl() + code, RemoteItemTO.class);
  }

}
