package com.service.shop;

import brave.Tracer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootApplication
@RestController
@Slf4j
public class ShopApplication {
	public static void main(String[] args) {
		SpringApplication.run(ShopApplication.class, args);
	}

	@Autowired
	RestTemplate restTemplate;

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@RequestMapping(value = "/products", method = RequestMethod.GET, produces = "application/json")
	public Object products() {
		// Don't use as belows. It does not sending the trace-id automatically.
		// RestTemplate restTemplate = new RestTemplate();

		log.info("Get products from product service.");

		List<Object> products = restTemplate.getForObject("http://product:8090/products", List.class);

		log.info("Products received from product service.");

		log.info(products.toString());

		return products;

	}
}
