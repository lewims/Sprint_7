package org.example;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class CourierRemoval {
    public Response courierRemovalMethod(CourierDetails courierDetails) {
        CourierId courierId =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(courierDetails)
                        .when()
                        .post("/api/v1/courier/login")
                        .as(CourierId.class);
        String id = courierId.getId().toString();
        Response removalResponse =
              given()
                      .header("Content-type", "application/json")
                      .delete("/api/v1/courier/" + id);
        return removalResponse;
    }
}
