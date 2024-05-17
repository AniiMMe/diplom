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

    public String checkNewUser(ClientDTO client) {
        if (clientRepository.existsClientByClientEmail(client.getClientEmail())) return "Такая почта уже существует";
        return null;

    }

    public Map<String, String> checkErrorSwitch(ClientDTO clientDTO,  BindingResult result){
        Map<String,String> descriptionError = new HashMap<>();
        result.getFieldErrors().forEach(error ->{
            switch (error.getField()){
                case "clientName":{
                    descriptionError.put("clientName", "Введите ФИО");
                    break;
                }
                case "clientEmail":{
                    descriptionError.put("clientEmail", "Введите Электронную почту");
                    break;
                }
                case "clientAddress":{
                    descriptionError.put("clientAddress", "Введите адрес клиента");
                    break;
                }
                case "clientTel":{
                    descriptionError.put("clientTel", "Введите номер телефона");
                    break;
                }
            }});
        if (checkNewUser(clientDTO)!=null)
            descriptionError.put("clientEmail", checkNewUser(clientDTO));
        return descriptionError;
    }



    public void addNewUser(Client client) {
        clientRepository.save(client);
    }
}
