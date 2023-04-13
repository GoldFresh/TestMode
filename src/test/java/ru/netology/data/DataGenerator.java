package ru.netology.data;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {

    private static final Faker faker = new Faker(new Locale("en"));

    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    private DataGenerator() {
    }

    @BeforeAll
    static void sendRequest(RegistrationDto registrationUsers) {
        given()
                .spec(requestSpec)
                .body(registrationUsers)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    public static String getRandomLogin() {
        return faker.name().username();
    }

    public static String getRandomPassword() {
        return faker.internet().password();
    }

    public static class Registration {
        private Registration() {
        }
    }

    public static RegistrationDto getActiveUser() {
        RegistrationDto registrationUsers = new RegistrationDto(getRandomLogin(), getRandomPassword(), "active");
        sendRequest(registrationUsers);
        return registrationUsers;
    }

    public static RegistrationDto getBlockedUser() {
        RegistrationDto registrationUsers = new RegistrationDto(getRandomLogin(), getRandomPassword(), "blocked");
        sendRequest(registrationUsers);
        return registrationUsers;
    }


    public static RegistrationDto getInvalidPasswordUser(String status) {
        sendRequest(new RegistrationDto(getRandomLogin(), getRandomPassword(), status));
        return new RegistrationDto(getRandomLogin(), getRandomPassword(), status);
    }

    public static RegistrationDto getInvalidLoginUser(String status) {
        sendRequest(new RegistrationDto(getRandomLogin(), getRandomPassword(), status));
        return new RegistrationDto(getRandomLogin(), getRandomPassword(), status);
    }

    public static RegistrationDto getUnregisteredUser(String status) {
        sendRequest(new RegistrationDto(getRandomLogin(), getRandomPassword(), status));
        return new RegistrationDto(getRandomLogin(), getRandomPassword(), status);
    }
}
