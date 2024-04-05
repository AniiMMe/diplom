package com.example.diplom.dto;

import com.example.diplom.entity.Assortment;
import com.example.diplom.entity.Client;
import com.example.diplom.entity.Orders;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderDTO {
    //client: document.getElementById("client").value,
    //            orderStatus: document.getElementById("status").value,
    //            orderDate: document.getElementById("date").value
    private Client client;
    private String orderStatus;
    private Date orderDate;

}
