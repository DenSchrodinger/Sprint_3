import org.junit.Before;
import org.junit.Test;
import java.io.File;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class CourierLoginNegativeTest {
    private static final String COURIER_LOGIN_API = "api/v1/courier/login";

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    // Логин курьера - система вернёт ошибку, если неправильно указать логин или пароль
    // Если авторизоваться под несуществующим пользователем, запрос возвращает ошибку
    public void courierCanNotLoginErrorTest() {
        File courierLoginWrongJson = new File("src/test/resources/courierLoginWrong.json");
        Response response = given()
                .header("Content-type", "application/json")
                .body(courierLoginWrongJson)
                .when()
                .post(COURIER_LOGIN_API);
                response
                .then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }

    @Test
    // Логин курьера - для авторизации нужно передать все обязательные поля
    // Если какого-то поля нет(логина), запрос возвращает ошибку
    public void courierLoginWithoutLoginTest() {
        File courierLoginWithoutLoginJson = new File("src/test/resources/courierLoginWithoutLogin.json");
        Response response = given()
                .header("Content-type", "application/json")
                .body(courierLoginWithoutLoginJson)
                .when()
                .post(COURIER_LOGIN_API);
                response
                .then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }

    @Test(timeout = 10000)
    // Логин курьера - если какого-то поля нет(пароля), запрос возвращает ошибку.
    // Если отправить запрос на авторизацию без поля пароля, то будет бесконечная отправка запроса.
    // Соответственно ожидаемого результата не будет - это баг, поэтому ставим тесту тайм-аут 10 секунд.
    public void courierLoginWithoutPasswordTest() {
        File courierLoginWithoutPasswordJson = new File("src/test/resources/courierLoginWithoutPassword.json");
        Response response = given()
                .header("Content-type", "application/json")
                .body(courierLoginWithoutPasswordJson)
                .when()
                .post(COURIER_LOGIN_API);
                response
                .then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }

}
