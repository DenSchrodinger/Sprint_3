package ru.praktikumservices.qascooter;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class OrderMethods extends RASpecs {

    @Step("Получение десяти доступных заказов.")
    public Response getTenOrders() {
        return given()
                .spec(getBaseSpec())
                .get("/api/v1/orders?limit=10&page=0");
    }

    @Step("Создание заказа.")
    public Response orderWithSomeColor (String color) {
        String orderBody =
                "{\"firstName\":\"SomeName\","
                + "\"lastName\":\"SomeLastName\","
                + "\"address\":\"Moscow, 182\","
                + "\"metroStation\":\"3\","
                + "\"phone\":\"+7 999 666 12 34\","
                + "\"rentTime\":\"5\","
                + "\"deliveryDate\":\"2021-11-11\","
                + "\"comment\":\"SomeComment\"";

                return given()
                .spec(getBaseSpec())
                .and()
                .body(orderBody + color + "}")
                .when()
                .post("/api/v1/orders");
    }
}
