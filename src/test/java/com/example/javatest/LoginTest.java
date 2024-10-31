package com.example.javatest;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.openqa.selenium.chrome.ChromeOptions;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import com.codeborne.selenide.WebDriverRunner;

public class LoginTest {
    private int customerId = 11772;
    private int accountId = 139478;
    private int payers = 139478;
    private int payess = 139481;

    @BeforeAll
    public static void setUpAll() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        Configuration.browserSize = "1280x800";
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    public void setUp() {
        Configuration.browserCapabilities = new ChromeOptions().addArguments("--remote-allow-origins=*");
        open("https://www.demo.guru99.com/V4/index.php");
    }

    @Test
    @Order(1)
    public void testLoginWithIncorrectCredentials() {
        $(byName("uid")).setValue("incorrectUserId");
        $(byName("password")).setValue("incorrectPassword");
        $("[name='btnLogin']").click();
        $("body").shouldHave(text("User or Password is not valid"));
    }

    @Test
    @Order(2)
    public void testLoginWithIncorrectUserId() {
        $(byName("uid")).setValue("incorrectUserId");
        $(byName("password")).setValue("correctPassword"); // Giả sử đây là password đúng
        $("[name='btnLogin']").click();
        $("body").shouldHave(text("User or Password is not valid"));
    }

    @Test
    @Order(3)
    public void testLoginWithIncorrectPassword() {
        $(byName("uid")).setValue("correctUserId"); // Giả sử đây là user ID đúng
        $(byName("password")).setValue("incorrectPassword");
        $("[name='btnLogin']").click();
        $("body").shouldHave(text("User or Password is not valid"));
    }

    @Test
    @Order(4)
    public void testLoginWithCorrectCredentials() {
        $(byName("uid")).setValue("mngr599729"); // Giả sử đây là user ID đúng
        $(byName("password")).setValue("dumEvar"); // Giả sử đây là password đúng
        $("[name='btnLogin']").click();
        $("body").shouldHave(text("Manger Id : mngr599729")); // Kiểm tra thông báo đăng nhập thành công
    }

    @Test
    @Order(5)
    public void testLoginWithEmptyUserIdAndPassword() {
        $(byName("uid")).setValue("");
        $(byName("password")).setValue("");
        $("[name='btnLogin']").click();
        $("body").shouldHave(text("User ID must not be blank"));
        $("body").shouldHave(text("Password must not be blank"));
    }

    @Test
    @Order(6)
    public void testLoginWithEmptyPassword() {
        $(byName("uid")).setValue("correctUserId"); // Giả sử đây là user ID đúng
        $(byName("password")).setValue("");
        $("[name='btnLogin']").click();
        $("body").shouldHave(text("Password must not be blank"));
    }

    @Test
    @Order(7)
    public void testLoginWithEmptyUserId() {
        $(byName("uid")).setValue("");
        $(byName("password")).setValue("correctPassword"); // Giả sử đây là password đúng
        $("[name='btnLogin']").click();
        $("body").shouldHave(text("User ID must not be blank"));
    }

    @Test
    @Order(8)
    public void testResetButtonClearsFields() {
        $(byName("uid")).setValue("testUserId");
        $(byName("password")).setValue("testPassword");
        $("[name='btnReset']").click(); // Nhấp vào nút Reset
        $(byName("uid")).shouldHave(value(""));
        $(byName("password")).shouldHave(value(""));
    }

}
