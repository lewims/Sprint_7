import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.CourierLogin;
import org.example.CourierRemoval;
import org.junit.After;
import org.junit.Before;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.CourierDetails;
import org.hamcrest.Matchers;
import org.junit.Test;
import static io.restassured.RestAssured.given;

public class CourierLogInTest {
    String login = RandomStringUtils.randomAlphanumeric(2, 15);
    String password = RandomStringUtils.randomAlphanumeric(7, 15);
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
@After
public void CourierRemovalAfterLogin() {
    CourierRemoval courierRemoval = new CourierRemoval();
    courierRemoval.courierRemovalMethod(courierDetails)
            .then().assertThat().body("ok", Matchers.is(true))
            .and()
            .statusCode(200);
}

    @Test
    @DisplayName("Логин Курьера")
    @Description("Проверка авторизации курьера с корректным логином и паролем")
    public void loginCourierTest() {
        CourierLogin courierLogin = new CourierLogin();
        courierLogin.courierLoginMethod(courierDetails)
                .then().assertThat().body("id", Matchers.notNullValue())
                .and()
                .statusCode(200);
        System.out.println("Вход успешно выполняется при вводе корректных логина и пароля");
    }

    @Test
    @DisplayName("Вход в учётную запись курьера без ввода логина")
    @Description("Проверка авторизации курьера без ввода логина")
    public void loginCourierWithoutLoginTest() {
        CourierLogin courierLogin = new CourierLogin();
        courierLogin.courierLoginMethod(courierDetailsWithoutLogin).
                then().assertThat().body("message", Matchers.is("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
        System.out.println("Вход в учётную запись курьера без ввода логина не осуществить");
    }

    @Test
    @DisplayName("Вход в учётную запись курьера без ввода пароля")
    @Description("Проверка авторизации курьера без ввода пароля")
    public void loginCourierWithoutPasswordTest() {
        CourierLogin courierLogin = new CourierLogin();
        courierLogin.courierLoginMethod(courierDetailsWithoutPassword)
                .then().assertThat().body("message", Matchers.is("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
        System.out.println("Вход в учётную запись курьера без ввода пароля не осуществить");
    }

    @Test
    @DisplayName("Вход в учётную запись с вводом неверного логина")
    @Description("Проверка авторизации курьера при вводе неверного логина")
    public void loginCourierWithIncorrectLoginTest() {
        CourierLogin courierLogin = new CourierLogin();
        courierLogin.courierLoginMethod(courierDetailsWithIncorrectLogin)
                .then().assertThat().body("message", Matchers.is("Учетная запись не найдена"))
                .and()
                .statusCode(404);
        System.out.println("При вводе неверного логина вход не осуществляется");
    }

    @Test
    @DisplayName("Вход в учётную запись с вводом неправильного пароля")
    @Description("Проверка авторизации курьера при вводе неправильного пароля")
    public void loginCourierWithIncorrectPasswordTest() {
        CourierLogin courierLogin = new CourierLogin();
        courierLogin.courierLoginMethod(courierDetailsWithIncorrectPassword)
                .then().assertThat().body("message", Matchers.is("Учетная запись не найдена"))
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
        CourierLogin courierLogin = new CourierLogin();
        courierLogin.courierLoginMethod(unregisteredCourierDetails1)
                .then().assertThat().body("message", Matchers.is("Учетная запись не найдена"))
                .and()
                .statusCode(404);
        System.out.println("Авторизация незарегистрированного пользователя не осуществляется");
    }
}
