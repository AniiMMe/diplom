package com.example.diplom.service;

import com.example.diplom.dto.*;
import com.example.diplom.entity.Assortment;
import com.example.diplom.entity.InfoForIvent;
import com.example.diplom.entity.Product;
import com.example.diplom.reposiroty.AssortmentRepository;
import com.example.diplom.reposiroty.ProductRepository;
import com.example.diplom.reposiroty.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
@RequiredArgsConstructor
public class AssortmentService {

    private final AssortmentRepository assortmentRepository;
    private final ProductRepository productRepository;
    private final InfoForIventRepository infoForIventRepository;
    private final EntityManager entityManager;
    private final UserRepository userRepository;
    // в этом методе я предполагаю уникального название товара
    public String checkNewAssortment(Assortment assortment) {
        if (assortmentRepository.existsByProductName(assortment.getProductName())) return "Такой товар уже существует";
        return null;
    }
    public Map<String, String> checkAssErrorSwitch(AssortmentDTO assortmentDTO, BindingResult result) {
        Map<String,String> descriptionError = new HashMap<>();
        result.getFieldErrors().forEach(error ->{
            switch (error.getField()){
                case "productName":{
                    descriptionError.put("productName", "Проверьте название");
                    break;
                }
                case "manufacturer":{
                    descriptionError.put("manufacturer", "Проверьте производителя");
                    break;
                } case "productType":{
                    descriptionError.put("productType", "Проверьте тип продукта");
                    break;
                }
            }});
        return descriptionError;
    }
    //бегаешь по case и смотришь где ошибка
    public Map<String, String> checkErrorSwitch(AssortmentDTO returnProductDto, BindingResult result) {
        Map<String,String> descriptionError = new HashMap<>();
        result.getFieldErrors().forEach(error ->{
            switch (error.getField()){
                case "dateReturn":{
                    descriptionError.put("dateReturn", "Неверная дата");
                    break;
                }
            }});
        return descriptionError;
    }
    // проверяешь логику
    public Map<String, String> checkError(AssortmentDTO returnProductDto, BindingResult result){
        Map<String,String> descriptionError = new HashMap<>();
        if (returnProductDto.getCount()<0) descriptionError.put("count","Неверное количество!");
        return descriptionError;
    }
    public void addNewAssortment(AssortmentDTO assortment) {
        assortmentRepository.save(assortment.build());
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
                .date(infoForIventDTO.getDate())
                .build();
        infoForIvent.setFactQuantityItog(infoForIvent.getFactQuantity().stream().mapToInt(Integer::intValue).sum());
        infoForIvent.setProductQuantityItog(infoForIvent.getProductQuantity().stream().mapToInt(Integer::intValue).sum());
        infoForIvent.setPriseQuantityItog(infoForIvent.getPriseQuantity().stream().mapToDouble(Double::doubleValue).sum());
        return infoForIventRepository.save(infoForIvent);
    }

    public AssortmentDTO getById(int id) {
        return assortmentRepository.getById(id).build();
    }

    public void change(AssortmentDTO assortment, int id) {
        Assortment assortmentDB = assortmentRepository.getById(id);
        if (!assortmentDB.getProductType().equals(assortment.getProductType()))
            assortmentDB.setProductType(assortment.getProductType());
        if (!assortmentDB.getProductName().equals(assortment.getProductName()))
            assortmentDB.setProductName(assortment.getProductName());
        if (!assortmentDB.getManufacturer().equals(assortment.getManufacturer()))
            assortmentDB.setManufacturer(assortment.getManufacturer());
        assortmentRepository.save(assortmentDB);
    }
    public List<Assortment> getAllAssortment(SearchData searchData) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Assortment> query = builder.createQuery(Assortment.class);
        Root<Assortment> root = query.from(Assortment.class);
        query.select(root);

        List<Order> orders = new ArrayList<>();

        if (searchData.getSortParam() != null && !searchData.getSortParam().isEmpty()) {
            if (searchData.getHowSort().equals("asc")) {
                switch (searchData.getSortParam()) {
                    case "idAssort":
                        orders.add(builder.asc(root.get("idAssort")));
                        break;
                    case "manufacturer":
                        orders.add(builder.asc(root.get("manufacturer")));
                        break;
                    case "productName":
                        orders.add(builder.asc(root.get("productName")));
                        break;
                    case "productType":
                        orders.add(builder.asc(root.get("productType")));
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + searchData.getSortParam());
                }
            } else {
                switch (searchData.getSortParam()) {
                    case "idAssort":
                        orders.add(builder.desc(root.get("idAssort")));
                        break;
                    case "manufacturer":
                        orders.add(builder.desc(root.get("manufacturer")));
                        break;
                    case "productName":
                        orders.add(builder.desc(root.get("productName")));
                        break;
                    case "productType":
                        orders.add(builder.desc(root.get("productType")));
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

                case "idAssort":
                    predicates.add(builder.like(root.get("idAssort"), searchData.getSearchQuery()));
                    break;
                case "manufacturer":
                    predicates.add(builder.like(root.get("manufacturer"), searchData.getSearchQuery()));
                    break;
                case "productName":
                    predicates.add(builder.like(root.get("productName"), searchData.getSearchQuery()));
                    break;
                case "productType":
                    predicates.add(builder.like(root.get("productType"), searchData.getSearchQuery()));
                    break;


            }
        }

        Predicate searchPredicate = builder.and(predicates.toArray(new Predicate[0]));
        query.where(searchPredicate);
        TypedQuery<Assortment> typedQuery = entityManager.createQuery(query);
        List<AssortmentDTO> AssortmentDTOList = new ArrayList<>();
        return typedQuery.getResultList();
    }
}
