package com.example.diplom.service;


import com.example.diplom.dto.UserDTO;
import com.example.diplom.dto.WorkersDTO;
import com.example.diplom.entity.UserRole;
import com.example.diplom.entity.Workers;
import com.example.diplom.reposiroty.UserRepository;
import lombok.AllArgsConstructor;
import org.apache.catalina.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import com.example.diplom.dto.SearchData;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.*;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final MailSender mailSender;
    private final EntityManager entityManager;
    public Map<String, String> checkNewUser(Workers workers) {
        Map<String,String> descriptionError = new HashMap<>();
        if (userRepository.existsByLogin(workers.getLogin())) descriptionError.put("login", "Такой логин уже существует!");
        if (userRepository.existsByWorkerEmail(workers.getWorkerEmail())) descriptionError.put("workerEmail", "Такая почта уже существует");
        if (userRepository.existsByWorkerTel(workers.getWorkerTel())) descriptionError.put("workerTel", "Такой номер уже существует!");
        return descriptionError;
    }

    public void addNewUser(WorkersDTO workers) {
        Workers worker = workers.build();
        worker.setActive(workers.isActive());
        worker.setLogin(workers.getLogin());
        worker.setRoles(Collections.singleton(UserRole.USER));
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(workers.getPassword());
        worker.setUserPassward(encodedPassword);
        if (!StringUtils.isEmpty(workers.getWorkerEmail())) {
            String message = String.format(
                    "Здравствуйте, %s %s! \n" +
                            "Добро пожаловать в систему склада. " +
                            "Перейдите по ссылке для авторизации: http://localhost:8088/login",
                    workers.getWorkerSurname(), workers.getWorkerName()
            );

            mailSender.send(workers.getWorkerEmail(), "Activation url", message);
        }
        userRepository.save(worker);

    }
    public Map<String, String> checkErrorSwitch(WorkersDTO workersDTO, BindingResult result){
        Map<String,String> descriptionError = new HashMap<>();
        result.getFieldErrors().forEach(error ->{
            switch (error.getField()){
                case "workerName":{
                    descriptionError.put("workerName", "Введите имя");
                    break;
                }
                case "workerSurname":{
                    descriptionError.put("workerSurname", "Введите фамилию");
                    break;
                }
                case "workerEmail":{
                    descriptionError.put("workerEmail", "Введите Электронную почту");
                    break;
                }
                case "active":{
                    descriptionError.put("active", "Активируйте пользователя");
                    break;
                }
                case "workerTel":{
                    descriptionError.put("workerTel", "Введите номер телефона");
                    break;
                }
                case "login":{
                    descriptionError.put("login", "Введите логин");
                    break;
                }
                case "userPassward":{
                    descriptionError.put("userPassward", "Введите пароль");
                    break;
                }
            }});

        return descriptionError;
    }

    public List<Workers> getAllWorkers(){
        return userRepository.findAll();
    }
    public String getRole(String login){
        return userRepository.findByLogin(login).getRoles().toString();
    }

    public Workers getWorkersByID(int id) {
        return userRepository.getById(id);
    }


    public void changeUser(WorkersDTO workers, int id) {
        Workers workersFromDB = userRepository.getById(id);
        if (workers.isActive()!=workersFromDB.isActive())
            workersFromDB.setActive(workers.isActive());
        if (!workers.getWorkerSurname().equals(workersFromDB.getWorkerSurname()))
            workersFromDB.setWorkerSurname(workers.getWorkerSurname());
        if (!workers.getWorkerName().equals(workersFromDB.getWorkerName()))
            workersFromDB.setWorkerName(workers.getWorkerName());
        if (!workers.getWorkerEmail().equals(workersFromDB.getWorkerEmail()))
            workersFromDB.setWorkerEmail(workers.getWorkerEmail());
        if (!workers.getWorkerTel().equals(workersFromDB.getWorkerTel()))
            workersFromDB.setWorkerTel(workers.getWorkerTel());
        if (workers.getPassword()!=null) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(workers.getPassword());
            workersFromDB.setUserPassward(encodedPassword);
        }
        userRepository.save(workersFromDB);
    }

    public void deleteClient(int id) {
        userRepository.delete(userRepository.getById(id));
    }


    public List<Workers> getAllWorkers(SearchData searchData) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Workers> query = builder.createQuery(Workers.class);
        Root<Workers> root = query.from(Workers.class);
        query.select(root);

        List<Order> orders = new ArrayList<>();

        if (searchData.getSortParam() != null && !searchData.getSortParam().isEmpty()) {
            if (searchData.getHowSort().equals("asc")) {
                switch (searchData.getSortParam()) {
                    case "userId":
                        orders.add(builder.asc(root.get("userId")));
                        break;
                    case "login":
                        orders.add(builder.asc(root.get("login")));
                        break;
                    case "workerName":
                        orders.add(builder.asc(root.get("workerName")));
                        break;
                    case "workerSurname":
                        orders.add(builder.asc(root.get("workerSurname")));
                        break;
                    case "workerTel":
                        orders.add(builder.asc(root.get("workerTel")));
                        break;
                    case "workerEmail":
                        orders.add(builder.asc(root.get("workerEmail")));
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + searchData.getSortParam());
                }
            } else {
                switch (searchData.getSortParam()) {
                    case "userId":
                        orders.add(builder.desc(root.get("userId")));
                        break;
                    case "login":
                        orders.add(builder.desc(root.get("login")));
                        break;
                    case "workerName":
                        orders.add(builder.desc(root.get("workerName")));
                        break;
                    case "workerSurname":
                        orders.add(builder.desc(root.get("workerSurname")));
                        break;
                    case "workerTel":
                        orders.add(builder.desc(root.get("workerTel")));
                        break;
                    case "workerEmail":
                        orders.add(builder.desc(root.get("workerEmail")));
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

                case "userId":
                    predicates.add(builder.like(root.get("userId"), searchData.getSearchQuery()));
                    break;
                case "login":
                    predicates.add(builder.like(root.get("login"), searchData.getSearchQuery()));
                    break;
                case "workerName":
                    predicates.add(builder.like(root.get("workerName"), searchData.getSearchQuery()));
                    break;
                case "workerSurname":
                    predicates.add(builder.like(root.get("workerSurname"), searchData.getSearchQuery()));
                    break;
                case "workerTel":
                    predicates.add(builder.like(root.get("workerTel"), searchData.getSearchQuery()));
                    break;
                case "workerEmail":
                    predicates.add(builder.like(root.get("workerEmail"), searchData.getSearchQuery()));
                    break;

            }
        }

        Predicate searchPredicate = builder.and(predicates.toArray(new Predicate[0]));
        query.where(searchPredicate);
        TypedQuery<Workers> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
    }
}
