package com.example.diplom.service;


import com.example.diplom.dto.ClientDTO;
import com.example.diplom.dto.ProvidersDTO;
import com.example.diplom.entity.Providers;
import com.example.diplom.reposiroty.ProvidersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProvidersService {
    private final ProvidersRepository providersRepository;

    public String checkNewProvider(ProvidersDTO provider) {
        if (providersRepository.existsByProviderEmail(provider.getProviderEmail())) return "Такая почта уже существует";
        return null;
    }
    public Map<String, String> checkErrorSwitch(ProvidersDTO providersDTO, BindingResult result){
        Map<String,String> descriptionError = new HashMap<>();
        result.getFieldErrors().forEach(error ->{
            switch (error.getField()){
                case "providerName":{
                    descriptionError.put("providerName", "Введите ФИО");
                    break;
                }
                case "providerEmail":{
                    descriptionError.put("providerEmail", "Введите Электронную почту");
                    break;
                }
                case "providerAddress":{
                    descriptionError.put("providerAddress", "Введите адрес клиента");
                    break;
                }
                case "providerTel":{
                    descriptionError.put("providerTel", "Введите номер телефона");
                    break;
                }
            }});
        if (checkNewProvider(providersDTO)!=null)
            descriptionError.put("providerEmail", checkNewProvider(providersDTO));

        return descriptionError;
    }


    public void addNewProvider(ProvidersDTO provider) {
        providersRepository.save(provider.build());
    }

    public List<Providers> getAllProvider() {
        return providersRepository.findAll();
    }

    public ProvidersDTO findById(int id) {
        return providersRepository.getById(id).build();
    }

    public void changeProvider(ProvidersDTO provider, int id) {
        Providers providersDB = providersRepository.getById(id);
        if (!providersDB.getProviderAddress().equals(provider.getProviderAddress()))
            providersDB.setProviderAddress(provider.getProviderAddress());
        if (!providersDB.getProviderName().equals(provider.getProviderName()))
            providersDB.setProviderName(provider.getProviderName());
        if (!providersDB.getProviderEmail().equals(provider.getProviderEmail()))
            providersDB.setProviderEmail(provider.getProviderEmail());
        if (!providersDB.getProviderTel().equals(provider.getProviderTel()))
            providersDB.setProviderTel(provider.getProviderTel());
        providersRepository.save(providersDB);
    }
}
