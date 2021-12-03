package ru.praktikumservices.qascooter;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import static org.junit.Assert.*;

public class CourierCreationNegativeTest {

    CourierMethods courier = new CourierMethods();

    @Before
    public void setUp() {
        courier.generateCourierCredentials();
    }

    @Test
    @DisplayName("Тест на регистрацию курьера без пароля")
    public void createNewCourierWithoutPasswordTest() {
        courier.registerCourierWithoutPassword();
                assertEquals(400, courier
                .getCourierResponse()
                .getStatusCode());
    }

    @Test
    @DisplayName("Тест на регистрацию курьера без логина")
    public void createNewCourierWithoutLoginTest() {
        courier.registerCourierWithoutLogin();
                assertEquals(400, courier
                .getCourierResponse()
                .getStatusCode());
    }
}
