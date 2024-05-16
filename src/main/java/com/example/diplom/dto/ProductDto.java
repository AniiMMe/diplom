package com.example.diplom.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {

    private int idProduct;
    @NotEmpty
    private String nameProduct;
    private int colvo;
    @NotEmpty
    private String prichina;

}
