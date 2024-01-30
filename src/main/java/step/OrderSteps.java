package step;

import dto.GetOrderForOneUserRequest;
import dto.OrderCreateRequest;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import lombok.AllArgsConstructor;
import send.OrderSend;

import java.util.ArrayList;

@AllArgsConstructor
public class OrderSteps {
    private final OrderSend orderSend;

    @Step("Send request body and get response for create order")
    public ValidatableResponse createOrder(ArrayList<String> ingredients, String token) {
        OrderCreateRequest orderCreateRequest = new OrderCreateRequest();
        orderCreateRequest.setIngredients(ingredients);
        orderCreateRequest.setToken(token);
        return orderSend.createOrder(token, orderCreateRequest).then();
    }

    @Step("Send request body and get response for get ingredients")
    public ValidatableResponse getIngredients() {
        return orderSend.getIngredients().then();
    }

    @Step("Send request body and get response for get order for one user")
    public ValidatableResponse getOrderForOneUser(String token) {
        GetOrderForOneUserRequest getOrderForOneUserRequest = new GetOrderForOneUserRequest();
        getOrderForOneUserRequest.setToken(token);
        return orderSend.getOrderForOneUser(token).then();
    }
}
