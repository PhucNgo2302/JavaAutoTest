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

public class NewAccountTest {
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
    public void validateEmptyCustomerIdInput() {
        loginTest();
        $("a[href='addAccount.php']").click();
        $(byName("cusid")).setValue(""); // Empty Customer ID
        $("[name='selaccount']").selectOption("Savings");
        $(byName("inideposit")).setValue("1000");
        $("[name='button2']").click();
        $("body").shouldHave(text("Customer ID must not be empty")); // Adjust the expected message
    }

    @Test
    public void validateInvalidCustomerIdInput() {
        loginTest();
        $("a[href='addAccount.php']").click();
        $(byName("cusid")).setValue("invalidID"); // Invalid Customer ID
        $("[name='selaccount']").selectOption("Savings");
        $(byName("inideposit")).setValue("1000");
        $("[name='button2']").click();
        $("body").shouldHave(text("Customer ID is invalid")); // Adjust the expected message
    }

    @Test
    public void validateEmptyInitialDepositInput() {
        loginTest();
        $("a[href='addAccount.php']").click();
        $(byName("cusid")).setValue(String.valueOf(customerId)); // Valid Customer ID
        $("[name='selaccount']").selectOption("Savings");
        $(byName("inideposit")).setValue(""); // Empty Initial Deposit
        $("[name='button2']").click();
        $("body").shouldHave(text("Initial Deposit must not be empty")); // Adjust the expected message
    }

    @Test
    public void validateNegativeInitialDepositInput() {
        loginTest();
        $("a[href='addAccount.php']").click();
        $(byName("cusid")).setValue(String.valueOf(customerId)); // Valid Customer ID
        $("[name='selaccount']").selectOption("Savings");
        $(byName("inideposit")).setValue("-500"); // Negative Initial Deposit
        $("[name='button2']").click();
        $("body").shouldHave(text("Initial Deposit must be positive")); // Adjust the expected message
    }

    @Test
    public void validateNonNumericInitialDepositInput() {
        loginTest();
        $("a[href='addAccount.php']").click();
        $(byName("cusid")).setValue(String.valueOf(customerId)); // Valid Customer ID
        $("[name='selaccount']").selectOption("Savings");
        $(byName("inideposit")).setValue("abc"); // Non-numeric Initial Deposit
        $("[name='button2']").click();
        $("body").shouldHave(text("Initial Deposit must be numeric")); // Adjust the expected message
    }

    @Test
    public void resetButtonClearsAllFields() {
        loginTest();
        $("a[href='addAccount.php']").click();
        $(byName("cusid")).setValue(String.valueOf(customerId));
        $("[name='selaccount']").selectOption("Savings");
        $(byName("inideposit")).setValue("1000");
        $("[name='reset']").click(); // Click Reset button
        $(byName("cusid")).shouldHave(value("")); // Check if Customer ID field is cleared
        $(byName("inideposit")).shouldHave(value("")); // Check if Initial Deposit field is cleared
    }

}
