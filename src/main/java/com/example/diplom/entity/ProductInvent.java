package com.example.diplom.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.Objects;
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table
public class ProductInvent {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "product_invent_id")
    private int productInventId;
    @Basic
    @Column(name = "product_id")
    private int productId;
    @Basic
    @Column(name = "prod_remainder")
    private int prodRemainder;
    @Basic
    @Column(name = "invent_id")
    private int inventId;

}
