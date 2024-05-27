package com.example.diplom.service;


import com.example.diplom.dto.ClientDTO;
import com.example.diplom.dto.ProvidersDTO;
import com.example.diplom.dto.SearchData;
import com.example.diplom.dto.UserDTO;
import com.example.diplom.entity.Providers;
import com.example.diplom.reposiroty.ProvidersRepository;
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
public class ProvidersService {
    private final ProvidersRepository providersRepository;
    private final EntityManager entityManager;
    private final UserRepository userRepository;
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
    public List<Providers> getAllProvider(SearchData searchData) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Providers> query = builder.createQuery(Providers.class);
        Root<Providers> root = query.from(Providers.class);
        query.select(root);

        List<Order> orders = new ArrayList<>();

        if (searchData.getSortParam() != null && !searchData.getSortParam().isEmpty()) {
            if (searchData.getHowSort().equals("asc")) {
                switch (searchData.getSortParam()) {
                    case "providerId":
                        orders.add(builder.asc(root.get("providerId")));
                        break;
                    case "providerName":
                        orders.add(builder.asc(root.get("providerName")));
                        break;
                    case "providerAddress":
                        orders.add(builder.asc(root.get("providerAddress")));
                        break;
                    case "providerTel":
                        orders.add(builder.asc(root.get("providerTel")));
                        break;
                    case "providerEmail":
                        orders.add(builder.asc(root.get("providerEmail")));
                        break;

                    default:
                        throw new IllegalStateException("Unexpected value: " + searchData.getSortParam());
                }
            } else {
                switch (searchData.getSortParam()) {
                    case "providerId":
                        orders.add(builder.desc(root.get("providerId")));
                        break;
                    case "providerName":
                        orders.add(builder.desc(root.get("providerName")));
                        break;
                    case "providerAddress":
                        orders.add(builder.desc(root.get("workerName")));
                        break;
                    case "providerTel":
                        orders.add(builder.desc(root.get("providerTel")));
                        break;
                    case "providerEmail":
                        orders.add(builder.desc(root.get("providerEmail")));
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

                case "providerId":
                    predicates.add(builder.like(root.get("providerId"), searchData.getSearchQuery()));
                    break;
                case "providerName":
                    predicates.add(builder.like(root.get("providerName"), searchData.getSearchQuery()));
                    break;
                case "providerAddress":
                    predicates.add(builder.like(root.get("providerAddress"), searchData.getSearchQuery()));
                    break;
                case "providerTel":
                    predicates.add(builder.like(root.get("providerTel"), searchData.getSearchQuery()));
                    break;
                case "providerEmail":
                    predicates.add(builder.like(root.get("providerEmail"), searchData.getSearchQuery()));
                    break;


            }
        }

        Predicate searchPredicate = builder.and(predicates.toArray(new Predicate[0]));
        query.where(searchPredicate);
        TypedQuery<Providers> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
    }
}
