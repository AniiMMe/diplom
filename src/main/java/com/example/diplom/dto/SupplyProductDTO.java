package com.example.diplom.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SupplyProductDTO {
    @NotEmpty
    @NonNull
    private int idAssortment;
    @NotEmpty
    @NonNull
    private int countProductFromAssortment;
    @NotEmpty
    @NonNull
    private double costForOneProduct;
    @NonNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date productStartdata;
    @NonNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date productEnddata;
}
