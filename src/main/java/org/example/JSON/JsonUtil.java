package org.example.JSON;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class JsonUtil {

    private static final Gson gson = new Gson();

    public static <T> T parse_json(HttpExchange exchange, Class<T> clazz) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8))){
            return gson.fromJson(reader, clazz);
        }
    }

    public static String object_to_json(Object obj){
        return gson.toJson(obj);
    }

}
