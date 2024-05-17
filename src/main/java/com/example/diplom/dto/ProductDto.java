package com.example.diplom.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {

    private int idProduct;
    @NotEmpty
    @NonNull
    private String nameProduct;
    @NotEmpty
    @NonNull
    private int colvo;
    @NotEmpty
    @Pattern(regexp = "^[А-ЯЁа-яё\s]+$")
    private String prichina;

}
