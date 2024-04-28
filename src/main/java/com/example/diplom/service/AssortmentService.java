package com.example.diplom.service;

import com.example.diplom.dto.InfoDTO;
import com.example.diplom.dto.InfoForIventDTO;
import com.example.diplom.entity.Assortment;
import com.example.diplom.entity.InfoForIvent;
import com.example.diplom.entity.Product;
import com.example.diplom.reposiroty.AssortmentRepository;
import com.example.diplom.reposiroty.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

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

    public InfoForIvent addInfoForIvent(InfoForIventDTO infoForIventDTO) {
        List<Assortment> assortments = new ArrayList<>();
        for (InfoDTO info:infoForIventDTO.getInfoDTOS()) {
            Assortment assortment = assortmentRepository.findById(info.getIdAssort()).orElse(null);
            if (assortment != null) assortments.add(assortment);
        }
        InfoForIvent infoForIvent = InfoForIvent.builder()
                .priseQuantity(infoForIventDTO.getInfoDTOS().stream().mapToDouble(InfoDTO::getPriseQuantity).boxed().toList())
                .productQuantity(infoForIventDTO.getInfoDTOS().stream().mapToInt(InfoDTO::getProductQuantity).boxed().toList())
                .factQuantity(infoForIventDTO.getInfoDTOS().stream().mapToInt(InfoDTO::getFactQuantity).boxed().toList())
                .assortment(assortments)
                .build();
        infoForIvent.setFactQuantityItog(infoForIvent.getFactQuantity().stream().mapToInt(Integer::intValue).sum());
        infoForIvent.setProductQuantityItog(infoForIvent.getProductQuantity().stream().mapToInt(Integer::intValue).sum());
        infoForIvent.setPriseQuantityItog(infoForIvent.getPriseQuantity().stream().mapToDouble(Double::doubleValue).sum());
        return infoForIventRepository.save(infoForIvent);
    }
}
