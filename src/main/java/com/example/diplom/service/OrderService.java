package com.example.diplom.service;

import com.example.diplom.dto.AssortmentDTO;
import com.example.diplom.dto.OrderDTO;
import com.example.diplom.dto.OrderProductDTO;
import com.example.diplom.entity.Assortment;
import com.example.diplom.entity.Client;
import com.example.diplom.entity.Orders;
import com.example.diplom.entity.Product;
import com.example.diplom.reposiroty.AssortmentRepository;
import com.example.diplom.reposiroty.ClientRepository;
import com.example.diplom.reposiroty.OrdersRepository;
import com.example.diplom.reposiroty.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class OrderService {
    private final OrdersRepository ordersRepository;
    private final ProductRepository productRepository;
    private final AssortmentRepository assortmentRepository;
    private final ClientRepository clientRepository;


    public void addNewOrder(OrderDTO order, List<OrderProductDTO> productList) {
        List<Product> productOrder = new ArrayList<>();
        Client client = clientRepository.getById(order.getClient());

        productList.forEach(x->{
            Assortment assortment = assortmentRepository.findById(x.getIdAssortment()).orElse(null);
            assortment.setCount(assortment.getCount()-x.getCountProductFromAssortment());
            List<Product> productListDB = productRepository.findAllByAssortmentOrderByProductEnddataDesc(assortment);
            int count = x.getCountProductFromAssortment();
            for (Product p: productListDB) {
                if (p.getProductQuantity() > count) {
                    p.setProductQuantity(p.getProductQuantity() - count);
                    break;
                } else if (p.getProductQuantity() == count) {
                    p.setProductQuantity(0);
                    break;
                } else {
                    int del = count - p.getProductQuantity();
                    p.setProductQuantity(p.getProductQuantity() - del);
                    count = count - del;
                }
                productOrder.add(productRepository.save(p));
            }
            assortmentRepository.save(assortment);

        });
        Orders orders = Orders.builder()
                .orderCost(productList.stream().mapToDouble(x -> x.getCostForOneProduct() * x.getCountProductFromAssortment()).sum())
                .orderQuantity(productList.stream().mapToDouble(OrderProductDTO::getCountProductFromAssortment).sum())
                .orderDate(order.getOrderDate())
                .orderStatus(order.getOrderStatus())
                .client(client)
                .build();
        orders.setProducts(productOrder);
        ordersRepository.save(orders);
    }
    public Map<String, String> checkErrorSwitch(OrderDTO orderDTO, OrderProductDTO orderProductDTO, BindingResult result){
        Map<String,String> descriptionError = new HashMap<>();
        result.getFieldErrors().forEach(error ->{
            switch (error.getField()){
                case "orderDate":{
                    descriptionError.put("orderDate", "Введите дату");
                    break;
                }
                case "idAssortment":{
                    descriptionError.put("idAssortment", "Выберите товар");
                    break;
                }
                case "countProductFromAssortment":{
                    descriptionError.put("countProductFromAssortment", "Введите количество товара");
                    break;
                }
                case "costForOneProduct":{
                    descriptionError.put("costForOneProduct", "Введите цену за единицу");
                    break;
                }
                case "client":{
                    descriptionError.put("client", "Выберите клиента");
                    break;
                }
                case "orderStatus":{
                    descriptionError.put("orderStatus", "Выберите статус заказа");
                    break;
                }
            }});

        return descriptionError;
    }
    public Map<String, String> checkError(OrderProductDTO orderProductDTO, BindingResult result){
        Map<String,String> descriptionError = new HashMap<>();
        if (orderProductDTO.getCountProductFromAssortment()<0) descriptionError.put("countProductFromAssortment","Неверное количество!");
        if (orderProductDTO.getCostForOneProduct()<0) descriptionError.put("costForOneProduct","Неверная цена!");
        return descriptionError;
    }
    public List<Orders> getAllOrder() {
        return ordersRepository.findAll();
    }
}
