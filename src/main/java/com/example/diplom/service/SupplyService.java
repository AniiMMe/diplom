package com.example.diplom.service;

import com.example.diplom.controller.SupplyDTO;
import com.example.diplom.dto.SupplyProductDTO;
import com.example.diplom.entity.Product;
import com.example.diplom.entity.Supply;
import com.example.diplom.reposiroty.AssortmentRepository;
import com.example.diplom.reposiroty.ProductRepository;
import com.example.diplom.reposiroty.ProvidersRepository;
import com.example.diplom.reposiroty.SupplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

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
}
