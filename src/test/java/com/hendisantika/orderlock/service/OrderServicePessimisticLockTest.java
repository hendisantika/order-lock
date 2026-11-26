package com.hendisantika.orderlock.service;

import com.hendisantika.orderlock.entity.Product;
import com.hendisantika.orderlock.exception.InsufficientStockException;
import com.hendisantika.orderlock.repository.ProductsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by IntelliJ IDEA.
 * Project : order-lock
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Link : s.id/hendisantika
 * Date: 26/11/26
 * Time: 13.40
 */
@SpringBootTest
class OrderServicePessimisticLockTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductsRepository repo;

    @Test
    void concurrentOrdersNeverOversell() throws Exception {
        // Use a product seeded by data.sql (Star Fruit) and reset its stock to 5
        Product product = repo.findById(3L).orElseThrow();
        product.setStock(5L);
        product = repo.save(product);
        Long productId = product.getId();
        int customers = 10;

        AtomicInteger success = new AtomicInteger();
        AtomicInteger outOfStock = new AtomicInteger();

        List<Callable<Void>> tasks = new ArrayList<>();
        for (int i = 0; i < customers; i++) {
            tasks.add(() -> {
                try {
                    orderService.order(productId, 1L);
                    success.incrementAndGet();
                } catch (InsufficientStockException e) {
                    outOfStock.incrementAndGet();
                }
                return null;
            });
        }

        try (ExecutorService executor = Executors.newFixedThreadPool(customers)) {
            for (Future<Void> future : executor.invokeAll(tasks)) {
                future.get(); // rethrows any unexpected exception
            }
        }

        assertThat(success.get()).isEqualTo(5);
        assertThat(outOfStock.get()).isEqualTo(5);
        assertThat(repo.findById(productId).orElseThrow().getStock()).isZero();
    }
}
