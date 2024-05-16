package com.example.diplom.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class ReasonsReturn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReasonsReturn;
    @Column
    private String reasons;
    @Column
    private int countProduct;
    @OneToOne
    private Product product;

}
