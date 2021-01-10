package cz.muni.fi.pv217.rouskovo;

import io.quarkus.test.junit.QuarkusTest;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.Test;

import javax.json.Json;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class AccountServiceTest {

    @Test
    public void testSetInfo() {

        String address = "address";
        String phone = "phone";
        String email = "email";
        String json = Json.createObjectBuilder()
                .add(address, address)
                .add(phone, phone)
                .add(email, email)
                .build()
                .toString();

        given()
                .contentType(String.valueOf(ContentType.APPLICATION_JSON))
                .body(json)
                .when()
                .post("/account/info")
                .then()
                .statusCode(200);

        assertThat(Customer.count(address, address) == 1);
        assertThat(Customer.count(phone, phone) == 1);
    }
    /*
    @Test
    public void testGetInfo() {

        Customer =

        String address = "address";
        String phone = "phone";
        String email = "email";
        String json = Json.createObjectBuilder()
                .add(address, address)
                .add(phone, phone)
                .add(email, email)
                .build()
                .toString();

        given()
                .contentType(String.valueOf(ContentType.APPLICATION_JSON))
                .body(json)
                .when()
                .post("/account/info")
                .then()
                .statusCode(200);

        assertThat(Customer.count(address, address) == 1);
        assertThat(Customer.count(phone, phone) == 1);
    }
    */
}