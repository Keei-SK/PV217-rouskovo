package cz.muni.fi.pv217.rouskovo;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class OrderServiceTest {

    @Test
    public void testHelloEndpoint() {
        given()
          .when().get("/order")
          .then()
             .statusCode(200)
             .body(is("To create an order use HTTP method POST on 'current_address/new' like: \\n\\t 'http POST 0.0.0.0:8083/order/new username=pabloescobar productID=123456789 quantity=20'\\n\\n\" +\n" +
                     "                \"To cancel order use HTTP method POST on 'current_address/order_id/cancel' like: \\n\\t 'http POST :8083/order/1/cancel'\\n\\n\" +\n" +
                     "                \"To show metrics use HTTP method GET on 'current_address/metrics' like: \\n\\t 'http :8083/metrics' \\n\\n\" +\n" +
                     "                \"For less detailed metrics use: \\n\\t 'http :8083/metrics/application Accept:application/json'\\n\\n"));
    }

}