package com.example.diplom.service;

import com.example.diplom.dto.ReturnProductDto;
import com.example.diplom.dto.SearchData;
import com.example.diplom.dto.UserDTO;
import com.example.diplom.entity.Assortment;
import com.example.diplom.entity.Product;
import com.example.diplom.entity.ReasonsReturn;
import com.example.diplom.entity.ReturnProduct;
import com.example.diplom.reposiroty.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReturnProductService {
    private final ReturnProductRepository returnProductRepository;
    private final ReasonsReturnRepository reasonsReturnRepository;
    private final OrdersRepository ordersRepository;
    private final ProductRepository productRepository;
    private final AssortmentRepository assortmentRepository;
    private final EntityManager entityManager;
    private final UserRepository userRepository;

    public List<ReturnProduct> getAll() {
        return returnProductRepository.findAll();
    }

    public Map<String, String> checkError(ReturnProductDto returnProductDto, BindingResult result) {
        Map<String,String> descriptionError = new HashMap<>();
        result.getFieldErrors().forEach(error ->{
            switch (error.getField()){
                case "dateReturn":{
                    descriptionError.put("dateReturn", "ТЫ ДУРАК!");
                }

            }});
        return descriptionError;
    }

    public void add(ReturnProductDto returnProductDto) {
        ReturnProduct returnProduct = ReturnProduct
                .builder()
                .dateReturn(returnProductDto.getDateReturn())
                .orders(ordersRepository.getById(returnProductDto.getIdOrder()))
                .build();
        List<ReasonsReturn> productsList = new ArrayList<>();
        returnProductDto.getProducts().forEach(x->{
            ReasonsReturn reasonsReturn = new ReasonsReturn();
            Product product = productRepository.getById(x.getIdProduct());
            Assortment assortment = assortmentRepository.getById
                    (product.getAssortment().getIdAssort());
            assortment.setCount(assortment.getCount()-x.getColvo());
            assortmentRepository.save(assortment);
            if (product.getProductQuantity() - x.getColvo()==0){
                product.setProductQuantity(0);
            }
            else {
                product.setProductQuantity(product.getProductQuantity() - x.getColvo());
            }
            reasonsReturn = ReasonsReturn.builder()
                    .countProduct(x.getColvo())
                    .product(productRepository.save(product))
                    .reasons(x.getPrichina())
                    .build();
            productsList.add(reasonsReturnRepository.save(reasonsReturn));
        });
        returnProduct.setReasonsReturns(productsList);
        returnProductRepository.save(returnProduct);
    }
//    public List<ReturnProductDto> findAllByAccount(String name, SearchData searchData) {
//        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
//
//        CriteriaQuery<ReturnProductDto> query = builder.createQuery(ReturnProductDto.class);
//        Root<ReturnProductDto> root = query.from(ReturnProductDto.class);
//        query.select(root);
//
//        List<ReturnProduct> returnProducts = new ArrayList<>();
//
//        if (searchData.getSortParam() != null && !searchData.getSortParam().isEmpty()) {
//            if (searchData.getHowSort().equals("asc")) {
//                switch (searchData.getSortParam()) {
//                    case "idReturnProduct":
//                        returnProducts.add(builder.asc(root.get("idReturnProduct")));
//                        break;
//                    case "dateReturn":
//                        returnProducts.add(builder.asc(root.get("dateReturn")));
//                        break;
//
//                    default:
//                        throw new IllegalStateException("Unexpected value: " + searchData.getSortParam());
//                }
//            } else {
//                switch (searchData.getSortParam()) {
//                    case "idReturnProduct":
//                        returnProducts.add(builder.desc(root.get("idReturnProduct")));
//                        break;
//                    case "dateReturn":
//                        returnProducts.add(builder.desc(root.get("dateReturn")));
//                        break;
//
//
//                }
//            }
//        }
//
//        if (!returnProducts.isEmpty()) {
//            query.orderBy(returnProducts);
//        }
//
//        List<Predicate> predicates = new ArrayList<>();
//
//        if (searchData.getSearchQuery() != null && !searchData.getSearchQuery().isEmpty()) {
//            switch (searchData.getSearchParam()) {
//
//                case "idReturnProduct":
//                    predicates.add(builder.like(root.get("idReturnProduct"), searchData.getSearchQuery()));
//                    break;
//                case "dateReturn":
//                    predicates.add(builder.like(root.get("dateReturn"), searchData.getSearchQuery()));
//                    break;
//
//            }
//        }
//
//        Predicate searchPredicate = builder.and(predicates.toArray(new Predicate[0]));
//        query.where(searchPredicate);
//        predicates.add(builder.equal(root.get("account"), userRepository.findByLogin(name)));
//        query.where(searchPredicate);
//        TypedQuery<ReturnProductDto> typedQuery = entityManager.createQuery(query);
//        List<ReturnProductDto> ReturnProductDtoList = new ArrayList<>();
//        typedQuery.getResultList().forEach(x->{
//            ReturnProductDtoList.add(x.build());
//        });
//        return ReturnProductDtoList;
//    }
}
