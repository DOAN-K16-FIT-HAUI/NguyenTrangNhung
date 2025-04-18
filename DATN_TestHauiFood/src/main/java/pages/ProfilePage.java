package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProfilePage {
    private WebDriver driver;
    private WebDriverWait wait;

    public ProfilePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }
    private By walletBalance = By.xpath("//div[contains(@class,'Profile_profile__coin__')]");

    public double getWalletBalance() {
        WebElement balanceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(walletBalance));
        String balanceText = balanceElement.getText();
        String numberOnly = balanceText.replaceAll("[^0-9]", ""); // loại bỏ chữ và dấu chấm
        return Double.parseDouble(numberOnly);
    }

}
