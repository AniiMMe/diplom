package com.example.diplom.controller;

import com.example.diplom.entity.Assortment;
import com.example.diplom.entity.Orders;
import com.example.diplom.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class OrderService {
    private final OrdersRepository ordersRepository;

    public void addNewOrder(Orders orders, List<Product> productList) {
        orders.setProducts(productList);
        ordersRepository.save(orders);
    }
}
