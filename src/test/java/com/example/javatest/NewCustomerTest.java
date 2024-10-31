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
    public void newCustomerTest() {
        loginTest(); // Assuming this logs in the user
        $("a[href='addcustomerpage.php']").click();

        // Test for Customer Name input
        $(byName("name")).setValue(""); // Leave Customer Name empty
        $(byName("name")).sendKeys(Keys.TAB); // Move to next field
        $("body").shouldHave(text("Customer Name must not be blank")); // Adjust expected message

        $(byName("name")).setValue("12345"); // Enter numbers
        $(byName("name")).sendKeys(Keys.TAB);
        $("body").shouldHave(text("Customer Name must not contain numbers")); // Adjust expected message

        $(byName("name")).setValue("@#$%"); // Enter special characters
        $(byName("name")).sendKeys(Keys.TAB);
        $("body").shouldHave(text("Customer Name must not contain special characters")); // Adjust expected message

        $(byName("name")).setValue(" John"); // Leading space
        $(byName("name")).sendKeys(Keys.TAB);
        $("body").shouldHave(text("Customer Name cannot start with a space")); // Adjust expected message

        $(byName("name")).setValue("Customer Name"); // Set valid name

        // Test for Date of Birth input
        $(byName("dob")).setValue(""); // Leave DOB empty
        $(byName("dob")).sendKeys(Keys.TAB); // Move to next field
        $("body").shouldHave(text("Date of Birth must not be blank")); // Adjust expected message
        $(byName("dob")).setValue("01/01/2000"); // Set valid date

        // Test for Address input
        $(byName("addr")).setValue(""); // Leave Address empty
        $(byName("addr")).sendKeys(Keys.TAB);
        $("body").shouldHave(text("Address must not be blank")); // Adjust expected message

        $(byName("addr")).setValue("@#$%"); // Enter special characters
        $(byName("addr")).sendKeys(Keys.TAB);
        $("body").shouldHave(text("Address must not contain special characters")); // Adjust expected message

        $(byName("addr")).setValue(" Address"); // Leading space
        $(byName("addr")).sendKeys(Keys.TAB);
        $("body").shouldHave(text("Address cannot start with a space")); // Adjust expected message

        $(byName("addr")).setValue("Customer Address"); // Set valid address

        // Test for City input
        $(byName("city")).setValue(""); // Leave City empty
        $(byName("city")).sendKeys(Keys.TAB);
        $("body").shouldHave(text("City must not be blank")); // Adjust expected message

        $(byName("city")).setValue("12345"); // Enter numbers
        $(byName("city")).sendKeys(Keys.TAB);
        $("body").shouldHave(text("City must not contain numbers")); // Adjust expected message

        $(byName("city")).setValue("@#$%"); // Enter special characters
        $(byName("city")).sendKeys(Keys.TAB);
        $("body").shouldHave(text("City must not contain special characters")); // Adjust expected message

        $(byName("city")).setValue(" City"); // Leading space
        $(byName("city")).sendKeys(Keys.TAB);
        $("body").shouldHave(text("City cannot start with a space")); // Adjust expected message

        $(byName("city")).setValue("City"); // Set valid city

        // Test for State input
        $(byName("state")).setValue(""); // Leave State empty
        $(byName("state")).sendKeys(Keys.TAB);
        $("body").shouldHave(text("State must not be blank")); // Adjust expected message

        $(byName("state")).setValue("12345"); // Enter numbers
        $(byName("state")).sendKeys(Keys.TAB);
        $("body").shouldHave(text("State must not contain numbers")); // Adjust expected message

        $(byName("state")).setValue("@#$%"); // Enter special characters
        $(byName("state")).sendKeys(Keys.TAB);
        $("body").shouldHave(text("State must not contain special characters")); // Adjust expected message

        $(byName("state")).setValue(" State"); // Leading space
        $(byName("state")).sendKeys(Keys.TAB);
        $("body").shouldHave(text("State cannot start with a space")); // Adjust expected message

        $(byName("state")).setValue("State"); // Set valid state

        // Test for PIN input
        $(byName("pinno")).setValue(""); // Leave PIN empty
        $(byName("pinno")).sendKeys(Keys.TAB);
        $("body").shouldHave(text("PIN Code must not be blank")); // Adjust expected message

        $(byName("pinno")).setValue("@#$%"); // Enter special characters
        $(byName("pinno")).sendKeys(Keys.TAB);
        $("body").shouldHave(text("PIN Code must not contain special characters")); // Adjust expected message

        $(byName("pinno")).setValue("123"); // Enter less than 6 digits
        $(byName("pinno")).sendKeys(Keys.TAB);
        $("body").shouldHave(text("PIN Code must have 6 digits")); // Adjust expected message

        $(byName("pinno")).setValue(" 123456"); // Leading space
        $(byName("pinno")).sendKeys(Keys.TAB);
        $("body").shouldHave(text("PIN Code cannot start with a space")); // Adjust expected message

        $(byName("pinno")).setValue("123456"); // Set valid PIN

        // Test for Telephone Number input
        $(byName("telephoneno")).setValue(""); // Leave Telephone Number empty
        $(byName("telephoneno")).sendKeys(Keys.TAB);
        $("body").shouldHave(text("Mobile no must not be blank")); // Adjust expected message

        $(byName("telephoneno")).setValue("@#$%"); // Enter special characters
        $(byName("telephoneno")).sendKeys(Keys.TAB);
        $("body").shouldHave(text("Mobile no must not contain special characters")); // Adjust expected message

        $(byName("telephoneno")).setValue("abc"); // Enter characters
        $(byName("telephoneno")).sendKeys(Keys.TAB);
        $("body").shouldHave(text("Mobile no must be numeric")); // Adjust expected message

        $(byName("telephoneno")).setValue(" 0123456789"); // Leading space
        $(byName("telephoneno")).sendKeys(Keys.TAB);
        $("body").shouldHave(text("Mobile no cannot start with a space")); // Adjust expected message

        $(byName("telephoneno")).setValue("0123456789"); // Set valid number

        // Test for Email input
        $(byName("emailid")).setValue(""); // Leave Email empty
        $(byName("emailid")).sendKeys(Keys.TAB);
        $("body").shouldHave(text("Email ID must not be blank")); // Adjust expected message

        $(byName("emailid")).setValue("invalid-email"); // Enter invalid email
        $(byName("emailid")).sendKeys(Keys.TAB);
        $("body").shouldHave(text("Email ID is not valid")); // Adjust expected message

        $(byName("emailid")).setValue(" email@example.com"); // Leading space
        $(byName("emailid")).sendKeys(Keys.TAB);
        $("body").shouldHave(text("Email ID cannot start with a space")); // Adjust expected message

        $(byName("emailid")).setValue("email11@example.com"); // Set valid email

        // Test for Password input
        $(byName("password")).setValue("password123"); // Set valid password

        // Submit the form
        $("[name='sub']").click();

        // Verify successful registration
        $("body").shouldHave(text("Customer Registered Successfully!!!"));
    }



}
