package com.example.diplom.service;

import com.example.diplom.dto.ClientDTO;
import com.example.diplom.entity.Client;

import com.example.diplom.entity.Workers;
import com.example.diplom.reposiroty.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public String checkNewUser(Client client) {
        if (clientRepository.existsClientByClientEmail(client.getClientEmail())) return "Такая почта уже существует";
        return null;

    }

    public Map<String, String> checkErrorSwitch(ClientDTO returnClientDTO,  BindingResult result){
        Map<String,String> descriptionError = new HashMap<>();
        result.getFieldErrors().forEach(error ->{
            switch (error.getField()){
                case "fio":{
                    descriptionError.put("fio", "Введите ФИО");
                    break;
                }
                case "email":{
                    descriptionError.put("email", "Введите Электронную почту");
                    break;
                }
                case "address":{
                    descriptionError.put("address", "Введите адрес клиента");
                    break;
                }
                case "phone":{
                    descriptionError.put("phone", "Введите номер телефона");
                    break;
                }
            }});
        return descriptionError;
    }



    public void addNewUser(Client client) {
        clientRepository.save(client);
    }
}
