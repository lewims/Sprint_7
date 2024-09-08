import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class OrderListTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Список заказов")
    @Description("Проверка получения списка заказов")
    public void getListOrdersTest() {
        Response response =
        given()
                .header("Content-type", "application/json")
                .get("/api/v1/orders");
        response.then().assertThat()
                .statusCode(200);
        System.out.println("Список заказов получен");
    }
    }

