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

public class CustomizedStatementFormTest {
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
    public void testValidAccountAndDateRange() {
        loginTest();
        $("a[href='customizedstatementpage.php']").click();
        $(byName("accountno")).setValue("12345"); // Nhập tài khoản hợp lệ
        $(byName("fdate")).setValue("2023-01-01"); // Ngày bắt đầu hợp lệ
        $(byName("tdate")).setValue("2023-12-31"); // Ngày kết thúc hợp lệ
        $(byName("amountlowerlimit")).setValue("1000"); // Giá trị tối thiểu hợp lệ
        $(byName("numtransaction")).setValue("10"); // Số giao dịch hợp lệ
        $("[name='AccSubmit']").click();

        // Kiểm tra xem báo cáo tùy chỉnh đã được tạo thành công
        $("body").shouldHave(text("Customized Statement Generated Successfully!!!"));
    }

    @Test
    public void testBlankToDate() {
        loginTest();
        $("a[href='customizedstatementpage.php']").click();
        $(byName("accountno")).setValue("12345");
        $(byName("fdate")).setValue("2023-01-01");
        $(byName("tdate")).setValue(""); // Để trống ngày kết thúc
        $(byName("amountlowerlimit")).setValue("1000");
        $(byName("numtransaction")).setValue("10");
        $("[name='AccSubmit']").click();

        // Kiểm tra thông báo lỗi
        $("body").shouldHave(text("To Date must not be blank"));
    }

    @Test
    public void testBlankFromDate() {
        loginTest();
        $("a[href='customizedstatementpage.php']").click();
        $(byName("accountno")).setValue("12345");
        $(byName("fdate")).setValue(""); // Để trống ngày bắt đầu
        $(byName("tdate")).setValue("2023-12-31");
        $(byName("amountlowerlimit")).setValue("1000");
        $(byName("numtransaction")).setValue("10");
        $("[name='AccSubmit']").click();

        // Kiểm tra thông báo lỗi
        $("body").shouldHave(text("From Date must not be blank"));
    }

    @Test
    public void testNegativeMinimumTransactionValue() {
        loginTest();
        $("a[href='customizedstatementpage.php']").click();
        $(byName("accountno")).setValue("12345");
        $(byName("fdate")).setValue("2023-01-01");
        $(byName("tdate")).setValue("2023-12-31");
        $(byName("amountlowerlimit")).setValue("-1000"); // Giá trị tối thiểu âm
        $(byName("numtransaction")).setValue("10");
        $("[name='AccSubmit']").click();

        // Kiểm tra thông báo lỗi
        $("body").shouldHave(text("Minimum Transaction Value must not be negative"));
    }

    @Test
    public void testNegativeNumberOfTransactions() {
        loginTest();
        $("a[href='customizedstatementpage.php']").click();
        $(byName("accountno")).setValue("12345");
        $(byName("fdate")).setValue("2023-01-01");
        $(byName("tdate")).setValue("2023-12-31");
        $(byName("amountlowerlimit")).setValue("1000");
        $(byName("numtransaction")).setValue("-10"); // Số lượng giao dịch âm
        $("[name='AccSubmit']").click();

        // Kiểm tra thông báo lỗi
        $("body").shouldHave(text("Number of Transactions must not be negative"));
    }

    @Test
    public void testInvalidMinimumTransactionValue() {
        loginTest();
        $("a[href='customizedstatementpage.php']").click();
        $(byName("accountno")).setValue("12345");
        $(byName("fdate")).setValue("2023-01-01");
        $(byName("tdate")).setValue("2023-12-31");
        $(byName("amountlowerlimit")).setValue("abc"); // Giá trị tối thiểu không hợp lệ
        $(byName("numtransaction")).setValue("10");
        $("[name='AccSubmit']").click();

        // Kiểm tra thông báo lỗi
        $("body").shouldHave(text("Invalid Minimum Transaction Value"));
    }

    @Test
    public void testInvalidNumberOfTransactions() {
        loginTest();
        $("a[href='customizedstatementpage.php']").click();
        $(byName("accountno")).setValue("12345");
        $(byName("fdate")).setValue("2023-01-01");
        $(byName("tdate")).setValue("2023-12-31");
        $(byName("amountlowerlimit")).setValue("1000");
        $(byName("numtransaction")).setValue("abc"); // Số lượng giao dịch không hợp lệ
        $("[name='AccSubmit']").click();

        // Kiểm tra thông báo lỗi
        $("body").shouldHave(text("Invalid Number of Transactions"));
    }

    @Test
    public void testInvertedDateRange() {
        loginTest();
        $("a[href='customizedstatementpage.php']").click();
        $(byName("accountno")).setValue("12345");
        $(byName("fdate")).setValue("2023-12-31"); // Ngày bắt đầu sau ngày kết thúc
        $(byName("tdate")).setValue("2023-01-01");
        $(byName("amountlowerlimit")).setValue("1000");
        $(byName("numtransaction")).setValue("10");
        $("[name='AccSubmit']").click();

        // Kiểm tra thông báo lỗi
        $("body").shouldHave(text("From Date cannot be after To Date"));
    }


}
