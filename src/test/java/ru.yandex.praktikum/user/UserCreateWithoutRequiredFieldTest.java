package ru.yandex.praktikum.user;

import jdk.jfr.Description;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.praktikum.send.UserSend;
import ru.yandex.praktikum.step.UserSteps;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.hamcrest.Matchers.is;

@RunWith(Parameterized.class)
public class UserCreateWithoutRequiredFieldTest {
    private final String email;
    private final String password;
    private final String name;
    private UserSteps userSteps;

    public UserCreateWithoutRequiredFieldTest(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    @Parameterized.Parameters
    public static Object[][] data() {
        return new Object[][]{
                {RandomStringUtils.randomAlphabetic(5) + "@mail.com", RandomStringUtils.randomAlphabetic(5) + RandomStringUtils.randomNumeric(5), ""},
                {RandomStringUtils.randomAlphabetic(5) + "@mail.com", "", RandomStringUtils.randomAlphabetic(10)},
                {"", RandomStringUtils.randomAlphabetic(5) + RandomStringUtils.randomNumeric(5), RandomStringUtils.randomAlphabetic(10)},
        };
    }

    @Before
    public void setUp() {
        userSteps = new UserSteps(new UserSend());
    }

    @Test
    @Description("Создание пользователя без заполнения обязательного поля")
    public void createUserWithoutRequiredFieldReturnStatusCode403() {
        userSteps
                .createUser(email, password, name)
                .statusCode(SC_FORBIDDEN)
                .body("success", is(false))
                .body("message", is("Email, password and name are required fields"));
    }

    @After
    public void deleteUser() {
        userSteps
                .deleteUser();
    }
}
