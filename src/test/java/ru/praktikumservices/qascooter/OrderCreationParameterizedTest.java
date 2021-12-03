package ru.praktikumservices.qascooter;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import io.qameta.allure.junit4.DisplayName;
import org.junit.runners.Parameterized;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderCreationParameterizedTest extends RASpecs {

    private final String color;

    public OrderCreationParameterizedTest(String color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] getColorData() {
        return new Object[][]{
                {"," + "\n" + "\"color\": [\"BLACK\"]"},
                {"," + "\n" + "\"color\": [\"GREY\"]"},
                {"," + "\n" + "\"color\": [\"BLACK\", \"GREY\"]"},
                {""},
        };
    }

    @Test
    @DisplayName("Параметризованный тест на создание заказа с разными цветами")
    public void createOrderParameterizedTest() {
        OrderMethods createOrder = new OrderMethods();
        Response response = createOrder.orderWithSomeColor(color);
                response
                .then()
                .assertThat()
                .body("track", notNullValue())
                .and()
                .statusCode(201);
    }
}