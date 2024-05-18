package com.example.diplom.dto;

import com.example.diplom.entity.ProductInvent;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.sql.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InternalisationDTO {

    private int inventId;
    @NonNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date inventDate;
    @NotEmpty
    @NonNull
    @Pattern(regexp = "\\d+")
    private String checkCount;
    @NonNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private String checkSrok;


    private List<ProductInvent> productInvents;
}
