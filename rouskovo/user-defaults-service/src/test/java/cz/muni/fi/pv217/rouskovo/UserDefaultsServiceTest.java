package cz.muni.fi.pv217.rouskovo;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.Method;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.json.Json;
import javax.ws.rs.core.MediaType;

import static org.assertj.core.api.Assertions.assertThat;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThatNoException;
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
        assertThat(UserEntity.count("role", Role.CUSTOMER) == 1);

    }

    @Test
    @TestSecurity(user = "testadmin", roles = { "ADMIN" })
    public void testCreateAdminSuccess() {
        String username = "admin";
        String json = Json.createObjectBuilder()
                .add("username", username)
                .add("password", "admin")
                .build()
                .toString();

        given()
                .contentType(String.valueOf(ContentType.APPLICATION_JSON))
                .body(json)
                .when()
                .post("/userdefaults/admin")
                .then()
                .statusCode(200)
                .body(is("New admin " + username +
                        " added successfully"));

        assertThat(UserEntity.count("username", username) == 1);
        assertThat(UserEntity.count("role", Role.ADMIN) == 1);
    }

    @Test
    @TestSecurity(user = "testuser", roles = { "customer" })
    public void testCreateAdminFailure() {
        String username = "admin";
        String json = Json.createObjectBuilder()
                .add("admin", username)
                .add("password", "admin")
                .build()
                .toString();

        given()
                .contentType(String.valueOf(ContentType.APPLICATION_JSON))
                .body(json)
                .when()
                .post("/userdefaults/admin")
                .then()
                .statusCode(403); // 403 - Forbidden
    }

    @Test
    public void getOwnTokenTest() {
        Assertions.assertDoesNotThrow(() -> UserDefaultsService.getOwnToken());
    }

}