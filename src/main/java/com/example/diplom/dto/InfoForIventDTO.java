package com.example.diplom.dto;

import com.example.diplom.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InfoForIventDTO {

    private List<InfoDTO> infoDTOS;
    private Date date;

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
