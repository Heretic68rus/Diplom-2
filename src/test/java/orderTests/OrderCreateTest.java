package orderTests;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import send.OrderSend;
import send.UserSend;
import step.OrderSteps;
import step.UserSteps;

import java.util.ArrayList;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR;
import static org.hamcrest.Matchers.is;

public class OrderCreateTest {
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
    public void createOrderWithValidIngredientsReturnSuccessTrue() {
        ArrayList<String> ingredients = orderSteps
                .getIngredients()
                .extract().body().path("data[0,1]._id");
        String token = userSteps
                .loginUser(email, password)
                .extract().body().path("accessToken");
        orderSteps
                .createOrder(ingredients, token.substring(7))
                .body("success", is(true));
    }

    @Test
    public void createOrderWithoutIngredientsReturnStatusCode400() {
        String token = userSteps
                .loginUser(email, password)
                .extract().body().path("accessToken");
        orderSteps
                .createOrder(null, token.replace("Bearer ", ""))
                .statusCode(SC_BAD_REQUEST)
                .body("success", is(false))
                .body("message", is("Ingredient ids must be provided"));
    }

    @Test
    public void createOrderWithInvalidIngredientsReturnStatusCode500() {
        ArrayList<String> ingredients = new ArrayList<>();
        ingredients.add("RandomStringUtils.randomAlphabetic(5)");

        String token = userSteps
                .loginUser(email, password)
                .extract().body().path("accessToken");
        orderSteps
                .createOrder(ingredients, token.substring(7))
                .statusCode(SC_INTERNAL_SERVER_ERROR);
    }

    @After
    public void deleteUser() {
        userSteps
                .deleteUser();
    }
}
