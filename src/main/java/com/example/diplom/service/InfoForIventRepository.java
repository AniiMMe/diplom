package com.example.diplom.service;

import com.example.diplom.entity.Assortment;
import com.example.diplom.entity.InfoForIvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InfoForIventRepository extends JpaRepository<InfoForIvent, Long> {
    List<InfoForIvent> findByAssortment(Assortment assortment);
}
