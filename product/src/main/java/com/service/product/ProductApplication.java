package com.service.product;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@EnableAutoConfiguration
@SpringBootApplication
@RestController
@Slf4j
public class ProductApplication {
	@Data
	class Product {
		private String pid;
		private String name;
		private String price;
	}

	public static void main(String[] args) {
		SpringApplication.run(ProductApplication.class, args);
	}

	@RequestMapping(value = "/products", method = RequestMethod.GET, produces = "application/json")
	public Object products() {

		log.info("Get products.");

		List<Product> products = new ArrayList<>();

		{
			Product product = new Product();
			product.setPid("PID1000");
			product.setName("Colorful cup");
			product.setPrice("1000");
			products.add(product);
		}

		{
			Product product = new Product();
			product.setPid("PID1001");
			product.setName("Brown Jacket");
			product.setPrice("1001");
			products.add(product);
		}

		{
			Product product = new Product();
			product.setPid("PID1002");
			product.setName("Nice book");
			product.setPrice("1002");
			products.add(product);
		}

		log.info("return products");

		return products;
	}
}
