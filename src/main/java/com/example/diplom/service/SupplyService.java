package com.example.diplom.service;

import com.example.diplom.controller.SupplyDTO;
import com.example.diplom.dto.SearchData;
import com.example.diplom.dto.SupplyProductDTO;
import com.example.diplom.dto.UserDTO;
import com.example.diplom.entity.Product;
import com.example.diplom.entity.Supply;
import com.example.diplom.reposiroty.*;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class SupplyService {
    private final SupplyRepository supplyRepository;
    private final ProductRepository productRepository;
    private final AssortmentRepository assortmentRepository;
    private final ProvidersRepository providersRepository;
    private final EntityManager entityManager;
    private final UserRepository userRepository;

    public void addNewSupply(SupplyDTO supplyDto, List<SupplyProductDTO> products) {
        List<Product> productList = new ArrayList<>();
        products.forEach(x->{
            productList.add(productRepository.save(Product.builder()
                            .productName(assortmentRepository.findById(x.getIdAssortment()).orElse(null).getProductName())
                            .productStartdata(x.getProductStartdata())
                            .productQuantity(x.getCountProductFromAssortment())
                            .productPrice(x.getCostForOneProduct())
                            .productEnddata(x.getProductEnddata())
                            .assortment(assortmentRepository.findById(x.getIdAssortment()).orElse(null))
                    .build()));
        });
        Supply supply = new Supply();
        supply.setProviders(providersRepository.findById(supplyDto.getId()).orElse(null));
        supply.setSupplyDate(supplyDto.getSupplyDate());
        supply.setSupplyCost(productList.stream().mapToDouble(x->x
                .getProductPrice()*x.getProductQuantity()).sum());
        supply.setProductsList(productList);
        Supply finalSupply = supplyRepository.save(supply);
        productList.forEach(x->{
            x.setSupplies(finalSupply);
            productRepository.save(x);
        });

    }

    public Map<String, String> checkErrorSwitch(SupplyDTO supplyDTO, List<SupplyProductDTO> supplyProductDTO, BindingResult result){
        Map<String,String> descriptionError = new HashMap<>();
        result.getFieldErrors().forEach(error ->{
            switch (error.getField()){
                case "supplyDate":{
                    descriptionError.put("supplyDate", "Введите дату");
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
                case "productStartdata":{
                    descriptionError.put("productStartdata", "Введите дату изготовления");
                    break;
                }
                case "productEnddata":{
                    descriptionError.put("productEnddata", "Введите срок годности");
                    break;
                }
            }});

        return descriptionError;
    }

    public Map<String, String> checkError(SupplyProductDTO supplyProductDTO, BindingResult result){
        Map<String,String> descriptionError = new HashMap<>();
        if(supplyProductDTO.getIdAssortment()==0) descriptionError.put("idAssortment", "Выберите товар!");
        if(supplyProductDTO.getCountProductFromAssortment()==0) descriptionError.put("countProductFromAssortment", "Введите количество товара!");
        if(supplyProductDTO.getCostForOneProduct()==0) descriptionError.put("costForOneProduct", "Введите цену за единицу!");
        if(supplyProductDTO.getProductStartdata() == null) descriptionError.put("productStartdata", "Введите дату изготовления");
        if(supplyProductDTO.getProductEnddata() == null) descriptionError.put("productEnddata", "Введите срок годности");
        if (supplyProductDTO.getCountProductFromAssortment()<0) descriptionError.put("countProductFromAssortment","Неверное количество!");
        if (supplyProductDTO.getCostForOneProduct()<0) descriptionError.put("costForOneProduct","Неверная цена!");
        return descriptionError;
    }
    public List<Supply> getAllSupplies() {
        return supplyRepository.findAll();
    }

//    public List<SupplyDTO> findAllByAccount(String name, SearchData searchData) {
//        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
//
//        CriteriaQuery<SupplyDTO> query = builder.createQuery(SupplyDTO.class);
//        Root<SupplyDTO> root = query.from(SupplyDTO.class);
//        query.select(root);
//
//        List<SupplyRepository> supplyRepositories = new ArrayList<>();
//
//        if (searchData.getSortParam() != null && !searchData.getSortParam().isEmpty()) {
//            if (searchData.getHowSort().equals("asc")) {
//                switch (searchData.getSortParam()) {
//                    case "supplyId":
//                        supplyRepositories.add(builder.asc(root.get("supplyId")));
//                        break;
//                    case "supplyDate":
//                        supplyRepositories.add(builder.asc(root.get("supplyDate")));
//                        break;
//                    case "supplyCost":
//                        supplyRepositories.add(builder.asc(root.get("supplyCost")));
//                        break;
//                    default:
//                        throw new IllegalStateException("Unexpected value: " + searchData.getSortParam());
//                }
//            } else {
//                switch (searchData.getSortParam()) {
//                    case "supplyId":
//                        supplyRepositories.add(builder.desc(root.get("supplyId")));
//                        break;
//                    case "supplyDate":
//                        supplyRepositories.add(builder.desc(root.get("supplyDate")));
//                        break;
//                    case "supplyCost":
//                        supplyRepositories.add(builder.desc(root.get("supplyCost")));
//                        break;
//
//
//                }
//            }
//        }
//
//        if (!supplyRepositories.isEmpty()) {
//            query.orderBy(supplyRepositories);
//        }
//
//        List<Predicate> predicates = new ArrayList<>();
//
//        if (searchData.getSearchQuery() != null && !searchData.getSearchQuery().isEmpty()) {
//            switch (searchData.getSearchParam()) {
//
//                case "supplyId":
//                    predicates.add(builder.like(root.get("supplyId"), searchData.getSearchQuery()));
//                    break;
//                case "supplyDate":
//                    predicates.add(builder.like(root.get("supplyDate"), searchData.getSearchQuery()));
//                    break;
//                case "supplyCost":
//                    predicates.add(builder.like(root.get("supplyCost"), searchData.getSearchQuery()));
//                    break;
//
//            }
//        }
//
//        Predicate searchPredicate = builder.and(predicates.toArray(new Predicate[0]));
//        query.where(searchPredicate);
//        predicates.add(builder.equal(root.get("account"), userRepository.findByLogin(name)));
//        query.where(searchPredicate);
//        TypedQuery<SupplyDTO> typedQuery = entityManager.createQuery(query);
//        List<SupplyDTO>SupplyList = new ArrayList<>();
//        typedQuery.getResultList().forEach(x->{
//            SupplyList.add(x.build());
//        });
//        return SupplyList;
//    }
}
