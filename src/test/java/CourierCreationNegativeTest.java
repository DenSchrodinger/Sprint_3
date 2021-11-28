import org.junit.Before;
import org.junit.Test;
import java.io.File;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class CourierCreationNegativeTest {

    private static final String COURIER_API = "api/v1/courier/";

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    // Создание курьера - чтобы создать курьера, нужно передать в ручку все обязательные поля
    //если одного из полей нет(password), запрос возвращает ошибку
    public void createNewCourierWithoutPasswordTest() {
        File courierCreationWithoutPasswordJson = new File("src/test/resources/courierCreationWithoutPassword.json");
        Response response = given()
                .header("Content-type", "application/json")
                .body(courierCreationWithoutPasswordJson)
                .when()
                .post(COURIER_API);
                response.
                then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }

    @Test
    // Создание курьера - чтобы создать курьера, нужно передать в ручку все обязательные поля
    //если одного из полей нет(login), запрос возвращает ошибку
    public void createNewCourierWithoutLoginTest() {
        File courierCreationWithoutLoginJson = new File("src/test/resources/courierCreationWithoutLogin.json");
        Response response = given()
                .header("Content-type", "application/json")
                .body(courierCreationWithoutLoginJson)
                .when()
                .post(COURIER_API);
                response
                .then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }
}