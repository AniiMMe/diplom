package com.example.diplom.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ReasonsReturn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReasonsReturn;
    @Column
    private String reasons;
    @Column
    private Long countProduct;
    @OneToOne
    private Product product;
    @ManyToOne
    private ReturnProduct returnProduct;
}
