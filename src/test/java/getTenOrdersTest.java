import io.restassured.response.Response;
import org.junit.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
public class getTenOrdersTest {

    @Test
    // Список заказов - в тело ответа возвращается список заказов (10 доступных заказов)
    public void getTenOrdersTest() {
        Response response = given()
                .header("Content-type", "application/json")
                .when()
                .get("/api/v1/orders?limit=10&page=0");
                response
                .then()
                .assertThat()
                .body("orders", is(not(0)))
                .and()
                .statusCode(200);
    }
}
