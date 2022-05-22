package ru.study.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.study.domain.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
@ComponentScan("ru.study")
public class CartConfig {

    @Bean
    public Map<Long, Product> getProducts() {
        List<Product> products = new ArrayList<>();
        products.add(new Product(1L, "Milk", 10.0));
        products.add(new Product(2L, "Bread", 5.1));
        products.add(new Product(3L, "Apples", 12.3));
        products.add(new Product(4L, "Chocolate", 8.9));
        products.add(new Product(5L, "Beef", 30.3));
        return products.stream().collect(Collectors.toMap(Product::getId, Function.identity()));
    }

}
