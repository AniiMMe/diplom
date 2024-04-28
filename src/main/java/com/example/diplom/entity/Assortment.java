package com.example.diplom.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "assortment")
public class Assortment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_assort")
    private int idAssort;
    @Basic
    @Column(name = "manufacturer")
    private String manufacturer;
    @Basic
    @Column(name = "product_name")
    private String productName;
    @Basic
    @Column(name = "product_type")
    private String productType;
    @Basic
    @Column(name = "count")
    private int count;
    @Basic
    @Column(name = "formIn")
    private String formIn;

    @ManyToOne
    @JoinColumn(name = "id_catalog")
    private Catalog catalog;
    @JsonIgnore
    @OneToMany(mappedBy = "assortment",  cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();
    @ManyToMany
    private List<InfoForIvent> infoForIvents;



}
