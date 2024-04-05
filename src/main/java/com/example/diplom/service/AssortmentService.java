package com.example.diplom.service;

import com.example.diplom.entity.Assortment;
import com.example.diplom.reposiroty.AssortmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssortmentService {
    private final AssortmentRepository assortmentRepository;
    // в этом методе я предполагаю уникального название товара
    public String checkNewAssortment(Assortment assortment) {
        if (assortmentRepository.existsByProductName(assortment.getProductName())) return "Такая товар уже существует";
        return null;
    }

    public void addNewAssortment(Assortment assortment) {
        assortmentRepository.save(assortment);
    }

    public List<Assortment> getAllAssortment() {
        return assortmentRepository.findAll();
    }
}
