package com.example.diplom.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReturnProductDto {
    private Long idOrder;
    private Date dateReturn;
    private List<ProductDto> products;

}
