package com.example.diplom.reposiroty;

import com.example.diplom.entity.Workers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Workers, Integer> {
//    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM Workers u WHERE u.login = :login")
    boolean existsByLogin(String login);
//    @Query("select w from Workers w where w.login =:login")
    UserDetails findByLogin(String login);
    boolean existsByWorkerEmail(String email);
    boolean existsByWorkerTel(String phone);
    List<Workers> findAll();
}
