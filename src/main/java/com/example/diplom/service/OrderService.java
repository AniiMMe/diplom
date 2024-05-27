package com.example.diplom.service;

import com.example.diplom.dto.*;
import com.example.diplom.entity.Assortment;
import com.example.diplom.entity.Client;
import com.example.diplom.entity.Orders;
import com.example.diplom.entity.Product;
import com.example.diplom.reposiroty.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.validation.BindingResult;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
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
    private final UserRepository userRepository;
    private final EntityManager entityManager;

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
    public Map<String, String> checkErrorSwitch(OrderDTO orderDTO, List<OrderProductDTO> orderProductDTO, BindingResult result){
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

        if(orderProductDTO.getIdAssortment()==0) descriptionError.put("idAssortment", "Выберите товар!");
        if(orderProductDTO.getCountProductFromAssortment()==0) descriptionError.put("countProductFromAssortment", "Введите количество товара!");
        if(orderProductDTO.getCostForOneProduct()==0) descriptionError.put("costForOneProduct", "Введите цену за единицу!");
        if (orderProductDTO.getCountProductFromAssortment()<0) descriptionError.put("countProductFromAssortment","Неверное количество!");
        if (orderProductDTO.getCostForOneProduct()<0) descriptionError.put("costForOneProduct","Неверная цена!");
        return descriptionError;
    }
    public List<Orders> getAllOrder() {
        return ordersRepository.findAll();
    }

    public List<Orders> getAllOrder( SearchData searchData) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Orders> query = builder.createQuery(Orders.class);
        Root<Orders> root = query.from(Orders.class);
        query.select(root);

        List<Order> orders = new ArrayList<>();

        if (searchData.getSortParam() != null && !searchData.getSortParam().isEmpty()) {
            if (searchData.getHowSort().equals("asc")) {
                switch (searchData.getSortParam()) {
                    case "ordersId":
                        orders.add(builder.asc(root.get("ordersId")));
                        break;
                    case "orderDate":
                        orders.add(builder.asc(root.get("orderDate")));
                        break;
                    case "orderQuantity":
                        orders.add(builder.asc(root.get("orderQuantity")));
                        break;
                    case "orderStatus":
                        orders.add(builder.asc(root.get("orderStatus")));
                        break;

                    default:
                        throw new IllegalStateException("Unexpected value: " + searchData.getSortParam());
                }
            } else {
                switch (searchData.getSortParam()) {
                    case "ordersId":
                        orders.add(builder.desc(root.get("ordersId")));
                        break;
                    case "orderDate":
                        orders.add(builder.desc(root.get("orderDate")));
                        break;
                    case "orderQuantity":
                        orders.add(builder.desc(root.get("orderQuantity")));
                        break;

                    case "orderStatus":
                        orders.add(builder.desc(root.get("orderStatus")));
                        break;


                }
            }
        }

        if (!orders.isEmpty()) {
            query.orderBy(orders);
        }

        List<Predicate> predicates = new ArrayList<>();

        if (searchData.getSearchQuery() != null && !searchData.getSearchQuery().isEmpty()) {
            switch (searchData.getSearchParam()) {

                case "ordersId":
                    predicates.add(builder.like(root.get("ordersId"), searchData.getSearchQuery()));
                    break;
                case "orderDate":
                    predicates.add(builder.like(root.get("orderDate"), searchData.getSearchQuery()));
                    break;
                case "orderQuantity":
                    predicates.add(builder.like(root.get("orderQuantity"), searchData.getSearchQuery()));
                    break;
                case "orderStatus":
                    predicates.add(builder.like(root.get("orderStatus"), searchData.getSearchQuery()));
                    break;


            }
        }

        Predicate searchPredicate = builder.and(predicates.toArray(new Predicate[0]));
        query.where(searchPredicate);
        TypedQuery<Orders> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
    }
}
