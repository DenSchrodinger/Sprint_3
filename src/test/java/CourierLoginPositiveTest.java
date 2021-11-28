import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.File;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static io.restassured.RestAssured.given;

public class CourierLoginPositiveTest {
    private static final String COURIER_LOGIN_API = "api/v1/courier/login";
    private static final String COURIER_API = "api/v1/courier/";

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
        File courierCreationJson = new File("src/test/resources/courierCreation.json");
                given()
                .header("Content-type", "application/json")
                .body(courierCreationJson)
                .when()
                .post("/api/v1/courier");
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
    // Логин курьера - курьер может авторизоваться
    public void courierCanLoginTest() {
        File courierLoginJson = new File("src/test/resources/courierLogin.json");
        Response response = given()
                .header("Content-type", "application/json")
                .body(courierLoginJson)
                .when()
                .post(COURIER_LOGIN_API);
                response
                .then()
                .assertThat()
                .body("id", is(not(0)))
                .and()
                .statusCode(200);
    }
}