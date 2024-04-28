package com.example.diplom.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplyDTO {
    private int id;
    private Date supplyDate;

}
