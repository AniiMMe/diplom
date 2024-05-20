package com.example.diplom.entity;

import com.example.diplom.dto.WorkersDTO;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.Base64;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Workers extends User {
    @Basic
    @Column(name = "worker_name")
    private String workerName;
    @Basic
    @Column(name = "worker_surname")
    private String workerSurname;
    @Basic
    @Column(name = "worker_email")
    private String workerEmail;
    @Basic
    @Column(name = "worker_tel")
    private String workerTel;

    public WorkersDTO build() {

        WorkersDTO workersDTO = new WorkersDTO();
        workersDTO.setRoles(getRoles());
        workersDTO.setLogin(getLogin());
        workersDTO.setUserPassward(getUserPassward());
        workersDTO.setActive(isActive());
        workersDTO.setWorkerEmail(workerEmail);
        workersDTO.setWorkerName(workerName);
        workersDTO.setWorkerSurname(workerSurname);
        workersDTO.setWorkerTel(workerTel);
        return workersDTO;
    }
}
