package com.example.diplom.dto;

import com.example.diplom.entity.Assortment;
import com.example.diplom.entity.Client;
import com.example.diplom.entity.Orders;
import com.example.diplom.entity.StatusOrder;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private int client;
    private StatusOrder orderStatus;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date orderDate;

}
