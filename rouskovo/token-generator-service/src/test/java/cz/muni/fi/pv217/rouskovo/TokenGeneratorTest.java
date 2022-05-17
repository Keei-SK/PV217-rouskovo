package cz.muni.fi.pv217.rouskovo;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.Test;

import javax.json.Json;

import static io.restassured.RestAssured.given;

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
    }

    @Test
    @TestSecurity(user = "admin", roles = { "ADMIN" })
    public void testCuSTomerTokenGenerator() {
        String json = Json.createObjectBuilder()
                .add("upn", "user")
                .build()
                .toString();

        var response = given()
                .contentType(String.valueOf(ContentType.APPLICATION_JSON))
                .filter(ResponseLoggingFilter.logResponseIfStatusCodeIs(200))
                .body(json)
                .when().post("/token/customer")
                .then()
                .statusCode(200);
    }

}