package com.example.diplom.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;
import java.util.Map;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class ReturnProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReturnProduct;
    @Column
    private Date dateReturn;
    @OneToMany
    private List<ReasonsReturn> reasonsReturns;
    @OneToOne
    private Orders orders;

}
