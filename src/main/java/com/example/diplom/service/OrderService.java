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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrdersRepository ordersRepository;
    private final ProductRepository productRepository;
    private final AssortmentRepository assortmentRepository;
    private final ClientRepository clientRepository;


    public void addNewOrder(OrderDTO order, List<OrderProductDTO> productList) {
        List<Product> productsListItog = new ArrayList<>();
        productList.forEach(x -> {
            Assortment assortment = assortmentRepository.getById(x.getIdAssortment());
            assortment.setCount(assortment.getCount() - x.getCountProductFromAssortment());
            int count = x.getCountProductFromAssortment();
            List<Product> writeOffProductToOrder =
                    productRepository.findAllByAssortmentOrderByProductEnddataDesc(assortment);
            for (Product y : writeOffProductToOrder) {
                if (count < y.getProductQuantity()) {
                    y.setProductQuantity(count - y.getProductQuantity());
                    productsListItog.add(productRepository.save(y));
                    break;
                } else if (count == y.getProductQuantity()) {
                    y.setProductQuantity(0);
                    y.setWriteOff(true);
                    productsListItog.add(productRepository.save(y));
                    break;
                } else {
                    y.setProductQuantity(0);
                    y.setWriteOff(true);
                    count -= y.getProductQuantity();
                    productsListItog.add(productRepository.save(y));
                }
            }
            assortmentRepository.save(assortment);
        });
        Orders orders = Orders.builder()
                .orderCost(productList.stream().mapToDouble(x ->
                                (x.getCostForOneProduct() * x.getCountProductFromAssortment()))
                        .sum())
                .orderQuantity(productList.stream().mapToInt(OrderProductDTO::getCountProductFromAssortment)
                        .sum())
                .orderStatus(order.getOrderStatus())
                .orderDate(order.getOrderDate())
                .products(productsListItog)
                .client(clientRepository.getById(order.getClient()))
                .build();
        ordersRepository.save(orders);
        log.info("СФОРМИРОВАН ЗАКАЗ {}", orders.toString());
    }

    public Map<String, String> checkErrorSwitch(OrderDTO orderDTO, List<OrderProductDTO> orderProductDTO, BindingResult result) {
        Map<String, String> descriptionError = new HashMap<>();
        result.getFieldErrors().forEach(error -> {
            switch (error.getField()) {
                case "orderDate": {
                    descriptionError.put("orderDate", "Введите дату");
                    break;
                }
                case "idAssortment": {
                    descriptionError.put("idAssortment", "Выберите товар");
                    break;
                }
                case "countProductFromAssortment": {
                    descriptionError.put("countProductFromAssortment", "Введите количество товара");
                    break;
                }
                case "costForOneProduct": {
                    descriptionError.put("costForOneProduct", "Введите цену за единицу");
                    break;
                }
                case "client": {
                    descriptionError.put("client", "Выберите клиента");
                    break;
                }
                case "orderStatus": {
                    descriptionError.put("orderStatus", "Выберите статус заказа");
                    break;
                }
            }
        });

        return descriptionError;
    }

    public Map<String, String> checkError(OrderProductDTO orderProductDTO, BindingResult result) {
        Map<String, String> descriptionError = new HashMap<>();

        if (orderProductDTO.getIdAssortment() == 0) descriptionError.put("idAssortment", "Выберите товар!");
        if (orderProductDTO.getCountProductFromAssortment() == 0)
            descriptionError.put("countProductFromAssortment", "Введите количество товара!");
        if (orderProductDTO.getCostForOneProduct() == 0)
            descriptionError.put("costForOneProduct", "Введите цену за единицу!");
        if (orderProductDTO.getCountProductFromAssortment() < 0)
            descriptionError.put("countProductFromAssortment", "Неверное количество!");
        if (orderProductDTO.getCostForOneProduct() < 0) descriptionError.put("costForOneProduct", "Неверная цена!");
        return descriptionError;
    }

    public List<Orders> getAllOrder() {
        return ordersRepository.findAll();
    }
}
