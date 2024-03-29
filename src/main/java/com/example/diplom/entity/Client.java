package com.example.diplom.entity;

import lombok.*;

import javax.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "client")
public class Client {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "client_id")
    private int clientId;
    @Basic
    @Column(name = "client_name")
    private String clientName;
    @Basic
    @Column(name = "client_address")
    private String clientAddress;
    @Basic
    @Column(name = "client_tel")
    private String clientTel;
    @Basic
    @Column(name = "client_email")
    private String clientEmail;

    @OneToMany(mappedBy = "client")
    private List<Orders> orders;
}
