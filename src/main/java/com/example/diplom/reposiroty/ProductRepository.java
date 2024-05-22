package com.example.diplom.reposiroty;

import com.example.diplom.entity.Assortment;
import com.example.diplom.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findAllByAssortmentAndProductEnddataIsBetween(Assortment assortment, Timestamp productEnddata, Timestamp productEnddata2);
    @Query("SELECT pr FROM Product  pr " +
            "WHERE pr.assortment =?1 " +
            "ORDER BY pr.productEnddata asc ")
    List<Product> findAllByAssortmentOrderByProductEnddataDesc(Assortment assortment);
    List<Product> findAllByWriteOff(Boolean writeOff);
}
