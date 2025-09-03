package org.acme.getting.started;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class GreetingResourceTest {

    @Test
    public void testGreetingEndpoint() {
        given()
                .when()
                .body("my additional prompt")
                .put("/hello/py/en/Bob")
                .then()
                .statusCode(200)
                .body(containsString("Bob"));
    }

}
