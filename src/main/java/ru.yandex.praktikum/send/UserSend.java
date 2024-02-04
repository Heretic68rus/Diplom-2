package ru.yandex.praktikum.send;

import ru.yandex.praktikum.dto.UserCreateRequest;
import ru.yandex.praktikum.dto.UserLoginRequest;
import ru.yandex.praktikum.dto.UserUpdateRequest;
import io.qameta.allure.Step;
import io.restassured.response.Response;

public class UserSend extends BaseSend {
    @Step("Send POST request to /uth/register")
    public Response createUser(UserCreateRequest userCreateRequest) {
        return getDefaultRequestSpecification()
                .body(userCreateRequest)
                .when()
                .post("/auth/register");
    }

    @Step("Send POST request to //auth/login")
    public Response loginUser(UserLoginRequest userLoginRequest) {
        return getDefaultRequestSpecification()
                .body(userLoginRequest)
                .when()
                .post("/auth/login");
    }

    @Step("Send PATCH request to /auth/user")
    public Response updateUser(String token, UserUpdateRequest userUpdateRequest) {
        return getDefaultRequestSpecification()
                .body(userUpdateRequest)
                .auth().oauth2(token)
                .when()
                .patch("/auth/user");
    }

    @Step("Send DELETE request to /auth/login")
    public Response deleteUser() {
        return getDefaultRequestSpecification()
                .when()
                .delete("/auth/login");
    }
}
