package ru.praktikumservices.qascooter;
import io.restassured.response.Response;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class OrdersTest extends RASpecs {

    @Test
    @DisplayName("Запрос возвращает список 10 доступных заказов")
    public void getTenOrdersTest() {
            OrderMethods orderList = new OrderMethods();
            Response response = orderList.getTenOrders();
                response
                .then()
                .assertThat()
                .body("orders", is(not(0)))
                .and()
                .statusCode(200);
    }
}


