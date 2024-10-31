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

public class NewCustomerTest {
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
    public void testCustomerNameMustNotBeBlank() {
        loginTest();
        $("a[href='addcustomerpage.php']").click();
        $(byName("name")).setValue(""); // Để trống Customer Name
        $(byName("name")).sendKeys(Keys.TAB);
        $("body").shouldHave(text("Customer Name must not be blank"));
    }

    @Test
    public void testCustomerNameCannotContainNumbers() {
        loginTest();
        $("a[href='addcustomerpage.php']").click();
        $(byName("name")).setValue("12345"); // Nhập số vào Customer Name
        $(byName("name")).sendKeys(Keys.TAB);
        $("body").shouldHave(text("Customer Name must not contain numbers"));
    }

    @Test
    public void testCustomerNameCannotContainSpecialCharacters() {
        loginTest();
        $("a[href='addcustomerpage.php']").click();
        $(byName("name")).setValue("@#$%"); // Nhập ký tự đặc biệt vào Customer Name
        $(byName("name")).sendKeys(Keys.TAB);
        $("body").shouldHave(text("Customer Name must not contain special characters"));
    }

    @Test
    public void testCustomerNameCannotStartWithSpace() {
        loginTest();
        $("a[href='addcustomerpage.php']").click();
        $(byName("name")).setValue(" John"); // Nhập tên bắt đầu bằng khoảng trắng
        $(byName("name")).sendKeys(Keys.TAB);
        $("body").shouldHave(text("Customer Name cannot start with a space"));
    }

    @Test
    public void testDobMustNotBeBlank() {
        loginTest();
        $("a[href='addcustomerpage.php']").click();
        $(byName("dob")).setValue(""); // Để trống Date of Birth
        $(byName("dob")).sendKeys(Keys.TAB);
        $("body").shouldHave(text("Date of Birth must not be blank"));
    }

    @Test
    public void testAddressMustNotBeBlank() {
        loginTest();
        $("a[href='addcustomerpage.php']").click();
        $(byName("addr")).setValue(""); // Để trống Address
        $(byName("addr")).sendKeys(Keys.TAB);
        $("body").shouldHave(text("Address must not be blank"));
    }

    @Test
    public void testCityMustNotBeBlank() {
        loginTest();
        $("a[href='addcustomerpage.php']").click();
        $(byName("city")).setValue(""); // Để trống City
        $(byName("city")).sendKeys(Keys.TAB);
        $("body").shouldHave(text("City must not be blank"));
    }

    @Test
    public void testStateMustNotBeBlank() {
        loginTest();
        $("a[href='addcustomerpage.php']").click();
        $(byName("state")).setValue(""); // Để trống State
        $(byName("state")).sendKeys(Keys.TAB);
        $("body").shouldHave(text("State must not be blank"));
    }

    @Test
    public void testPinMustNotBeBlank() {
        loginTest();
        $("a[href='addcustomerpage.php']").click();
        $(byName("pinno")).setValue(""); // Để trống PIN
        $(byName("pinno")).sendKeys(Keys.TAB);
        $("body").shouldHave(text("PIN Code must not be blank"));
    }

    @Test
    public void testTelephoneMustNotBeBlank() {
        loginTest();
        $("a[href='addcustomerpage.php']").click();
        $(byName("telephoneno")).setValue(""); // Để trống Telephone Number
        $(byName("telephoneno")).sendKeys(Keys.TAB);
        $("body").shouldHave(text("Mobile no must not be blank"));
    }

    @Test
    public void testEmailMustNotBeBlank() {
        loginTest();
        $("a[href='addcustomerpage.php']").click();
        $(byName("emailid")).setValue(""); // Để trống Email
        $(byName("emailid")).sendKeys(Keys.TAB);
        $("body").shouldHave(text("Email ID must not be blank"));
    }

    @Test
    public void testSubmitNewCustomer() {
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



}
