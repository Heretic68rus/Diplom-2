package userTests;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import send.UserSend;
import step.UserSteps;

import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.Matchers.is;


public class UserUpdateTest {
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
    public void updateUsersNameWithoutAuthorizationReturnStatusCode401() {
        String newEmail = RandomStringUtils.randomAlphabetic(5) + "@mail.com";

        userSteps
                .updateUser(newEmail, name, "")
                .statusCode(SC_UNAUTHORIZED)
                .body("success", is(false))
                .body("message", is("You should be authorised"));
    }

    @Test
    public void updateUsersEmailWithoutAuthorizationReturnStatusCode401() {
        String newName = RandomStringUtils.randomAlphabetic(10);

        userSteps
                .updateUser(email, newName, "")
                .statusCode(SC_UNAUTHORIZED)
                .body("success", is(false))
                .body("message", is("You should be authorised"));
    }

    @Test
    public void updateUsersNameWithAuthorizationReturnSuccessTrue() {
        String newName = RandomStringUtils.randomAlphabetic(10);

        String token = userSteps
                .loginUser(email, password)
                .extract().body().jsonPath().get("accessToken");
        userSteps
                .updateUser(email, newName, token.replace("Bearer ", ""))
                .body("success", is(true));
    }

    @Test
    public void updateUsersEmailWithoutAuthorizationReturnSuccessTrue() {
        String newEmail = RandomStringUtils.randomAlphabetic(5) + "@mail.com";

        String token = userSteps
                .loginUser(email, password)
                .extract().body().jsonPath().get("accessToken");
        userSteps
                .updateUser(email, newEmail, token.substring(7))
                .body("success", is(true));
    }

    @After
    public void deleteUser() {
        userSteps
                .deleteUser();
    }

}
