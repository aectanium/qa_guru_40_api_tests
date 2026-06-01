package tests;

import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class WdHubStatusTests extends TestBase {


    @Test
    public void succesfulAuthorizedStatusTest() {
        given()
                .log().all()
                .auth().basic("user1", "1234")
                .when()
                .get("/status")
                .then()
                .log().all()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/wd_hub_status_responce_schema.json"));
    }

    @Test
    public void checkStatusResponseBodyMessageTest() {
        given()
                .log().all()
                .auth().basic("user1", "1234")
                .when()
                .get("/status")
                .then()
                .log().all()
                .statusCode(200)
                .body("value.message", is("Selenoid 1.11.3 built at 2024-05-25_12:34:40PM"))
                .body("value.ready", is(true));
    }

    @Test
    public void checkStatusResponseTimeTest() {
        given()
                .log().all()
                .auth().basic("user1", "1234")
                .when()
                .get("/status")
                .then()
                .log().all()
                .time(is(lessThan(5000L)))
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/wd_hub_status_responce_schema.json"));
    }

    @Test
    public void checkStatusResponseContentTypeTest() {
        given()
                .log().all()
                .auth().basic("user1", "1234")
                .when()
                .get("/status")
                .then()
                .log().all()
                .statusCode(200)
                .contentType("application/json")
                .body(matchesJsonSchemaInClasspath("schemas/wd_hub_status_responce_schema.json"));
    }


    @Test
    public void unauthorizedStatusTest() {
        given()
                .log().all()
                .when()
                .get("/status")
                .then()
                .log().all()
                .statusCode(401);
    }

    @Test
    public void invalidCredentialsStatusTest() {
        given()
                .log().all()
                .auth().basic("user1", "11111")
                .when()
                .get("/status")
                .then()
                .log().all()
                .statusCode(401);
    }

    @Test
    public void emptyCredentialsStatusTest() {
        given()
                .log().all()
                .auth().basic("", "")
                .when()
                .get("/status")
                .then()
                .log().all()
                .statusCode(401);

    }
}
