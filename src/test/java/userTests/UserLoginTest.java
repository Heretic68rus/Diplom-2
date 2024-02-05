package userTests;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import send.UserSend;
import step.UserSteps;

import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.Matchers.is;

public class UserLoginTest {
    private final String email = RandomStringUtils.randomAlphabetic(5) + "@mail.com";
    private final String password = RandomStringUtils.randomAlphabetic(5) + RandomStringUtils.randomNumeric(5);
    private final String name = RandomStringUtils.randomAlphabetic(10);
    private UserSteps userSteps;

    @Before
    public void setUp() {
        userSteps = new UserSteps(new UserSend());
        userSteps
                .createUser(email, password, name);
    }

    @Test
    public void loginUserSuccessfully() {
        userSteps
                .loginUser(email, password)
                .body("success", is(true));
    }

    @Test
    public void userLoginWithIncorrectPasswordReturnStatusCode401() {
        userSteps
                .loginUser(email, "some_password")
                .statusCode(SC_UNAUTHORIZED)
                .body("success", is(false))
                .body("message", is("email or password are incorrect"));
    }

    @Test
    public void userLoginWithIncorrectLoginReturnStatusCode401() {
        userSteps
                .loginUser("some_email", password)
                .statusCode(SC_UNAUTHORIZED)
                .body("success", is(false))
                .body("message", is("email or password are incorrect"));
    }

    @After
    public void deleteUser() {
        userSteps
                .deleteUser();
    }
}
