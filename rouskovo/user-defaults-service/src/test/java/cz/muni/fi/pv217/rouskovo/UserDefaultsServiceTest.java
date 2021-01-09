package cz.muni.fi.pv217.rouskovo;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class UserDefaultsServiceTest {

    @Test
    public void testHelloEndpoint() {
        given()
          .when().get("/userdefaults")
          .then()
             .statusCode(200)
             .body(is("Hello RESTEasy"));
    }

}