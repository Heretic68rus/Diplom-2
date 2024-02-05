package orderTests;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import send.OrderSend;
import send.UserSend;
import step.OrderSteps;
import step.UserSteps;

import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class GetOrdersTest {
    private final String email = RandomStringUtils.randomAlphabetic(5) + "@mail.com";
    private final String password = RandomStringUtils.randomAlphabetic(5) + RandomStringUtils.randomNumeric(5);
    private final String name = RandomStringUtils.randomAlphabetic(10);
    private OrderSteps orderSteps;
    private UserSteps userSteps;

    @Before
    public void setUp() {
        orderSteps = new OrderSteps(new OrderSend());
        userSteps = new UserSteps(new UserSend());
        userSteps
                .createUser(email, password, name);
    }

    @Test
    public void getOrderForUserWithAuthorizationReturnSuccessTrue() {
        String token = userSteps
                .loginUser(email, password)
                .extract().body().jsonPath().get("accessToken");
        orderSteps
                .getOrderForOneUser(token.substring(7))
                .body("success", is(true))
                .body("orders", is(notNullValue()));
    }

    @Test
    public void getOrderForUserWithoutAuthorizationReturnStatusCode401() {
        orderSteps
                .getOrderForOneUser("")
                .statusCode(SC_UNAUTHORIZED)
                .body("success", is(false))
                .body("message", is("You should be authorised"));
    }

    @After
    public void deleteUser() {
        userSteps
                .deleteUser();
    }
}
