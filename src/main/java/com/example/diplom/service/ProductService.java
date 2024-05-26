package com.example.diplom.service;

import com.example.diplom.dto.ProductDto;
import com.example.diplom.dto.SearchData;
import com.example.diplom.dto.UserDTO;
import com.example.diplom.entity.Assortment;
import com.example.diplom.entity.Orders;
import com.example.diplom.entity.Product;
import com.example.diplom.reposiroty.AssortmentRepository;
import com.example.diplom.reposiroty.OrdersRepository;
import com.example.diplom.reposiroty.ProductRepository;
import com.example.diplom.reposiroty.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
    private final EntityManager entityManager;
    private final UserRepository userRepository;

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

//    public List<ProductDto> findAllByAccount(String name, SearchData searchData) {
//        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
//
//        CriteriaQuery<ProductDto> query = builder.createQuery(ProductDto.class);
//        Root<ProductDto> root = query.from(ProductDto.class);
//        query.select(root);
//
//        List<Product> products = new ArrayList<>();
//
//        if (searchData.getSortParam() != null && !searchData.getSortParam().isEmpty()) {
//            if (searchData.getHowSort().equals("asc")) {
//                switch (searchData.getSortParam()) {
//                    case "productId":
//                        products.add(builder.asc(root.get("productId")));
//                        break;
//                    case "productName":
//                        products.add(builder.asc(root.get("productName")));
//                        break;
//                    case "productStartdata":
//                        products.add(builder.asc(root.get("productStartdata")));
//                        break;
//                    case "productPrice":
//                        products.add(builder.asc(root.get("productPrice")));
//                        break;
//                    case "productQuantity":
//                        products.add(builder.asc(root.get("productQuantity")));
//                        break;
//                    case "productEnddata":
//                        products.add(builder.asc(root.get("productEnddata")));
//                        break;
//                    default:
//                        throw new IllegalStateException("Unexpected value: " + searchData.getSortParam());
//                }
//            } else {
//                switch (searchData.getSortParam()) {
//                    case "productId":
//                        products.add(builder.desc(root.get("userId")));
//                        break;
//                    case "productName":
//                        products.add(builder.desc(root.get("login")));
//                        break;
//                    case "productStartdata":
//                        products.add(builder.desc(root.get("workerName")));
//                        break;
//                    case "productPrice":
//                        products.add(builder.desc(root.get("workerSurname")));
//                        break;
//                    case "productQuantity":
//                        products.add(builder.desc(root.get("productQuantity")));
//                        break;
//                    case "productEnddata":
//                        products.add(builder.desc(root.get("productEnddata")));
//                        break;
//
//                }
//            }
//        }
//
//        if (!products.isEmpty()) {
//            query.orderBy(products);
//        }
//
//        List<Predicate> predicates = new ArrayList<>();
//
//        if (searchData.getSearchQuery() != null && !searchData.getSearchQuery().isEmpty()) {
//            switch (searchData.getSearchParam()) {
//
//                case "productId":
//                    predicates.add(builder.like(root.get("productId"), searchData.getSearchQuery()));
//                    break;
//                case "productName":
//                    predicates.add(builder.like(root.get("productName"), searchData.getSearchQuery()));
//                    break;
//                case "productStartdata":
//                    predicates.add(builder.like(root.get("productStartdata"), searchData.getSearchQuery()));
//                    break;
//                case "productPrice":
//                    predicates.add(builder.like(root.get("productPrice"), searchData.getSearchQuery()));
//                    break;
//                case "productQuantity":
//                    predicates.add(builder.like(root.get("productQuantity"), searchData.getSearchQuery()));
//                    break;
//                case "productEnddata":
//                    predicates.add(builder.like(root.get("productEnddata"), searchData.getSearchQuery()));
//                    break;
//
//            }
//        }
//
//        Predicate searchPredicate = builder.and(predicates.toArray(new Predicate[0]));
//        query.where(searchPredicate);
//        predicates.add(builder.equal(root.get("account"), userRepository.findByLogin(name)));
//        query.where(searchPredicate);
//        TypedQuery<ProductDto> typedQuery = entityManager.createQuery(query);
//        List<ProductDto> productDtoList = new ArrayList<>();
//        typedQuery.getResultList().forEach(x->{
//            productDtoList.add(x.build());
//        });
//        return productDtoList;
//    }
}
