package ru.yandex.praktikum.send;

import ru.yandex.praktikum.dto.OrderCreateRequest;
import io.qameta.allure.Step;
import io.restassured.response.Response;

public class OrderSend extends BaseSend {
    @Step("Send POST request to /orders")
    public Response createOrder(String token, OrderCreateRequest orderCreateRequest) {
        return getDefaultRequestSpecification()
                .body(orderCreateRequest)
                .auth().oauth2(token)
                .when()
                .post("/orders");
    }

    @Step("Send GET request to /orders")
    public Response getOrderForOneUser(String token) {
        return getDefaultRequestSpecification()
                .given()
                .auth().oauth2(token)
                .get("/orders");
    }

    @Step("Send GET request to /ingredients")
    public Response getIngredients() {
        return getDefaultRequestSpecification()
                .given()
                .get("/ingredients");
    }
}
