package com.hendisantika.orderlock.repository;

import com.hendisantika.orderlock.entity.Product;
import org.springframework.data.repository.CrudRepository;

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
}
