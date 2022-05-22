package ru;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.study.config.CartConfig;
import ru.study.domain.Product;
import ru.study.repository.ProductRepository;
import ru.study.service.Cart;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ShoppingApplication {

    private static final List<Cart> usedCarts = new ArrayList<>();

    private static final List<String> VALID_COMMANDS = Arrays.asList("/add", "/delete", "/new", "/complete");

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(CartConfig.class);
        context.refresh();

        Cart cart = context.getBean(Cart.class);
        ProductRepository productRepository = context.getBean(ProductRepository.class);

        System.out.println("All Products:");
        Collection<Product> allProducts = productRepository.getAll();
        List<Long> validProductIds = allProducts.stream().map(Product::getId).collect(Collectors.toList());
        productRepository.getAll().forEach(System.out::println);

        System.out.println("Valid Commands: " + VALID_COMMANDS);
        System.out.println("Command format: /{command} {product id}");

        usedCarts.add(cart);
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                String input = scanner.nextLine();
                String[] parts = input.split(" ");

                String command = parts[0];

                if (!VALID_COMMANDS.contains(command)) {
                    System.out.println("Invalid command: " + command);
                    continue;
                }

                if ("/new".equals(command)) {
                    cart = context.getBean(Cart.class);
                    usedCarts.add(cart);
                    System.out.println("New cart is requested");
                    continue;
                }

                if ("/complete".equals(command)) {
                    System.out.println("Shopping is over");
                    break;
                }

                long productId = getProductNumber(parts[1]);
                if (!validProductIds.contains(productId)) {
                    System.out.println("Invalid product id: " + parts[1]);
                    continue;
                }

                if ("/add".equals(command)) {
                    cart.add(productId);
                    System.out.println("Cart's content: " + cart.getContent());
                } else if ("/delete".equals(command)) {
                    cart.delete(productId);
                    System.out.println("Cart's content: " + cart.getContent());
                }
            }
        }

        System.out.println("All Carts Content: ");
        usedCarts.forEach(shoppingCarts -> System.out.println(shoppingCarts.getContent()));
    }

    private static long getProductNumber(String value) {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            return 0L;
        }
    }
}
