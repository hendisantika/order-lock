package com.hendisantika.orderlock.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * Project : order-lock
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 2019-01-22
 * Time: 03:30
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    private Long version;

    private String name;

    private Long stock;

    public Product(String name, Long upc) {
        this.name = name;
        this.stock = upc;
    }
}
