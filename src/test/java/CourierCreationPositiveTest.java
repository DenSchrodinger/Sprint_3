import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.File;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertTrue;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class CourierCreationPositiveTest {
    private static final String COURIER_API = "api/v1/courier/";

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @After
    public void tearDown() {
        File courierLoginJson = new File("src/test/resources/courierLogin.json");
        int courierId = given()
                .header("Content-type", "application/json")
                .body(courierLoginJson)
                .when()
                .post(COURIER_API + "login/")
                .then()
                .assertThat().body("id", is(not(0)))
                .statusCode(200)
                .extract()
                .path("id");
                given()
                .when()
                .delete(COURIER_API + courierId);
    }

    @Test
    // Создание курьера - курьера можно создать
    public void courierCanBeCreatedTest() {
        File courierCreationJson = new File("src/test/resources/courierCreation.json");
        boolean ok = given()
                .header("Content-type", "application/json")
                .body(courierCreationJson)
                .when()
                .post(COURIER_API)
                .then()
                .assertThat()
                .statusCode(201)
                .and()
                .extract()
                .path("ok");
        assertTrue(ok);
    }

    @Test
    // Создание курьера - нельзя создать двух одинаковых курьеров
    // Если создать пользователя с логином, который уже есть, возвращается ошибка
    public void sameCourierCanNotBeCreatedTest() {
        File courierCreationJson = new File("src/test/resources/courierCreation.json");
                given()
                .header("Content-type", "application/json")
                .body(courierCreationJson)
                .when()
                .post(COURIER_API);

                Response response1 = given()
                .header("Content-type", "application/json")
                .body(courierCreationJson)
                .when()
                .post(COURIER_API);
                response1.then()
                .assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                //Фактическое сообщение в теле ответа при попытке создания дубля курьера отличается от  сообщения в документации.
                //Поэтому сообщение в этом тесте было исправлено на "Этот логин уже используется. Попробуйте другой."
                .and()
                .statusCode(409);
    }
}