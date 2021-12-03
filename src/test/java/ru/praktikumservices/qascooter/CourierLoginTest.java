package ru.praktikumservices.qascooter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import static org.junit.Assert.*;

public class CourierLoginTest {

    CourierMethods courier = new CourierMethods();

    @Before
    public void setUp() {
        courier.generateCourierCredentials();
    }

    @After
    public void deleteCourierToClearTestData() {
        courier.deleteCourier();
    }

    @Test
    @DisplayName("Тест на успешный логин курьера")
    public void courierLoginTest() {
        courier.registerCourier();
        courier.courierLogin();
        courier.saveCourierId();
                assertNotEquals(0, courier
                .getCourierId());
    }
}