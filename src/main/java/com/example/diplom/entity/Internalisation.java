package com.example.diplom.entity;

import lombok.*;

import jakarta.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "internalisation")
public class Internalisation {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "invent_id")
    private int inventId;
    @Basic
    @Column(name = "invent_date")
    private Date inventDate;
    @Basic
    @Column(name = "checkCount")
    private String checkCount;
    @Basic
    @Column(name = "checkSrok")
    private String checkSrok;


}
