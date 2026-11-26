package com.hendisantika.orderlock.controller;

import com.hendisantika.orderlock.entity.Product;
import com.hendisantika.orderlock.repository.ProductsRepository;
import com.hendisantika.orderlock.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * Project : order-lock
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 2019-01-22
 * Time: 03:33
 * To change this template use File | Settings | File Templates.
 */
@RestController("ProductsController")
@RequestMapping("/products")
public class ProductsController {
    @Autowired
    private ProductsRepository repo;

    @Autowired
    private OrderService orderService;


    @GetMapping
    public Iterable<Product> findAll() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Product> findOne(@PathVariable Long id) {
        return repo.findById(id);
    }

    @PutMapping
    public Product save(@RequestBody Product product) {
        return repo.save(product);
    }

    /**
     * Pessimistic locking example: order {@code quantity} items of a product.
     * Concurrent orders for the same product wait for each other instead of failing.
     */
    @PostMapping("/{id}/order")
    public Product order(@PathVariable Long id, @RequestParam(defaultValue = "1") Long quantity) {
        return orderService.order(id, quantity);
    }

}