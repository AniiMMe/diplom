package com.example.diplom.service;

import com.example.diplom.dto.ClientDTO;
import com.example.diplom.dto.SearchData;
import com.example.diplom.dto.UserDTO;
import com.example.diplom.entity.Client;

import com.example.diplom.entity.Workers;
import com.example.diplom.reposiroty.ClientRepository;
import com.example.diplom.reposiroty.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final EntityManager entityManager;
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
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

    public ClientDTO findById(int id) {
        return clientRepository.getById(id).build();
    }

    public void changeClient(Client build, int id) {
        Client client = clientRepository.getById(id);
        if (!client.getClientAddress().equals(build.getClientAddress()))
            client.setClientAddress(build.getClientAddress());
        if (!client.getClientName().equals(build.getClientName()))
            client.setClientName(build.getClientName());
        if (!client.getClientEmail().equals(build.getClientEmail()))
            client.setClientEmail(build.getClientEmail());
        if (!client.getClientTel().equals(build.getClientTel()))
            client.setClientTel(build.getClientTel());
        clientRepository.save(client);
    }

    public void deleteClient(int id) {
        clientRepository.delete(clientRepository.getById(id));
    }

    public List<Client> getAllClients(SearchData searchData) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Client> query = builder.createQuery(Client.class);
        Root<Client> root = query.from(Client.class);
        query.select(root);

        List<Order> orders = new ArrayList<>();

        if (searchData.getSortParam() != null && !searchData.getSortParam().isEmpty()) {
            if (searchData.getHowSort().equals("asc")) {
                switch (searchData.getSortParam()) {
                    case "clientId":
                        orders.add(builder.asc(root.get("clientId")));
                        break;
                    case "clientName":
                        orders.add(builder.asc(root.get("clientName")));
                        break;
                    case "clientAddress":
                        orders.add(builder.asc(root.get("clientAddress")));
                        break;
                    case "clientTel":
                        orders.add(builder.asc(root.get("clientTel")));
                        break;
                    case "clientEmail":
                        orders.add(builder.asc(root.get("clientEmail")));
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + searchData.getSortParam());
                }
            } else {
                switch (searchData.getSortParam()) {
                    case "clientId":
                        orders.add(builder.desc(root.get("clientId")));
                        break;
                    case "clientName":
                        orders.add(builder.desc(root.get("clientName")));
                        break;
                    case "clientAddress":
                        orders.add(builder.desc(root.get("clientAddress")));
                        break;
                    case "clientTel":
                        orders.add(builder.desc(root.get("clientTel")));
                        break;
                    case "clientEmail":
                        orders.add(builder.desc(root.get("clientEmail")));
                        break;


                }
            }
        }

        if (!orders.isEmpty()) {
            query.orderBy(orders);
        }

        List<Predicate> predicates = new ArrayList<>();

        if (searchData.getSearchQuery() != null && !searchData.getSearchQuery().isEmpty()) {
            switch (searchData.getSearchParam()) {

                case "clientId":
                    predicates.add(builder.like(root.get("clientId"), searchData.getSearchQuery()));
                    break;
                case "clientName":
                    predicates.add(builder.like(root.get("clientName"), searchData.getSearchQuery()));
                    break;
                case "clientAddress":
                    predicates.add(builder.like(root.get("clientAddress"), searchData.getSearchQuery()));
                    break;
                case "clientTel":
                    predicates.add(builder.like(root.get("clientTel"), searchData.getSearchQuery()));
                    break;
                case "clientEmail":
                    predicates.add(builder.like(root.get("clientEmail"), searchData.getSearchQuery()));
                    break;


            }
        }

        Predicate searchPredicate = builder.and(predicates.toArray(new Predicate[0]));
        query.where(searchPredicate);
        TypedQuery<Client> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
    }
}
