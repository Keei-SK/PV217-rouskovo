package cz.muni.fi.pv217.rouskovo;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.Test;

import javax.json.Json;
import javax.transaction.Transactional;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.put;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class AccountServiceTest {

    @Test
    @TestSecurity(user = "user", roles = { "CUSTOMER" })
    public void testSetInfo() {

        String username = "user";
        String address = "address";
        String name = "User User";
        String phone = "phone";
        String email = "email";
        String json = Json.createObjectBuilder()
                .add(address, address)
                .add(phone, phone)
                .add(email, email)
                .add("name", name)
                .build()
                .toString();

        given()
                .headers(getHeaders())
                .contentType(String.valueOf(ContentType.APPLICATION_JSON))
                .body(json)
                .when()
                .post("/account/info")
                .then()
                .statusCode(200);

        assertThat(Customer.count(address, address) == 1);
        assertThat(Customer.count(phone, phone) == 1);
        assertThat(Customer.count(username, username) == 1);
    }

    @Test
    @TestSecurity(user = "user", roles = { "CUSTOMER" })
    @Transactional
    public void testGetInfo() {

        String username = "user";
        String address = "address";
        String name = "User User";
        String phone = "phone";
        String email = "email";
        String json = Json.createObjectBuilder()
                .add(address, address)
                .add(phone, phone)
                .add(email, email)
                .add("name", name)
                .build()
                .toString();

        given()
                .headers(getHeaders())
                .contentType(String.valueOf(ContentType.APPLICATION_JSON))
                .body(json)
                .when()
                .post("/account/info")
                .then()
                .statusCode(200);

        Customer c = new Customer(username, name, address, phone, email);

        given()
                .headers(getHeaders())
                .when()
                .get("/account/info")
                .then()
                .statusCode(200)
                .body(is("Account details\n" + c.toString()));

    }

    public Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<>(){
            {
                put("Authorization", "Bearer " + getToken());
            }
        };
        return headers;
    }

    public String getToken() {
        return "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJodHRwczovL2V4YW1wbGUuY29tL2lzc3VlciIsInVwbiI6InVzZXIiLCJncm91cHMiOlsiQ1VTVE9NRVIiXSwiaWF0IjoxNjEwMzY3ODI2LCJleHAiOjE2MTA5NzI2MjYsImp0aSI6IndNb1pGaS1CTnhYcEZyYzZRNUd6d1EifQ.HSmPcGoc4HdjBDJhouSAXLV-8sUJTxvvUCfO_cjla9V5i8Cp9XxIYLYHLci2k8d-Sw-jQED-5-HmQV19nC6XTC2stgT-cCIBkeUr_1Q1Wkke90wLEq3DQiUVpiSqlx4EfcohJWTUEY4c9YnybubpON0MNFvhDZAGU3KVeGJKC7VhOwMdDe2xb2tFQddeUsaTQR_NgM8QkmS54K45-nLPyacnl99E5B2sWi4S7UFuiiSuPvJEHLvXxcALmI4putSR1tVsbplOvGBK18tjj27fHJd2I3Z70jSKdN5AzYE-JRj3xpcWnhehwsr28_IBu3Z6237yxITQqwAlJEiORXf_mw";
    }

}