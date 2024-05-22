package com.example.diplom.entity;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
public enum StatusOrder {
    PROCESSING,     // Обработка
    COMPLETED,      // Завершено
    CANCELLED,      // Отменено
    DELIVERED;     // Доставлено
@Getter
    public static Map<StatusOrder, String> statusInRussian = new HashMap<>();

    static {
        statusInRussian.put(StatusOrder.PROCESSING,"Обработка");
        statusInRussian.put(StatusOrder.COMPLETED,"Завершено");
        statusInRussian.put(StatusOrder.CANCELLED,"Отменено");
        statusInRussian.put(StatusOrder.DELIVERED,"Доставлено");
    }
    public static Map<StatusOrder, String>  getStatus(){
        return statusInRussian;
    }
}
