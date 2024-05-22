package com.example.diplom.service;

import com.example.diplom.entity.Assortment;
import com.example.diplom.entity.Orders;
import com.example.diplom.entity.Product;
import com.example.diplom.reposiroty.AssortmentRepository;
import com.example.diplom.reposiroty.OrdersRepository;
import com.example.diplom.reposiroty.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository   productRepository;
    private final AssortmentRepository assortmentRepository;
    private final OrdersRepository ordersRepository;

    public List<Product> addNewProduct(List<Product> productList, List<String> assortment) {
        List<Product> productListFromDB = new ArrayList<>();
        AtomicInteger indexAssortment = new AtomicInteger(0);
        for(Product product: productList){
            Assortment assortmentDB = assortmentRepository
                    .findAllByProductName(assortment.get(indexAssortment.getAndIncrement()));
            product.setAssortment(assortmentDB);
            assortmentDB.setCount(assortmentDB.getCount() + product.getProductQuantity());
            assortmentRepository.save(assortmentDB);
            productListFromDB.add(productRepository.save(product));
        }
        return productListFromDB;
    }
    public boolean checkProduct(Assortment assortment, int col){
        List<String> idProduct = new ArrayList<>();
        List<Product> productList = productRepository.findAllByAssortmentAndProductEnddataIsBetween(assortment,
                Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now().plusDays(5)));
        int count = col;
        for (Product product : productList){
             count-= product.getProductId();
            if (count>0 || count==0) {
                product.setWriteOff(true);
                productRepository.save(product);
            }
            else {
                count = product.getProductQuantity() + count;
                product.setProductQuantity(product.getProductQuantity() - count);
                count=0;
                productRepository.save(product);
                break;
            }
        }
        if (count!=0) return false;
        return true;

    }

    public Map<Assortment,String> getProductForOrder(List<Product> productList, List<String> assortment) {
        Map<Assortment,String>  productMapId = new HashMap<>();
        AtomicInteger indexAssortment = new AtomicInteger(0);
        for(Product product: productList){
            Assortment assortmentDB = assortmentRepository
                    .findAllByProductName(assortment.get(indexAssortment.getAndIncrement()));
            if(assortmentDB.getCount() - product.getProductQuantity() < 0) {
                productMapId.put(assortmentDB, "Недостаточно товара");
                continue;
            }
            assortmentDB.setCount(assortmentDB.getCount() - product.getProductQuantity());
            assortmentRepository.save(assortmentDB);
            if(!checkProduct(assortmentDB, product.getProductQuantity())){
                productMapId.put(assortmentDB, "Недостаточно товара");
            }
        }
        return productMapId;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAllByWriteOff(false);
    }

    public List<Product> getAllProductsByOrder(int id) {
        Orders orders = ordersRepository.getById(id);
        return orders.getProducts();
    }

    public void deleteProvider(int id) {
        productRepository.delete(productRepository.getById(id));
    }
}
