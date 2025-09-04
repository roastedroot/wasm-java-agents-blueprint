package org.acme.getting.started;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class GreetingResourceTest {

    @Test
    public void testPython() {
        given().when()
                .body("my additional prompt")
                .put("/hello/py/en/Alice")
                .then()
                .statusCode(200)
                .body(containsString("Alice"));
    }

    @Test
    public void testJavaScript() {
        given().when()
                .body("my additional prompt")
                .put("/hello/js/en/Bob")
                .then()
                .statusCode(200)
                .body(containsString("Bob"));
    }

    @Test
    public void testRust() {
        given().when()
                .body("my additional prompt")
                .put("/hello/rust/en/Charlie")
                .then()
                .statusCode(200)
                .body(containsString("Charlie"));
    }

    @Test
    public void testGo() {
        given().when()
                .body("my additional prompt")
                .put("/hello/go/en/Dan")
                .then()
                .statusCode(200)
                .body(containsString("Dan"));
    }
}
