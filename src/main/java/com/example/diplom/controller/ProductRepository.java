package com.example.diplom.controller;

import com.example.diplom.entity.Assortment;
import com.example.diplom.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findAllByAssortmentAndProductEnddataIsBetween(Assortment assortment, Timestamp productEnddata, Timestamp productEnddata2);
}
