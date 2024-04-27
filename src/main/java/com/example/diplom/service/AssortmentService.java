package com.example.diplom.service;

import com.example.diplom.dto.InfoForIventDTO;
import com.example.diplom.entity.Assortment;
import com.example.diplom.entity.InfoForIvent;
import com.example.diplom.entity.Product;
import com.example.diplom.reposiroty.AssortmentRepository;
import com.example.diplom.reposiroty.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AssortmentService {
    private final AssortmentRepository assortmentRepository;
    private final ProductRepository productRepository;
    private final InfoForIventRepository infoForIventRepository;

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

    public List<Assortment> getAllAssortmentForInvent() {
        return assortmentRepository.findAll();
    }

    public List<InfoForIvent> addInfoForIvent(InfoForIventDTO infoForIventDTO, int idAssortment) {
        Assortment assortment = assortmentRepository.findById(idAssortment).orElse(null);
        List<InfoForIvent> infoForIventFromDB = infoForIventRepository.findByAssortment(assortment);
        if (assortment == null) return null;
        InfoForIvent infoForIvent = InfoForIvent.builder()
                    .priseQuantity(Arrays.stream(infoForIventDTO.getPriseQuantity()).boxed().collect(Collectors.toList()))
                    .productQuantity(Arrays.stream(infoForIventDTO.getProductQuantity()).boxed().collect(Collectors.toList()))
                    .factQuantity(Arrays.stream(infoForIventDTO.getFactQuantity()).boxed().collect(Collectors.toList()))
                    .factQuantityItog(Arrays.stream(infoForIventDTO.getFactQuantity()).boxed()
                            .toList()
                            .stream().mapToInt(Integer::intValue).sum())
                    .productQuantityItog(Arrays.stream(infoForIventDTO.getProductQuantity()).boxed()
                            .toList()
                            .stream().mapToInt(Integer::intValue).sum())
                    .priseQuantityItog(Arrays.stream(infoForIventDTO.getProductQuantity()).boxed()
                            .toList()
                            .stream().mapToDouble(Integer::doubleValue).sum())
                    .assortment(assortment)
                    .build();
        infoForIventFromDB.add(infoForIventRepository.save(infoForIvent));

        return infoForIventFromDB;

    }
}
