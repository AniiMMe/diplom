package com.example.diplom.service;

import com.example.diplom.entity.Workers;
import com.example.diplom.reposiroty.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final MailSender mailSender;
    public Map<String, String> checkNewUser(Workers workers) {
        Map<String,String> descriptionError = new HashMap<>();
        if (userRepository.existsByLogin(workers.getLogin())) descriptionError.put("login", "Такой логин уже существует!");
        if (userRepository.existsByWorkerEmail(workers.getWorkerEmail())) descriptionError.put("workerEmail", "Такая почта уже существует");
        if (userRepository.existsByWorkerTel(workers.getWorkerTel())) descriptionError.put("workerTel", "Такой номер уже существует!");
        return descriptionError;
    }

    public void addNewUser(Workers workers) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(workers.getPassword());
        workers.setUserPassward(encodedPassword);
        if (!StringUtils.isEmpty(workers.getWorkerEmail())) {
            String message = String.format(
                    "Здравствуйте, %s %s! \n" +
                            "Добро пожаловать в систему склада. " +
                            "Перейдите по ссылке для авторизации: http://localhost:8088/login",
                    workers.getWorkerSurname(), workers.getWorkerName()
            );

            mailSender.send(workers.getWorkerEmail(), "Activation url", message);
        }
        userRepository.save(workers);

    }

    public List<Workers> getAllWorkers(){
        return userRepository.findAll();
    }
}
