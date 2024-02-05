package ru.yandex.praktikum.user;

import jdk.jfr.Description;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.send.UserSend;
import ru.yandex.praktikum.step.UserSteps;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.hamcrest.Matchers.is;

public class UserCreateTest {
    private UserSteps userSteps;

    @Before
    public void setUp() {
        userSteps = new UserSteps(new UserSend());
    }

    @Test
    @Description("Создание нового пользователя")
    public void createUserSuccessfully() {
        String email = RandomStringUtils.randomAlphabetic(5) + "@mail.com";
        String password = RandomStringUtils.randomAlphabetic(5) + RandomStringUtils.randomNumeric(5);
        String name = RandomStringUtils.randomAlphabetic(10);

        userSteps
                .createUser(email, password, name)
                .body("success", is(true));
    }

    @Test
    @Description("Создание существующего пользователя")
    public void createSameUserReturnStatusCode403() {
        String email = RandomStringUtils.randomAlphabetic(5) + "@mail.com";
        String password = RandomStringUtils.randomAlphabetic(5) + RandomStringUtils.randomNumeric(5);
        String name = RandomStringUtils.randomAlphabetic(10);

        userSteps
                .createUser(email, password, name);
        userSteps
                .createUser(email, password, name)
                .statusCode(SC_FORBIDDEN)
                .body("success", is(false))
                .body("message", is("User already exists"));
    }

    @After
    public void deleteUser() {
        userSteps
                .deleteUser();
    }
}
