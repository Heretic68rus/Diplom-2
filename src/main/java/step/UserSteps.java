package step;

import dto.UserCreateRequest;
import dto.UserLoginRequest;
import dto.UserUpdateRequest;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import lombok.AllArgsConstructor;
import send.UserSend;

@AllArgsConstructor
public class UserSteps {
    private final UserSend userSend;

    @Step("Send request body and get response for create user")
    public ValidatableResponse createUser(String email, String password, String name) {
        UserCreateRequest userCreateRequest = new UserCreateRequest();
        userCreateRequest.setEmail(email);
        userCreateRequest.setPassword(password);
        userCreateRequest.setName(name);
        return userSend.createUser(userCreateRequest).then();
    }

    @Step("Send request body and get response for login user")
    public ValidatableResponse loginUser(String email, String password) {
        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setEmail(email);
        userLoginRequest.setPassword(password);
        return userSend.loginUser(userLoginRequest).then();
    }

    @Step("Send request body and get response for update user")
    public ValidatableResponse updateUser(String email, String name, String token) {
        UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
        userUpdateRequest.setEmail(email);
        userUpdateRequest.setName(name);
        userUpdateRequest.setToken(token);
        return userSend.updateUser(token, userUpdateRequest).then();
    }

    @Step("Send request body and get response for delete user")
    public ValidatableResponse deleteUser() {
        return userSend.deleteUser().then();
    }
}
