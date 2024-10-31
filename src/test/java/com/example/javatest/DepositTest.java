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
import org.openqa.selenium.Keys;

public class DepositTest {
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
    public void validateAccountNoSpecialCharacterInput() {
        loginTest();
        $("a[href='DepositInput.php']").click(); // Open Deposit form
        $(byName("accountno")).setValue("@!#"); // Enter special characters in Account No
        $(byName("ammount")).setValue("500"); // Fill Amount
        $(byName("desc")).setValue("Deposit"); // Fill Description
        $(byName("accountno")).sendKeys(Keys.TAB); // Move to next field
        $("body").shouldHave(text("Account No must be numeric")); // Adjust expected message
    }

    @Test
    public void validateAccountNoCharacterInput() {
        loginTest();
        $("a[href='DepositInput.php']").click(); // Open Deposit form
        $(byName("accountno")).setValue("abc"); // Enter characters in Account No
        $(byName("ammount")).setValue("500"); // Fill Amount
        $(byName("desc")).setValue("Deposit"); // Fill Description
        $(byName("accountno")).sendKeys(Keys.TAB); // Move to next field
        $("body").shouldHave(text("Account No must be numeric")); // Adjust expected message
    }

    @Test
    public void validateAccountNoFieldEmpty() {
        loginTest();
        $("a[href='DepositInput.php']").click(); // Open Deposit form
        $(byName("accountno")).setValue(""); // Leave Account No empty
        $(byName("ammount")).setValue("500"); // Fill Amount
        $(byName("desc")).setValue("Deposit"); // Fill Description
        $(byName("accountno")).sendKeys(Keys.TAB); // Move to next field
        $("body").shouldHave(text("Account No must not be blank")); // Adjust expected message
    }

    @Test
    public void validateAmountSpecialCharacterInput() {
        loginTest();
        $("a[href='DepositInput.php']").click(); // Open Deposit form
        $(byName("accountno")).setValue("12345"); // Valid Account No
        $(byName("ammount")).setValue("@!#"); // Enter special characters in Amount
        $(byName("desc")).setValue("Deposit"); // Fill Description
        $(byName("ammount")).sendKeys(Keys.TAB); // Move to next field
        $("body").shouldHave(text("Amount must be numeric")); // Adjust expected message
    }

    @Test
    public void validateAmountCharacterInput() {
        loginTest();
        $("a[href='DepositInput.php']").click(); // Open Deposit form
        $(byName("accountno")).setValue("12345"); // Valid Account No
        $(byName("ammount")).setValue("abc"); // Enter characters in Amount
        $(byName("desc")).setValue("Deposit"); // Fill Description
        $(byName("ammount")).sendKeys(Keys.TAB); // Move to next field
        $("body").shouldHave(text("Amount must be numeric")); // Adjust expected message
    }

    @Test
    public void validateAmountFieldEmpty() {
        loginTest();
        $("a[href='DepositInput.php']").click(); // Open Deposit form
        $(byName("accountno")).setValue("12345"); // Valid Account No
        $(byName("ammount")).setValue(""); // Leave Amount empty
        $(byName("desc")).setValue("Deposit"); // Fill Description
        $(byName("ammount")).sendKeys(Keys.TAB); // Move to next field
        $("body").shouldHave(text("Amount must not be blank")); // Adjust expected message
    }

    @Test
    public void validateDescriptionFieldEmpty() {
        loginTest();
        $("a[href='DepositInput.php']").click(); // Open Deposit form
        $(byName("accountno")).setValue("12345"); // Valid Account No
        $(byName("ammount")).setValue("500"); // Fill Amount
        $(byName("desc")).setValue(""); // Leave Description empty
        $(byName("desc")).sendKeys(Keys.TAB); // Move to next field
        $("body").shouldHave(text("Description must not be blank")); // Adjust expected message
    }

    @Test
    public void validateSubmitButton() {
        loginTest();
        $("a[href='DepositInput.php']").click(); // Open Deposit form
        $(byName("accountno")).setValue("12345"); // Valid Account No
        $(byName("ammount")).setValue("500"); // Fill Amount
        $(byName("desc")).setValue("Deposit"); // Fill Description
        $("[name='AccSubmit']").click(); // Click Submit button
        $("body").shouldHave(text("Deposit Successful")); // Adjust expected message for successful deposit
    }


}
