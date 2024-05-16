package com.example.diplom.service;

import com.example.diplom.entity.ReasonsReturn;
import com.example.diplom.entity.ReturnProduct;
import com.example.diplom.reposiroty.ReasonsReturnRepository;
import com.example.diplom.reposiroty.ReturnProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReturnProductService {
    private final ReturnProductRepository returnProductRepository;
    private final ReasonsReturnRepository reasonsReturnRepository;

    public List<ReturnProduct> getAll() {
        return returnProductRepository.findAll();
    }
}
