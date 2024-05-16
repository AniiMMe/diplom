package com.example.diplom.reposiroty;

import com.example.diplom.entity.ReasonsReturn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReasonsReturnRepository extends JpaRepository<ReasonsReturn, Long> {
}
