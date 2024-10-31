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

public class FundTransferTest {
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
    public void testPayersAccountNoMustNotBeBlank() {
        loginTest();
        $("a[href='fundtransferpage.php']").click();
        $(byName("payersAccountNo")).setValue(""); // Để trống Payer's Account No
        $(byName("payersAccountNo")).sendKeys(Keys.TAB);
        $("body").shouldHave(text("Payer's Account No must not be blank"));
    }

    @Test
    public void testPayeesAccountNoMustNotBeBlank() {
        loginTest();
        $("a[href='fundtransferpage.php']").click();
        $(byName("payeesAccountNo")).setValue(""); // Để trống Payee's Account No
        $(byName("payeesAccountNo")).sendKeys(Keys.TAB);
        $("body").shouldHave(text("Payee's Account No must not be blank"));
    }

    @Test
    public void testAmountMustNotBeBlank() {
        loginTest();
        $("a[href='fundtransferpage.php']").click();
        $(byName("amount")).setValue(""); // Để trống Amount
        $(byName("amount")).sendKeys(Keys.TAB);
        $("body").shouldHave(text("Amount must not be blank"));
    }

    @Test
    public void testDescriptionMustNotBeBlank() {
        loginTest();
        $("a[href='fundtransferpage.php']").click();
        $(byName("description")).setValue(""); // Để trống Description
        $(byName("description")).sendKeys(Keys.TAB);
        $("body").shouldHave(text("Description must not be blank"));
    }

    @Test
    public void testPayersAccountNoCannotContainSpecialCharacters() {
        loginTest();
        $("a[href='fundtransferpage.php']").click();
        $(byName("payersAccountNo")).setValue("!@#$"); // Nhập ký tự đặc biệt vào Payer's Account No
        $(byName("payersAccountNo")).sendKeys(Keys.TAB);
        $("body").shouldHave(text("Payer's Account No cannot contain special characters"));
    }

    @Test
    public void testPayeesAccountNoCannotContainSpecialCharacters() {
        loginTest();
        $("a[href='fundtransferpage.php']").click();
        $(byName("payeesAccountNo")).setValue("!@#$"); // Nhập ký tự đặc biệt vào Payee's Account No
        $(byName("payeesAccountNo")).sendKeys(Keys.TAB);
        $("body").shouldHave(text("Payee's Account No cannot contain special characters"));
    }

    @Test
    public void testAmountCannotContainSpecialCharacters() {
        loginTest();
        $("a[href='fundtransferpage.php']").click();
        $(byName("amount")).setValue("!@#$"); // Nhập ký tự đặc biệt vào Amount
        $(byName("amount")).sendKeys(Keys.TAB);
        $("body").shouldHave(text("Amount cannot contain special characters"));
    }

    @Test
    public void testPayersAccountNoCannotBeTheSameAsPayeesAccountNo() {
        loginTest();
        $("a[href='fundtransferpage.php']").click();
        $(byName("payersAccountNo")).setValue("12345");
        $(byName("payeesAccountNo")).setValue("12345"); // Payer's Account No giống Payee's Account No
        $(byName("payeesAccountNo")).sendKeys(Keys.TAB);
        $("body").shouldHave(text("Payer's Account No and Payee's Account No cannot be the same"));
    }

    @Test
    public void testPayersAccountMustExist() {
        loginTest();
        $("a[href='fundtransferpage.php']").click();
        $(byName("payersAccountNo")).setValue("99999"); // Nhập Payer's Account No không tồn tại
        $(byName("payersAccountNo")).sendKeys(Keys.TAB);
        $("body").shouldHave(text("Payer's Account No must exist"));
    }

    @Test
    public void testPayeesAccountMustExist() {
        loginTest();
        $("a[href='fundtransferpage.php']").click();
        $(byName("payeesAccountNo")).setValue("99999"); // Nhập Payee's Account No không tồn tại
        $(byName("payeesAccountNo")).sendKeys(Keys.TAB);
        $("body").shouldHave(text("Payee's Account No must exist"));
    }

    @Test
    public void testPayersAccountMustHaveNecessaryBalance() {
        loginTest();
        $("a[href='fundtransferpage.php']").click();
        $(byName("payersAccountNo")).setValue("12345"); // Nhập Payer's Account No có số dư không đủ
        $(byName("payeesAccountNo")).setValue("67890");
        $(byName("amount")).setValue("10000"); // Số tiền lớn hơn số dư
        $(byName("amount")).sendKeys(Keys.TAB);
        $("body").shouldHave(text("Payer's account must have the necessary balance"));
    }

    @Test
    public void testResetButtonFunctionality() {
        loginTest();
        $("a[href='fundtransferpage.php']").click();
        $(byName("payersAccountNo")).setValue("12345");
        $(byName("payeesAccountNo")).setValue("67890");
        $(byName("amount")).setValue("1000");
        $(byName("description")).setValue("Transfer for bills");

        // Nhấn nút Reset
        $("[name='reset']").click();

        // Kiểm tra xem tất cả các trường đã được xóa
        $(byName("payersAccountNo")).shouldHave(value(""));
        $(byName("payeesAccountNo")).shouldHave(value(""));
        $(byName("amount")).shouldHave(value(""));
        $(byName("description")).shouldHave(value(""));
    }

    @Test
    public void testSubmitFundTransfer() {
        loginTest();
        $("a[href='fundtransferpage.php']").click();
        $(byName("payersAccountNo")).setValue("12345");
        $(byName("payeesAccountNo")).setValue("67890");
        $(byName("amount")).setValue("500");
        $(byName("description")).setValue("Transfer for payment");
        $("[name='submit']").click();

        // Kiểm tra thông báo thành côngcu
        $("body").shouldHave(text("Fund Transfer Successful!!!"));
    }

}
