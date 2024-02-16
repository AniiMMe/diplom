package com.example.diplom.entity;

import lombok.*;

import jakarta.persistence.*;
import java.sql.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
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
    private int orderStatus;

   }
