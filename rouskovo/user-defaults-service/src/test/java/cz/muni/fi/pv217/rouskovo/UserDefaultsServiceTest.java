package cz.muni.fi.pv217.rouskovo;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.Method;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.json.Json;
import javax.ws.rs.core.MediaType;

import static org.assertj.core.api.Assertions.assertThat;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class UserDefaultsServiceTest {

    @Test
    public void testRegisterInfoEndpoint() {
        given()
                .when().get("/userdefaults/register")
                .then()
                .statusCode(200)
                .body(is("Choose a username and a password"));
    }

    @Test
    public void testRegisterCustomer() {

        String username = "testuser";
        String json = Json.createObjectBuilder()
                .add("username", username)
                .add("password", "testpassword")
                .build()
                .toString();

        given()
                .contentType(String.valueOf(ContentType.APPLICATION_JSON))
                .body(json)
                .when()
                .post("/userdefaults/register")
                .then()
                .statusCode(200)
                .body(is("Welcome " + username +
                        "! Thank your for using our services"));

        assertThat(UserEntity.count("username", username) == 1);

    }

}