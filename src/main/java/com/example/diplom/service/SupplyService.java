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

import java.util.ArrayList;
import java.util.List;

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

    public List<Supply> getAllSupplies() {
        return supplyRepository.findAll();
    }
}
