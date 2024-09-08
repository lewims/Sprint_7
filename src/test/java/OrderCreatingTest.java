import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.Orders;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.given;

@RunWith(Parameterized.class)
public class OrderCreatingTest {

    private final Orders orders;

    public OrderCreatingTest (Orders orders) {
        this.orders = orders;
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{{new Orders("Кирилл", "Рататуев", "пр-т Химиков 10", "Румянцево", "89347892900", 3, "2024-10-01", "Очень жду!", new String[]{"BLACK"})}, {new Orders("Изабелла", "Вайнштейн", "ул.Лапнко 69", "Юго-Западная", "89683330808", 4, "2024-09-10", "Нужен самокат", new String[]{"GREY"})}, {new Orders("Василий", "Кукошкин", "проезд Тихомирова 9", "Арбатская", "89053236575", 6, "2025-09-23", "", new String[]{"BLACK", "GREY"})}, {new Orders("Фрося", "Ахмадулина", "ул.Кирилла Мефодия 43", "Жулебино", "89885457532", 3, "2024-09-12", "Хей, привези мне самокат", new String[]{})},};
    }

    @Test
    @DisplayName("Создание заказа")
    @Description("Проверка создания заказа с различными данными")
    public void checkCreateOrder() {

        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(orders)
                        .when()
                        .post("/api/v1/orders");
        response.then().assertThat().body("track", Matchers.notNullValue())
                .and()
                .statusCode(201);
        System.out.println("Заказ создан");

    }

}
