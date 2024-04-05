package com.example.diplom.service;

import com.example.diplom.entity.Orders;
import com.example.diplom.entity.Product;
import com.example.diplom.reposiroty.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderService {
    private final OrdersRepository ordersRepository;

    public void addNewOrder(Orders orders, List<Product> productList) {
        orders.setProducts(productList);
        ordersRepository.save(orders);
    }

    public List<Orders> getAllOrder() {
        return ordersRepository.findAll();
    }
}
