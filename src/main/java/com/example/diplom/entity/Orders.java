package com.example.diplom.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
@Builder
public class Orders {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int ordersId;
    @Basic
    @Column(name = "order_date")
    private Date orderDate;
    @Basic
    @Column(name = "order_cost")
    private double orderCost;
    @Basic
    @Column(name = "order_quantity")
    private double orderQuantity;
    @Basic
    @Column(name = "order_status")
    private StatusOrder orderStatus;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;
    @OneToOne
    public Workers workers;

    @ManyToMany
    @JoinTable(name = "product_orders",
            joinColumns = @JoinColumn(name="order_id"),
            inverseJoinColumns = @JoinColumn(name="product_id"))
    private List<Product> products;
   }
