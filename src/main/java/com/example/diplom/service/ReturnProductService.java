package com.example.diplom.service;

import com.example.diplom.dto.ReturnProductDto;
import com.example.diplom.entity.Assortment;
import com.example.diplom.entity.Product;
import com.example.diplom.entity.ReasonsReturn;
import com.example.diplom.entity.ReturnProduct;
import com.example.diplom.reposiroty.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

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
}
