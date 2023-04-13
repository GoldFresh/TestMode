package ru.netology.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataGenerator;
import ru.netology.data.RegistrationDto;;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;

public class UsersRegistrationTest {

    @BeforeEach
    void Setup() {
        open("http://localhost:9999");
    }

    @Test
    void shouldLoginIfActiveUserRegistered() {
        RegistrationDto activeUser = DataGenerator.getActiveUser();
        $("[data-test-id=login] input").setValue(activeUser.getLogin());
        $("[data-test-id=password] input").setValue(activeUser.getPassword());
        $("[data-test-id=action-login]").click();
        $x("//*[@id='root']").shouldHave(Condition.text("Личный кабинет")).shouldBe(Condition.visible);
    }


    @Test
    void shouldGetErrorIfBlockedUser() {
        RegistrationDto blockedUser = DataGenerator.getBlockedUser();
        $("[data-test-id=login] input").setValue(blockedUser.getLogin());
        $("[data-test-id=password] input").setValue(blockedUser.getPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification] .notification__content").
                shouldHave(Condition.text("Пользователь заблокирован")).shouldBe(Condition.visible);
    }


    @Test
    void shouldGetErrorIfWrongLogin() {
        RegistrationDto invalidLoginUser = DataGenerator.getInvalidLoginUser("active");
        $("[data-test-id=login] input").setValue(invalidLoginUser.getLogin());
        $("[data-test-id=password] input").setValue(invalidLoginUser.getPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification] .notification__content").
                shouldHave(Condition.text("Неверно указан логин или пароль")).shouldBe(Condition.visible);
    }

    @Test
    void shouldGetErrorIfWrongPassword() {
        RegistrationDto invalidPassword = DataGenerator.getInvalidPasswordUser("active");
        $("[data-test-id=login] input").setValue(invalidPassword.getLogin());
        $("[data-test-id=password] input").setValue(invalidPassword.getPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification] .notification__content").
                shouldHave(Condition.text("Неверно указан логин или пароль")).shouldBe(Condition.visible);
    }

    @Test
    void shouldGetUnregisteredUser() {
        RegistrationDto unregisteredUser = DataGenerator.getUnregisteredUser("active");
        $("[data-test-id=login] input").setValue(unregisteredUser.getLogin());
        $("[data-test-id=password] input").setValue(unregisteredUser.getPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification] .notification__content").
                shouldHave(Condition.text("Неверно указан логин или пароль")).shouldBe(Condition.visible);
    }
}
