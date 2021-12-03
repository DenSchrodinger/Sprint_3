package ru.praktikumservices.qascooter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import static org.junit.Assert.*;

public class CourierCreationTest {

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
    @DisplayName("Тест на регистрацию курьера")
    public void courierCanBeCreatedTest() {
        courier.registerCourier();
                assertTrue(courier
                .getCourierResponse()
                .jsonPath()
                .getBoolean("ok"));
    }

    @Test
    @DisplayName("Тест на попытку регистрации курьера, зарегистрированного ранее")
    public void sameCourierCanNotBeCreatedTest() {
        courier.registerCourier();
        courier.registerCourier();
                assertEquals(409,courier
                .getCourierResponse()
                .getStatusCode());
    }
}
