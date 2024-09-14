package org.example;

import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class CourierLogin {
    public Response courierLoginMethod(CourierDetails courierDetails) {
        Response response1 =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(courierDetails)
                        .when()
                        .post("/api/v1/courier/login");
        return response1;
    }
}

