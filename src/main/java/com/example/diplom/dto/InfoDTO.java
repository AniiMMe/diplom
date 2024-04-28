package com.example.diplom.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InfoDTO {
    private int idAssort;
    private int productQuantity;
    private double priseQuantity;
    private int factQuantity;

}
