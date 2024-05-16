package com.example.diplom.dto;

import com.example.diplom.entity.Catalog;
import com.example.diplom.entity.FormVipuska;
import com.example.diplom.entity.InfoForIvent;
import com.example.diplom.entity.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssortmentDTO {

    private int idAssort;

    private String manufacturer;

    private String productName;

    private String productType;

    private int count;

    private FormVipuska formIn;

    private Catalog catalog;
    private List<Product> products = new ArrayList<>();

    private List<InfoForIvent> infoForIvents;



}
