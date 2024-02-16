package com.example.diplom.entity;

import lombok.*;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "product")
public class Product {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "product_id")
    private int productId;
    @Basic
    @Column(name = "product_startdata")
    private Timestamp productStartdata;
    @Basic
    @Column(name = "product_price")
    private Double productPrice;
    @Basic
    @Column(name = "product_quantity")
    private int productQuantity;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "idAssortment")
    private Assortment assortment;
    @Column
    private Timestamp productEnddata;

//    @ManyToMany(mappedBy = "products")
//    private List<Supply> supplies;

}


