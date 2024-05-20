package com.example.diplom.service;

import com.example.diplom.entity.InfoForIvent;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class InfoForIventService {
    private final InfoForIventRepository infoForIventRepository;


    public List<InfoForIvent> getAll() {
        return infoForIventRepository.findAll();
    }
}
