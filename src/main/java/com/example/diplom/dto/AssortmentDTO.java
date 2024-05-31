package com.example.diplom.dto;

import com.example.diplom.entity.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssortmentDTO {

    private int idAssort;
    @NotEmpty
    @NonNull
    private String manufacturer;
    @NotEmpty
    @NonNull
    private String productName;
    private String productType;

    private int count;

    private FormVipuska formIn;

    private List<Product> products = new ArrayList<>();

    private List<InfoForIvent> infoForIvents;


    public Assortment build() {
        return Assortment.builder()
                .count(count)
                .formIn(formIn)
                .manufacturer(manufacturer)
                .productName(productName)
                .productType(productType)
                .build();
    }
}
