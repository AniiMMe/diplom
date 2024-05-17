package com.example.diplom.dto;

import com.example.diplom.entity.Catalog;
import com.example.diplom.entity.FormVipuska;
import com.example.diplom.entity.InfoForIvent;
import com.example.diplom.entity.Product;
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
    @NotEmpty
    @NonNull
    @Pattern(regexp = "^[А-ЯЁа-яё\s]+$")
    private String productType;

    private int count;

    private FormVipuska formIn;

    private Catalog catalog;
    private List<Product> products = new ArrayList<>();

    private List<InfoForIvent> infoForIvents;



}
