package com.example.diplom.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;
import java.util.Map;

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
