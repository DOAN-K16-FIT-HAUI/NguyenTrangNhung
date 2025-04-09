package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class HomePage {
    private WebDriver driver;

    // Định vị phần tử thông báo đăng nhập thành công
    private By successMessage = By.xpath("//div[text()='Đăng nhập thành công']");
    private By homePageElement = By.cssSelector(".Banner_banner__content__WxUF8");

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    // Lấy nội dung thông báo đăng nhập thành công
    public String getSuccessMessage() {
        return driver.findElement(successMessage).getText();
    }

    // Kiểm tra xem có phải trang chủ không
    public boolean isHomePageDisplayed() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(homePageElement)).isDisplayed();
    }

}
