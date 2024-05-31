package com.example.diplom.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

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
    private int idProduct;
    @Column
    private String reasonWriteOff;
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
    @OneToOne
    public Workers admin;

}
