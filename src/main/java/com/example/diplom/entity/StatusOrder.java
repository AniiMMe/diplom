package com.example.diplom.entity;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
@Getter
public enum StatusOrder {
    PROCESSING,     // Обработка
    COMPLETED,      // Завершено
    CANCELLED,      // Отменено
    DELIVERED;     // Доставлено

    private static Map<StatusOrder, String> statusInRussian = new HashMap<>();

    static {
        statusInRussian.put(StatusOrder.PROCESSING,"Обработка");
        statusInRussian.put(StatusOrder.COMPLETED,"Завершено");
        statusInRussian.put(StatusOrder.CANCELLED,"Отменено");
        statusInRussian.put(StatusOrder.DELIVERED,"Доставлено");
    }
}
