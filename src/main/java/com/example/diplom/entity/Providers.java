package com.example.diplom.entity;

import com.example.diplom.dto.ClientDTO;
import com.example.diplom.dto.ProvidersDTO;
import lombok.*;

import javax.persistence.*;

import java.util.List;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
@Builder
public class Providers {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "provider_id")
    private int providerId;
    @Basic
    @Column(name = "provider_name")
    private String providerName;
    @Basic
    @Column(name = "provider_address")
    private String providerAddress;
    @Basic
    @Column(name = "provider_tel")
    private String providerTel;
    @Basic
    @Column(name = "provider_email")
    private String providerEmail;

    @OneToMany(mappedBy = "providers")
    private List<Supply> supplies;
    @OneToOne
    public Workers admin;

    public ProvidersDTO build() {
        return ProvidersDTO.builder()
                .providerId(providerId)
                .providerAddress(providerAddress)
                .providerEmail(providerEmail)
                .providerName(providerName)
                .providerTel(providerTel)
                .build();
    }
}
