package ru.study.service;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ru.study.domain.Product;
import ru.study.repository.ProductRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class Cart {

    private final Map<Long, Product> content = new HashMap<>();

    private final ProductRepository productRepository;

    public Cart(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void add(Long productId) {
        productRepository.getById(productId)
                         .ifPresent(product -> content.put(productId, product));
    }

    public void delete(Long productId) {
        productRepository.getById(productId)
                         .ifPresent(product -> content.remove(productId));
    }

    public String getContent() {
        return String.valueOf(content.values().stream().map(Product::getName).collect(Collectors.toList()));
    }

}
