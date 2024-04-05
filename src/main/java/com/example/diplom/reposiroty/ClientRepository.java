package com.example.diplom.reposiroty;

import com.example.diplom.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Integer> {
    boolean existsClientByClientEmail(String clientEmail);
}
