package ru.praktikumservices.qascooter;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import static io.restassured.RestAssured.given;

public class CourierMethods extends RASpecs {

    private String courierLogin;
    private String courierPassword;
    private String courierFirstName;
    private int courierId = 0;
    private Response courierResponse;

    public Response getCourierResponse() {
        return courierResponse;
    }
    public int getCourierId() {
        return courierId;
    }

    @Step("Генерация полей логин, пароль и имя для курьера")
    public void generateCourierCredentials() {
        courierLogin = RandomStringUtils.randomAlphabetic(10);
        courierPassword = RandomStringUtils.randomAlphabetic(10);
        courierFirstName = RandomStringUtils.randomAlphabetic(10);
    }

    @Step("Регистрация курьера")
    public void registerCourier() {
        String registerRequestBody = "{\"login\":\"" + courierLogin + "\","
                + "\"password\":\"" + courierPassword + "\","
                + "\"firstName\":\"" + courierFirstName + "\"}";
                courierResponse = given()
                .spec(getBaseSpec())
                .body(registerRequestBody)
                .when()
                .post("/api/v1/courier");
    }

    @Step("Регистрация курьера без пароля")
    public void registerCourierWithoutPassword() {
        String registerRequestBody = "{\"login\":\"" + courierLogin + "\","
                + "\"firstName\":\"" + courierFirstName + "\"}";
                courierResponse = given()
                .spec(getBaseSpec())
                .body(registerRequestBody)
                .when()
                .post("/api/v1/courier");
    }

    @Step("Регистрация курьера без логина")
    public void registerCourierWithoutLogin() {
        String registerRequestBody = "{\"password\":\"" + courierPassword + "\","
                + "\"firstName\":\"" + courierFirstName + "\"}";
                courierResponse = given()
                .spec(getBaseSpec())
                .body(registerRequestBody)
                .when()
                .post("/api/v1/courier");
    }

    @Step("Сохранение ID курьера")
    public void saveCourierId() {
            courierId = courierResponse
                .then()
                .extract()
                .path("id");
    }

    @Step("Логин курьера")
    public void courierLogin() {
            String requestByLoginAndPass =
            "{\"login\": \"" + courierLogin + "\", \"password\": \"" + courierPassword + "\"}";
            courierResponse = given()
                    .spec(getBaseSpec())
                    .body(requestByLoginAndPass)
                    .when()
                    .post("/api/v1/courier/login");
    }

    @Step("Логин курьера без логина")
    public void courierLoginWithoutLogin() {
            String loginRequestBody =
            "{\"password\":\"" + courierPassword + "\"}";
            courierResponse = given()
                .spec(getBaseSpec())
                .body(loginRequestBody)
                .when()
                .post("/api/v1/courier/login");
    }

    @Step("Логин курьера без пароля")
    public void courierLoginWithoutPassword(){
        String loginRequestBody =
                "{\"login\":\"" + courierLogin + "\"}";
        courierResponse = given()
                .spec(getBaseSpec())
                .body(loginRequestBody)
                .when()
                .post("/api/v1/courier/login");
    }

    @Step("Удаление курьера")
    public void deleteCourier() {
            courierLogin();
            saveCourierId();
            if (courierId != 0) {
                    courierResponse = given()
                        .spec(getBaseSpec())
                        .when()
                        .delete("/api/v1/courier/" + courierId);
            }
    }
}




