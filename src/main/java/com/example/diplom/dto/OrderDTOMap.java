package com.example.diplom.dto;

import com.example.diplom.entity.Client;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderDTOMap {
    private Client client;
    private String orderStatus;
    private Date orderDate;
    List<OrderProductDTO> orderProductDTOS;
}
