package com.example.diplom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class OrderProductDTO {

    private int idAssortment;
    private int CountProductFromAssortment;
    private double costForOneProduct;

}
