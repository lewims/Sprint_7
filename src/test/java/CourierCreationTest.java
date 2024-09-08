import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.CourierDetails;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import static io.restassured.RestAssured.given;


import io.restassured.response.Response;


public class CourierCreationTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";

    }

    @Test
    @DisplayName("Создание учетной записи курьера")
    @Description("Проверка состояние кода и тела /api/v1/courier")
    public void courierCreationIsPossibleTest() {
        String login = RandomStringUtils.randomAlphanumeric(2,15);
        String password = RandomStringUtils.randomAlphanumeric(7,15);
        String firstName = RandomStringUtils.randomAlphabetic(2,18);
    CourierDetails courierDetails = new CourierDetails(login, password, firstName);
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(courierDetails)
                        .when()
                        .post("/api/v1/courier");
        response.then().assertThat().body("ok", Matchers.is(true))
                .and()
                .statusCode(201);
        System.out.println("Новый курьер создан");
    }



    @Test
    @DisplayName("Создание курьера без логина")
    @Description("Проверка состояние кода и сообщение при создании курьера без логина и пароля")
    public void courierCreationWithoutFirstNameAndPasswordTest() {
        String login = "";
        String password = "";
        String firstName = RandomStringUtils.randomAlphabetic(2,18);
        CourierDetails courierDetails = new CourierDetails(login, password, firstName);
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(courierDetails)
                        .when()
                        .post("/api/v1/courier");
        response.then().assertThat().body("message", Matchers.is("Недостаточно данных для создания учетной записи"))
                .statusCode(400);
        System.out.println("Без логина и пароля новая учётная запись не создаётся");
    }

    @Test
    @DisplayName("Создание курьера без логина")
    @Description("Проверка состояние кода и сообщение при создании курьера без логина")
    public void courierCreationWithoutLoginTest() {

        String login = "";
        String password = RandomStringUtils.randomAlphanumeric(7,15);
        String firstName = RandomStringUtils.randomAlphabetic(2,18);
        CourierDetails courierDetails = new CourierDetails(login, password, firstName);
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(courierDetails)
                        .when()
                        .post("/api/v1/courier");
        response.then().assertThat().body("message", Matchers.is("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
        System.out.println("Без логина новая учётная запись не создаётся");
    }

    @Test
    @DisplayName("Создание курьера без логина")
    @Description("Проверка состояние кода и сообщение при создании курьера без пароля")
    public void courierCreationWithoutPasswordTest() {

        String login = RandomStringUtils.randomAlphanumeric(2,15);
        String password = "";
        String firstName = RandomStringUtils.randomAlphabetic(2,18);
        CourierDetails courierDetails = new CourierDetails(login, password, firstName);
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(courierDetails)
                        .when()
                        .post("/api/v1/courier");
        response.then().assertThat().body("message", Matchers.is("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
        System.out.println("Без пароля новая учётная запись не создаётся");
    }

    @Test
    @DisplayName("Создание курьеров с одинаковыми логинами")
    @Description("Проверка состояние кода и сообщение при создании курьера с уже существующим логином")
    public void courierCreationWithNotAvailableLoginTest() {

        String login = RandomStringUtils.randomAlphanumeric(2,15);
        String firstCourierPassword = RandomStringUtils.randomAlphanumeric(7,15);
        String secondCourierPassword = RandomStringUtils.randomAlphanumeric(7,15);
        String firstCourierFirstName = RandomStringUtils.randomAlphabetic(2,18);
        String secondCourierFirstName = RandomStringUtils.randomAlphabetic(2,18);

        CourierDetails firstCourierDetails = new CourierDetails(login, firstCourierPassword, firstCourierFirstName);
        CourierDetails secondCourierDetails = new CourierDetails(login, secondCourierPassword, secondCourierFirstName);
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(firstCourierDetails)
                        .when()
                        .post("/api/v1/courier");
        response.then().assertThat().body("ok", Matchers.is(true))
                .and()
                .statusCode(201);
 Response response1 =
         given()
                 .header("Content-type", "application/json")
                 .and()
                 .body(secondCourierDetails)
                 .when()
                 .post("/api/v1/courier");



        response1.then().assertThat().body("message", Matchers.is("Этот логин уже используется. Попробуйте другой."))
                .and()
                .statusCode(409);

        System.out.println("Нельзя создать курьера с уже существующим Логином");
    }


}
