package com.example.diplom.entity;

import com.example.diplom.dto.InfoForIventDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class InfoForIvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idIvent;
    @Column
    @ElementCollection
    private List<Integer> factQuantity;
    @Column
    @ElementCollection
    private List<Integer> productQuantity;
    @Column
    @ElementCollection
    private List<Double> priseQuantity;
    @Column
    private int factQuantityItog;
    @Column
    private int productQuantityItog;
    @Column
    private double priseQuantityItog;
    @ManyToMany
    private List<Assortment> assortment;

}
