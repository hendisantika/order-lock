package com.hendisantika.orderlock.repository;

import com.hendisantika.orderlock.entity.Product;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * Project : order-lock
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 2019-01-22
 * Time: 03:32
 * To change this template use File | Settings | File Templates.
 */
public interface ProductsRepository extends CrudRepository<Product, Long> {

    /**
     * Pessimistic locking: reads the product with {@code SELECT ... FOR UPDATE}.
     * Other transactions that call this method for the same row wait until the
     * current transaction commits or rolls back (or the lock timeout is reached).
     * Must be called inside a transaction.
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints(@QueryHint(name = "jakarta.persistence.lock.timeout", value = "3000"))
    @Query("SELECT p FROM Product p WHERE p.id = :id")
    Optional<Product> findByIdForUpdate(@Param("id") Long id);
}
