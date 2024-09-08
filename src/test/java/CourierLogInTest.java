import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.CourierDetails;
import org.hamcrest.Matchers;
import org.junit.Test;
import static io.restassured.RestAssured.given;

public class CourierLogInTest {

    String login = RandomStringUtils.randomAlphanumeric(2,15);
    String password = RandomStringUtils.randomAlphanumeric(7,15);
    CourierDetails courierDetails = new CourierDetails(login, password);
    CourierDetails courierDetailsWithoutLogin = new CourierDetails("", password);
    CourierDetails courierDetailsWithoutPassword = new CourierDetails(login, "");
    CourierDetails courierDetailsWithIncorrectLogin = new CourierDetails("ЛаширсКвы", password);
    CourierDetails courierDetailsWithIncorrectPassword = new CourierDetails(login, "9999999");


    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
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

    }



    @Test
    @DisplayName("Логин Курьера")
    @Description("Проверка авторизации курьера с корректным логином и паролем")
    public void loginCourierTest() {



        Response response1 =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(courierDetails)
                        .when()
                        .post("/api/v1/courier/login");
        response1.then().assertThat().body("id", Matchers.notNullValue())
                .and()
                .statusCode(200);
        System.out.println("Вход успешно выполняется при вводе корректных логина и пароля");
    }

    @Test
    @DisplayName("Вход в учётную запись курьера без ввода логина ")
    @Description("Проверка авторизации курьера без ввода логина")
    public void loginCourierWithoutLoginTest() {


        Response response1 =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(courierDetailsWithoutLogin)
                        .when()
                        .post("/api/v1/courier/login");
        response1.then().assertThat().body("message", Matchers.is("Недостаточно данных для входа"))
                .and()
                .statusCode(400);

        System.out.println("Вход в учётную запись курьера без ввода логина не осуществить");
    }

    @Test
    @DisplayName("Вход в учётную запись курьера без ввода логина ")
    @Description("Проверка авторизации курьера без ввода логина")
    public void loginCourierWithoutPasswordTest() {


        Response response1 =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(courierDetailsWithoutPassword)
                        .when()
                        .post("/api/v1/courier/login");
        response1.then().assertThat().body("message", Matchers.is("Недостаточно данных для входа"))
                .and()
                .statusCode(400);

        System.out.println("Вход в учётную запись курьера без ввода пароля не осуществить");
    }

    @Test
    @DisplayName("Вход в учётную запись курьера без ввода логина ")
    @Description("Проверка авторизации курьера без ввода логина")
    public void loginCourierWithIncorrectLoginTest() {


        Response response1 =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(courierDetailsWithIncorrectLogin)
                        .when()
                        .post("/api/v1/courier/login");
        response1.then().assertThat().body("message", Matchers.is("Учетная запись не найдена"))
                .and()
                .statusCode(404);

        System.out.println("При вводе незарегистрированного логина вход не осуществляется");
    }

    @Test
    @DisplayName("Вход в учётную запись курьера без ввода пароля")
    @Description("Проверка авторизации курьера без ввода пароля")
    public void loginCourierWithIncorrectPasswordTest() {


        Response response1 =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(courierDetailsWithIncorrectPassword)
                        .when()
                        .post("/api/v1/courier/login");
        response1.then().assertThat().body("message", Matchers.is("Учетная запись не найдена"))
                .and()
                .statusCode(404);

        System.out.println("При вводе неверного пароля вход не осуществляется");
    }

    @Test
    @DisplayName("Логин неавторизованного Курьера")
    @Description("Проверка авторизации незарегистрированного курьера")
    public void loginUnregisteredCourierTest() {
        String login = RandomStringUtils.randomAlphanumeric(2,15);
        String password = RandomStringUtils.randomAlphanumeric(7,15);

CourierDetails unregisteredCourierDetails1 = new CourierDetails(login, password);


        Response response1 =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(unregisteredCourierDetails1)
                        .when()
                        .post("/api/v1/courier/login");
        response1.then().assertThat().body("message", Matchers.is("Учетная запись не найдена"))
                .and()
                .statusCode(404);

        System.out.println("Авторизация незарегистрированного пользователя не осуществляется");
    }


}
