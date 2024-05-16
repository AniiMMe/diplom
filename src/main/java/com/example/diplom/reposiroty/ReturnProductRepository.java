package com.example.diplom.reposiroty;

import com.example.diplom.entity.ReturnProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReturnProductRepository extends JpaRepository<ReturnProduct, Long> {
}
