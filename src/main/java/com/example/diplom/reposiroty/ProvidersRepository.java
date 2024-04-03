package com.example.diplom.reposiroty;

import com.example.diplom.entity.Providers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvidersRepository extends JpaRepository<Providers, Integer> {
    boolean existsByProviderEmail(String providerEmail);
}
