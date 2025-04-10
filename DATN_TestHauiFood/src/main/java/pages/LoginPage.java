package pages;

import io.qameta.allure.Attachment;
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
    private By loginButton = By.xpath("//button[contains(@class, 'Button_primary__9MLUH') and not(contains(@class, 'Button_disabled'))]");
    private By registerLink = By.xpath("//a[@class='SignIn_login__link__OrO5U' and contains(text(),'Đăng ký')]");

    //thông báo lỗi khi login thất bại
    private By errorMessage = By.xpath("//div[text()='Tài khoản hoặc mật khẩu không chính xác']");
    //thông báo lỗi khi bỏ trống
    private By emailErrorMessage = By.xpath("(//div[@class='form__group'])[1]//p[@class='form__error']");
    private By passwordErrorMessage = By.xpath("(//div[@class='form__group'])[2]//p[@class='form__error']");
    //thoong báo lỗi khi sai định dạng
    private By emailInvalidMessage = By.xpath("//p[text()='Email không đúng, vui lòng nhập lại.']");
    private By passwordInvalidMessage = By.xpath("//p[text()='Mật khẩu phải chứa ít nhất 8 ký tự và 1 ký tự đặc biệt @-_']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    // Email Field
    public void clickEmailField() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailField)).click();
    }

    public void enterEmail(String email) {
        WebElement emailElement = wait.until(ExpectedConditions.visibilityOfElementLocated(emailField));
        emailElement.clear();
        emailElement.sendKeys(email);
    }

    public String getEmailErrorMessage() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(emailErrorMessage));
            return errorElement.getText();
        } catch (TimeoutException e) {
            return "Không có lỗi email";
        }
    }

    public String getEmailInvalidMessage() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(emailInvalidMessage));
            return errorElement.getText();
        } catch (TimeoutException e) {
            return "Không có lỗi sai định dạng email";
        }
    }

    //Password Field
    public void clickPasswordField() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField)).click();
    }

    public void enterPassword(String password) {
        WebElement passwordElement = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
        passwordElement.clear();
        passwordElement.sendKeys(password);
    }

    public String getPasswordErrorMessage() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordErrorMessage));
            return errorElement.getText();
        } catch (TimeoutException e) {
            return "Không có lỗi password";
        }
    }

    public String getPasswordInvalidMessage() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInvalidMessage));
            return errorElement.getText();
        } catch (TimeoutException e) {
            return "Không có lỗi sai định dạng password";
        }
    }

    //Click Đăng ký
        public void clickRegister() {
            wait.until(ExpectedConditions.visibilityOfElementLocated(registerLink)).click();
        }


    @Step("Nhấn vào nút đăng nhập")
    public HomePage clickLoginButton() {
        WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        loginBtn.click();
        return new HomePage(driver);
    }

    @Step("Kiểm tra thông báo lỗi hiển thị")
    public boolean isErrorMessageDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    @Step("Lấy nội dung thông báo lỗi")
    public String getErrorMessageText() {
        try {
            WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage));
            return errorElement.getText();
        } catch (TimeoutException e) {
            return "Không tìm thấy thông báo lỗi!";
        }
    }

}
