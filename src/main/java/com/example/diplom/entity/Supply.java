package com.example.diplom.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Supply {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "supply_id")
    private int supplyId;
    @Basic
    @Column(name = "supply_date")
    private Date supplyDate;
    @Basic
    @Column(name = "supply_cost")
    private double supplyCost;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id")
    private Providers providers;

    @ManyToMany
    @JoinTable(name = "product_supply",
            joinColumns = @JoinColumn(name="supply_id"),
    inverseJoinColumns = @JoinColumn(name="product_id"))
    private List<Product> products;


}
