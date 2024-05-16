package com.example.diplom.config;

import java.util.HashMap;
import java.util.Map;

public class AnswerMessage {
    public static Map<String, String> getOKMessage(String msg){
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", msg);
        return response;
    }
    public static Map<String, String> getBadMessage(Map<String, String> response){
        response.put("status", "bad");
        return response;
    }
}
