package com.example.diplom.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Providers {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "provider_id")
    private int providerId;
    @Basic
    @Column(name = "provider_name")
    private String providerName;
    @Basic
    @Column(name = "provider_address")
    private String providerAddress;
    @Basic
    @Column(name = "provider_tel")
    private String providerTel;
    @Basic
    @Column(name = "provider_email")
    private String providerEmail;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Supply> supplies;

}
