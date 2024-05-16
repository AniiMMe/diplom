package com.example.diplom.entity;

import lombok.Data;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
public enum FormVipuska {
    PACK, BOX, BOTTLE, RULLON;
    @Getter
    private static final Map<FormVipuska, String> russianName = new HashMap<>();

    static {
        russianName.put(PACK,"Пачка");
        russianName.put(BOX, "Коробка");
        russianName.put(BOTTLE, "Бутылка");
        russianName.put(RULLON, "Рулон");
    }
    public static String getName(FormVipuska formVipuska){
        return russianName.get(formVipuska);
    }
}
