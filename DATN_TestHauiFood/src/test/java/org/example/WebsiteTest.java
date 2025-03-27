package org.example;

import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import io.qameta.allure.testng.AllureTestNg;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

@Listeners({AllureTestNg.class})
public class WebsiteTest {
    private WebDriver driver;

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\WebDriver\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    @Step("Mở trang web và kiểm tra tiêu đề")
    public void testHomePageTitle() {
        driver.get("https://hauifood.com/");
        String pageTitle = driver.getTitle();
        saveTextLog("Title của trang: " + pageTitle);
        System.out.println("Title của trang: " + pageTitle);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Attachment(value = "Thông tin test", type = "text/plain")
    public String saveTextLog(String message) {
        return message;
    }
}
