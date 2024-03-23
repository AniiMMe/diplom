package com.example.diplom.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

}
