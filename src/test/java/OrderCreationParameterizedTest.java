import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.io.File;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@RunWith(Parameterized.class)
public class OrderCreationParameterizedTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    private final String testDataFilePath;
    public OrderCreationParameterizedTest(String testDataFilePath) {
        this.testDataFilePath = testDataFilePath;
    }

    @Parameterized.Parameters
    public static Object[] testDataSet() {
        return new Object[][] {
                {"src/test/resources/orderWithGrayScooter.json"},
                {"src/test/resources/orderWithBlackScooter.json"},
                {"src/test/resources/orderWithGrayAndBlackScooter.json"},
                {"src/test/resources/orderWithNoColorScooter.json"}
        };
    }

    @Test
    public void createOrderParameterizedTest() {
        File json = new File(testDataFilePath);
                given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post("/api/v1/orders")
                .then()
                .assertThat().body("track", is(not(0)))
                .statusCode(201);
    }
}