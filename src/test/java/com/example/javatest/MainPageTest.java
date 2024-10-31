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

public class MainPageTest {
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

    public void newCustomerTest() {
        loginTest();
        $("a[href='addcustomerpage.php']").click();
        $(byName("name")).setValue("Customer Name");
        $(byName("dob")).setValue("01/01/2000");
        $(byName("addr")).setValue("Customer Address");
        $(byName("city")).setValue("City");
        $(byName("state")).setValue("State");
        $(byName("pinno")).setValue("123456");
        $(byName("telephoneno")).setValue("0123456789");
        $(byName("emailid")).setValue("email11@example.com");
        $(byName("password")).setValue("password123");
        $("[name='sub']").click();


        $("body").shouldHave(text("Customer Registered Successfully!!!"));

    }

    @Test

    public void newAccountTest() {
        loginTest();
        $("a[href='addAccount.php']").click();
        $(byName("cusid")).setValue(String.valueOf(customerId));
        $("[name='selaccount']").selectOption("Savings");
        $(byName("inideposit")).setValue("1000");
        $("[name='button2']").click();
        $("body").shouldHave(text("Account Generated Successfully!!!"));
    }

    @Test

    public void depositTest() {
        loginTest();
        $("a[href='DepositInput.php']").click();
        $(byName("accountno")).setValue(String.valueOf(accountId));
        $(byName("ammount")).setValue("500");
        $(byName("desc")).setValue("Deposit");
        $("[name='AccSubmit']").click();

    }

    @Test
    public void withdrawTest() {
        loginTest();
        $("a[href='WithdrawalInput.php']").click();
        $(byName("accountno")).setValue(String.valueOf(accountId));
        $(byName("ammount")).setValue("200");
        $(byName("desc")).setValue("Withdraw");
        $("[name='AccSubmit']").click();
        $("body").shouldHave(text("Transaction details of Withdrawal for Account " + accountId));
    }

    @Test
    public void fundTransferTest() {
        loginTest();
        $("a[href='FundTransInput.php']").click();
        $(byName("payersaccount")).setValue(String.valueOf(payers));
        $(byName("payeeaccount")).setValue(String.valueOf(payess));
        $(byName("ammount")).setValue("300");
        $(byName("desc")).setValue("Transfer");
        $("[name='AccSubmit']").click();
        $("body").shouldHave(text("Fund Transfer Details"));
    }

    @Test
    public void customizedStatementTest() {
        loginTest();
        $("a[href='CustomisedStatementInput.php']").click();
        $(byName("accountno")).setValue(String.valueOf(accountId));
        $(byName("fdate")).setValue("01/01/2023");
        $(byName("tdate")).setValue("12/31/2023");
        $(byName("amountlowerlimit")).setValue("100");
        $(byName("numtransaction")).setValue("5");
        $("[name='AccSubmit']").click();

    }

    @Test
    public void logoutTest() {
        loginTest();
        $("a[href='Logout.php']").click();
        switchTo().alert().accept(); // Đóng thông báo đăng xuất


    }

}
