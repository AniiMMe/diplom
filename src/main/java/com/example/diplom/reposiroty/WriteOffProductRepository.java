package com.example.diplom.reposiroty;

import com.example.diplom.entity.WriteOffProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WriteOffProductRepository extends JpaRepository<WriteOffProduct, Long> {
}
