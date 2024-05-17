package com.example.diplom.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class OrderProductDTO {
    @NotEmpty
    @NonNull
    private int idAssortment;
    @NotEmpty
    @NonNull
    private int countProductFromAssortment;
    @NotEmpty
    @NonNull
    private double costForOneProduct;

}
