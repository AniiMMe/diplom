package com.example.diplom.service;

import com.example.diplom.entity.Product;
import com.example.diplom.entity.Supply;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplyService {
    private final SupplyRepository supplyRepository;

    public void addNewSupply(Supply supply, List<Product> products) {
        supply.setProducts(products);
        supplyRepository.save(supply);
    }

    public List<Supply> getAllSupplies() {
        return supplyRepository.findAll();
    }
}
