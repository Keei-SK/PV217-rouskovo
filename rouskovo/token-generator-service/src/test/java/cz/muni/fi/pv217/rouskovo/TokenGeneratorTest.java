package cz.muni.fi.pv217.rouskovo;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.Test;

import javax.json.Json;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class TokenGeneratorTest {

    @Test
    @TestSecurity(user = "admin", roles = { "ADMIN" })
    public void testTokenGenerator() {
        String json = Json.createObjectBuilder()
                .add("upn", "admin")
                .build()
                .toString();

        var response = given()
                .contentType(String.valueOf(ContentType.APPLICATION_JSON))
                .filter(ResponseLoggingFilter.logResponseIfStatusCodeIs(200))
                .body(json)
                .when().post("/token/admin")
                .then()
                .statusCode(200);
        // System.out.println(response.body.toString());
    }

    /*
    @Test
    @TestSecurity(user = "admin", roles = { "ADMIN" })
    public void testTokenGenerator() {
        var response = given()
                .when().get("/token")
                .then()
                .statusCode(200)
                .extract();
        System.out.println(response.toString());
    }
    */

}