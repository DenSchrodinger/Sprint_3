package ru.praktikumservices.qascooter;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import static org.junit.Assert.assertEquals;

public class CourierLoginNegativeTest {

    CourierMethods courier = new CourierMethods();

    @Before
    public void setUp() {
        courier.generateCourierCredentials();
    }

    @Test
    @DisplayName("Тест на попытку логина незарегистрированного курьера")
    public void courierCanNotLoginTest() {
        courier.courierLogin();
                assertEquals(404, courier
                .getCourierResponse()
                .getStatusCode());
    }

    @Test
    @DisplayName("Тест на попытку логина курьера в системе без логина")
    public void courierLoginWithoutLoginTest() {
        courier.registerCourier();
        courier.courierLoginWithoutLogin();
                assertEquals(400, courier
                .getCourierResponse()
                .getStatusCode());
    }

    @Test(timeout = 7000)
    // Если отправить запрос на авторизацию без поля пароля, то будет бесконечная отправка запроса.
    // Соответственно ожидаемого результата не будет - это баг, поэтому ставим тесту тайм-аут 7 секунд.
    @DisplayName("Тест на попытку логина курьера в системе без пароля")
    public void courierLoginWithoutPasswordTest() {
        courier.registerCourier();
        courier.courierLoginWithoutPassword();
                assertEquals(400, courier
                .getCourierResponse()
                .getStatusCode());
    }
}
