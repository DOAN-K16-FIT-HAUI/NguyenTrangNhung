package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By emailField = By.name("email");
    private By passwordField = By.name("password");
    private By loginButton = By.xpath("//button[contains(@class, 'Button_primary') and not(contains(@class, 'Button_disabled'))]");
    private By registerLink = By.xpath("//a[contains(@class, 'SignIn_login') and text()='Đăng ký']");
    //thông báo khi đăng nhập thất bại
    private By errorMessage = By.xpath("//div[text()='Tài khoản hoặc mật khẩu không chính xác']");
    //thông báo khi bỏ trống trường
    private By emailErrorMessage = By.xpath("(//div[@class='form__group'])[1]//p[@class='form__error']");
    private By passwordErrorMessage = By.xpath("(//div[@class='form__group'])[2]//p[@class='form__error']");
    //thông báo khi nhp sai định dạng
    private By emailInvalidMessage = By.xpath("//p[text()='Email không đúng, vui lòng nhập lại.']");
    private By passwordInvalidMessage = By.xpath("//p[text()='Mật khẩu phải chứa ít nhất 8 ký tự và 1 ký tự đặc biệt @-_']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void clickEmailField() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailField)).click();
    }

    public void enterEmail(String email) {
        WebElement emailElement = wait.until(ExpectedConditions.visibilityOfElementLocated(emailField));
        emailElement.clear();
        emailElement.sendKeys(email);
    }

    public String getEmailErrorMessage() {
        return getTextIfVisible(emailErrorMessage, "Không có lỗi email");
    }

    public String getEmailInvalidMessage() {
        return getTextIfVisible(emailInvalidMessage, "Không có lỗi sai định dạng email");
    }

    public void clickPasswordField() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField)).click();
    }

    public void enterPassword(String password) {
        WebElement passwordElement = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
        passwordElement.clear();
        passwordElement.sendKeys(password);
    }

    public String getPasswordErrorMessage() {
        return getTextIfVisible(passwordErrorMessage, "Không có lỗi password");
    }

    public String getPasswordInvalidMessage() {
        return getTextIfVisible(passwordInvalidMessage, "Không có lỗi sai định dạng password");
    }

    public void clickRegister() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(registerLink)).click();
    }

    @Step("Nhấn vào nút đăng nhập")
    public HomePage clickLoginButton() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
        return new HomePage(driver);
    }

    @Step("Kiểm tra thông báo lỗi hiển thị")
    public boolean isErrorMessageDisplayed() {
        return isElementVisible(errorMessage);
    }

    @Step("Lấy nội dung thông báo lỗi")
    public String getErrorMessageText() {
        return getTextIfVisible(errorMessage, "Không tìm thấy thông báo lỗi!");
    }

    //Lấy text nếu element visible
    private String getTextIfVisible(By locator, String defaultMsg) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
        } catch (TimeoutException e) {
            return defaultMsg;
        }
    }

    //check element visible
    private boolean isElementVisible(By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }
}
