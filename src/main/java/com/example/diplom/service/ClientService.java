package com.example.diplom.service;

import com.example.diplom.entity.Client;
import com.example.diplom.entity.Workers;
import com.example.diplom.reposiroty.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void addNewUser(Client client) {
        clientRepository.save(client);
    }
}
