package ru.yandex.praktikum.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateRequest {
    private ArrayList<String> ingredients;
    private String token;
}
