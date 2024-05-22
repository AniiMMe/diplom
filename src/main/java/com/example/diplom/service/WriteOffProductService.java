package com.example.diplom.service;

import com.example.diplom.entity.Product;
import com.example.diplom.entity.WriteOffProduct;
import com.example.diplom.reposiroty.ProductRepository;
import com.example.diplom.reposiroty.WriteOffProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WriteOffProductService {
    private final WriteOffProductRepository writeOffProductRepository;
    private final ProductRepository productRepository;

    public void addProduct(WriteOffProduct writeOffProduct) {
        Product product = productRepository.getById(writeOffProduct.getIdProduct());
        writeOffProductRepository.save(WriteOffProduct.builder()
                        .productName(product.getProductName())
                        .productPrice(product.getProductPrice())
                        .productQuantity(product.getProductQuantity())
                        .productStartdata(product.getProductStartdata())
                        .productEnddata(product.getProductEnddata())
                .build());
        product.setWriteOff(true);
        productRepository.save(product);
    }
}
