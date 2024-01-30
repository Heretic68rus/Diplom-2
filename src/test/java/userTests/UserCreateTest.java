package userTests;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import send.UserSend;
import step.UserSteps;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.hamcrest.Matchers.is;

public class UserCreateTest {
    private UserSteps userSteps;

    @Before
    public void setUp() {
        userSteps = new UserSteps(new UserSend());
    }

    @Test
    public void createUserSuccessfully() {
        String email = RandomStringUtils.randomAlphabetic(5) + "@mail.com";
        String password = RandomStringUtils.randomAlphabetic(5) + RandomStringUtils.randomNumeric(5);
        String name = RandomStringUtils.randomAlphabetic(10);

        userSteps
                .createUser(email, password, name)
                .body("success", is(true));
    }

    @Test
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
