package com.example.diplom.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class WriteOffProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idWriteOffProduct;
    @Column
    private String productName;
    @Basic
    @Column(name = "product_startdata")
    private Date productStartdata;
    @Basic
    @Column(name = "product_price")
    private Double productPrice;
    @Basic
    @Column(name = "product_quantity")
    private int productQuantity;
    @Column
    private Date productEnddata;

}
