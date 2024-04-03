package com.example.diplom.reposiroty;

import com.example.diplom.entity.Assortment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssortmentRepository extends JpaRepository<Assortment, Integer> {
    boolean existsByProductName(String productName);
    Assortment findAllByProductName(String productName);
}
