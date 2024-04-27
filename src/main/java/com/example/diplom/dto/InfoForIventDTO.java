package com.example.diplom.dto;

import com.example.diplom.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InfoForIventDTO {
    private int[] factQuantity = new int[100];
    private int[] productQuantity = new int[100];
    private double[] priseQuantity = new double[100];

    private int factQuantityItog;
    private int productQuantityItog;
    private double priseQuantityItog;
    public int getProductQuantity(List<Product> products){
        return products.stream().mapToInt(Product::getProductQuantity).filter(Objects::nonNull).sum();
    }
    public double getPriseQuantity(List<Product> products){
        return products.stream().mapToDouble(Product::getProductPrice).filter(Objects::nonNull).sum();
    }
}
