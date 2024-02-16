package com.example.diplom.entity;

import lombok.*;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "catalog")
public class Catalog {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = " id_catalog")
    private int idCatalog;
    @Basic
    @Column(name = "name_catalog")
    private String nameCatalog;
    @Basic
    @Column(name = "discription")
    private String discription;

    public int getIdCatalog() {
        return idCatalog;
    }

    public void setIdCatalog(int idCatalog) {
        this.idCatalog = idCatalog;
    }

    public String getNameCatalog() {
        return nameCatalog;
    }

    public void setNameCatalog(String nameCatalog) {
        this.nameCatalog = nameCatalog;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Catalog catalog = (Catalog) o;
        return idCatalog == catalog.idCatalog && Objects.equals(nameCatalog, catalog.nameCatalog) && Objects.equals(discription, catalog.discription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCatalog, nameCatalog, discription);
    }
}
