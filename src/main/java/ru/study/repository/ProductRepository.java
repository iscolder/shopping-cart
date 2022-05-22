package ru.study.repository;

import org.springframework.stereotype.Repository;
import ru.study.domain.Product;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
@Repository
public class ProductRepository {

    private final Map<Long, Product> products;

    public ProductRepository(Map<Long, Product> products) {
        this.products = products;
    }

    public Collection<Product> getAll() {
        return products.values();
    }

    public Optional<Product> getById(Long productId) {
        return Optional.ofNullable(products.get(productId));
    }

}
