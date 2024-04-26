package com.example.diplom.dto;

import lombok.*;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SupplyProductDTO {
    private int idAssortment;
    private int countProductFromAssortment;
    private double costForOneProduct;
    private Date productStartdata;
    private Date productEnddata;
}
