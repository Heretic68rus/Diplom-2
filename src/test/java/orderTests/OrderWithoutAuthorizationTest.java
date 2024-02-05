package orderTests;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import send.OrderSend;
import send.UserSend;
import step.OrderSteps;
import step.UserSteps;

import java.util.ArrayList;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

@RunWith(Parameterized.class)
public class OrderWithoutAuthorizationTest {
    private final String email = RandomStringUtils.randomAlphabetic(5) + "@mail.com";
    private final String password = RandomStringUtils.randomAlphabetic(5) + RandomStringUtils.randomNumeric(5);
    private final String name = RandomStringUtils.randomAlphabetic(10);
    private final String token;
    private OrderSteps orderSteps;
    private UserSteps userSteps;

    public OrderWithoutAuthorizationTest(String token) {
        this.token = token;
    }

    @Parameterized.Parameters
    public static Object[][] data() {
        return new Object[][]{
                {""},
                {RandomStringUtils.randomAlphabetic(5)},
        };
    }

    @Before
    public void setUp() {
        orderSteps = new OrderSteps(new OrderSend());
        userSteps = new UserSteps(new UserSend());
        userSteps
                .createUser(email, password, name);
    }

    @Test
    public void createOrderWithoutAuthorizationReturnNoNameOfIngredients() {
        ArrayList<String> ingredients = orderSteps
                .getIngredients()
                .extract().body().path("data._id");
        orderSteps
                .createOrder(ingredients, token)
                .body("name", is(nullValue()));
    }
    // без авторизации заказ не должен создаваться согласно требованиям, указанным в документации API:
// "Только авторизованные пользователи могут делать заказы. Структура эндпоинтов
//не меняется, но нужно предоставлять токен при запросе к серверу в поле
//Authorization"
//Однако, при использовании пустого токена приходит ответ, соответствующий созданию заказа

    @After
    public void deleteUser() {
        userSteps
                .deleteUser();
    }
}
