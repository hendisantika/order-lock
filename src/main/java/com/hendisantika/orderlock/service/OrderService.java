package com.hendisantika.orderlock.service;

import com.hendisantika.orderlock.entity.Product;
import com.hendisantika.orderlock.exception.InsufficientStockException;
import com.hendisantika.orderlock.exception.ProductNotFoundException;
import com.hendisantika.orderlock.repository.ProductsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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
@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductsRepository repo;

    /**
     * Orders a product using pessimistic locking.
     * <p>
     * The product row is locked ({@code SELECT ... FOR UPDATE}) for the whole transaction,
     * so concurrent orders for the same product are serialized: each one reads the latest
     * stock, checks it and decrements it. Nobody can oversell, and no request fails with an
     * optimistic locking exception; they simply wait for their turn.
     */
    @Transactional
    public Product order(Long productId, Long quantity) {
        if (quantity == null || quantity <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantity must be greater than 0");
        }

        Product product = repo.findByIdForUpdate(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        if (product.getStock() < quantity) {
            throw new InsufficientStockException(productId, product.getStock(), quantity);
        }

        product.setStock(product.getStock() - quantity);
        // The entity is managed, the UPDATE is flushed on commit and the lock is released.
        return product;
    }
}
