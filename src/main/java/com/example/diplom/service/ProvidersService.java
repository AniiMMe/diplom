package com.example.diplom.service;

import com.example.diplom.entity.Providers;
import com.example.diplom.reposiroty.ProvidersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProvidersService {
    private final ProvidersRepository providersRepository;

    public String checkNewProvider(Providers provider) {
        if (providersRepository.existsByProviderEmail(provider.getProviderEmail())) return "Такая почта уже существует";
        return null;
    }

    public void addNewProvider(Providers provider) {
        providersRepository.save(provider);
    }

    public List<Providers> getAllProvider() {
        return providersRepository.findAll();
    }
}
