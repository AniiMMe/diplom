package com.example.diplom.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@Table(name = "product")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Product {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "product_id")
    private int productId;
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
    private Boolean writeOff = false;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "idAssortment")
    @JsonIgnore
    private Assortment assortment;
    @Column
    private Date productEnddata;
    @ManyToOne
    @JsonIgnore
    private Supply supplies;
    @JsonIgnore
    @ManyToMany(mappedBy = "products")
    private List<Orders> orders;
    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<ProductInvent> productInvents;
}


