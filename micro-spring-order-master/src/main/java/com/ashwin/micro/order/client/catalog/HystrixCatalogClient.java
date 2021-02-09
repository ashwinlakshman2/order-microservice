package com.ashwin.micro.order.client.catalog;

import com.ashwin.micro.order.config.ConfigProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@Profile("hystrix")
public class HystrixCatalogClient implements CatalogClient {

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private ConfigProperties properties;


  @Override
  @HystrixCommand(
    fallbackMethod = "getAllItemsFallback",
    commandProperties = {
      @HystrixProperty(
        name = "execution.isolation.thread.timeoutInMilliseconds",
        value = "500")
    })
  public List<RemoteItemTO> getAllItems() {
    return Arrays.asList(restTemplate.getForObject(properties.getCatalogServiceUrl(), RemoteItemTO[].class));
  }

  public List<RemoteItemTO> getAllItemsFallback() {
    return Collections.emptyList();
  }

  @Override
  @HystrixCommand(
    fallbackMethod = "getItemByCodeFallback",
    commandProperties = {
      @HystrixProperty(
        name = "execution.isolation.thread.timeoutInMilliseconds",
        value = "500")
    })
  public RemoteItemTO getItemByCode(String code) {
    return restTemplate.getForObject(properties.getCatalogServiceUrl() + code, RemoteItemTO.class);
  }

  public RemoteItemTO getItemByCodeFallback(String code) {
    return null;
  }
}
