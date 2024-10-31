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

public class WithdrawTest {
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

    public void loginTest() {
        $(byName("uid")).setValue("mngr599729");
        $(byName("password")).setValue("dumEvar");
        $("[name='btnLogin']").click();
        $("body").shouldHave(text("Manger Id : mngr599729"));
    }

    @Test
    public void verifyAccountNoMustNotBeBlank() {
        loginTest();
        $("a[href='WithdrawalInput.php']").click();
        $(byName("accountno")).setValue(""); // Empty Account No
        $(byName("ammount")).setValue("500");
        $(byName("desc")).setValue("Withdraw");
        $("[name='AccSubmit']").click();
        $("body").shouldHave(text("Account No must not be blank")); // Adjust the expected message
    }

    @Test
    public void verifyAmountMustNotBeBlank() {
        loginTest();
        $("a[href='WithdrawalInput.php']").click();
        $(byName("accountno")).setValue("12345"); // Valid Account No
        $(byName("ammount")).setValue(""); // Empty Amount
        $(byName("desc")).setValue("Withdraw");
        $("[name='AccSubmit']").click();
        $("body").shouldHave(text("Amount must not be blank")); // Adjust the expected message
    }

    @Test
    public void verifyDescriptionMustNotBeBlank() {
        loginTest();
        $("a[href='WithdrawalInput.php']").click();
        $(byName("accountno")).setValue("12345"); // Valid Account No
        $(byName("ammount")).setValue("500");
        $(byName("desc")).setValue(""); // Empty Description
        $("[name='AccSubmit']").click();
        $("body").shouldHave(text("Description must not be blank")); // Adjust the expected message
    }

    @Test
    public void verifyAccountNoMustExist() {
        loginTest();
        $("a[href='WithdrawalInput.php']").click();
        $(byName("accountno")).setValue("99999"); // Non-existent Account No
        $(byName("ammount")).setValue("500");
        $(byName("desc")).setValue("Withdraw");
        $("[name='AccSubmit']").click();
        $("body").shouldHave(text("Account No does not exist")); // Adjust the expected message
    }

    @Test
    public void verifyAmountFieldSpecialCharactersNotAllowed() {
        loginTest();
        $("a[href='WithdrawalInput.php']").click();
        $(byName("accountno")).setValue("12345"); // Valid Account No
        $(byName("ammount")).setValue("@!#"); // Special characters in Amount
        $(byName("desc")).setValue("Withdraw");
        $("[name='AccSubmit']").click();
        $("body").shouldHave(text("Amount must be numeric")); // Adjust the expected message
    }

    @Test
    public void verifyAmountFieldCharactersNotAllowed() {
        loginTest();
        $("a[href='WithdrawalInput.php']").click();
        $(byName("accountno")).setValue("12345"); // Valid Account No
        $(byName("ammount")).setValue("abc"); // Characters in Amount
        $(byName("desc")).setValue("Withdraw");
        $("[name='AccSubmit']").click();
        $("body").shouldHave(text("Amount must be numeric")); // Adjust the expected message
    }

    @Test
    public void verifyAccountMustHaveNecessaryBalance() {
        loginTest();
        $("a[href='WithdrawalInput.php']").click();
        $(byName("accountno")).setValue("12345"); // Valid Account No with insufficient balance
        $(byName("ammount")).setValue("10000"); // Attempt to withdraw more than available balance
        $(byName("desc")).setValue("Withdraw");
        $("[name='AccSubmit']").click();
        $("body").shouldHave(text("Insufficient balance")); // Adjust the expected message
    }

    @Test
    public void resetButtonMustWork() {
        loginTest();
        $("a[href='WithdrawalInput.php']").click();
        $(byName("accountno")).setValue("12345");
        $(byName("ammount")).setValue("500");
        $(byName("desc")).setValue("Withdraw");
        $("[name='reset']").click(); // Click Reset button
        $(byName("accountno")).shouldHave(value("")); // Check if Account No field is cleared
        $(byName("ammount")).shouldHave(value("")); // Check if Amount field is cleared
        $(byName("desc")).shouldHave(value("")); // Check if Description field is cleared
    }


}
