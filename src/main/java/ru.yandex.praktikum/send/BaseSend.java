package ru.yandex.praktikum.send;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static ru.yandex.praktikum.config.ApiConfig.BASE_URL;

public class BaseSend {
    protected RequestSpecification getDefaultRequestSpecification() {
        return given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON);
    }
}
