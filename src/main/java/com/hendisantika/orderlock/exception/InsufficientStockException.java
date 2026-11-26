package com.hendisantika.orderlock.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

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
@ResponseStatus(HttpStatus.CONFLICT)
public class InsufficientStockException extends RuntimeException {

    public InsufficientStockException(Long productId, Long stock, Long quantity) {
        super("Insufficient stock for product " + productId + ": stock=" + stock + ", requested=" + quantity);
    }
}
