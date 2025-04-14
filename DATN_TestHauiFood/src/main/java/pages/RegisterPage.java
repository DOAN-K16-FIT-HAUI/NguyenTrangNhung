package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class RegisterPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By nameField = By.xpath("//input[@placeholder='Họ tên']");
    private By emailField = By.xpath("//input[@placeholder='Email']");
    private By passwordField = By.xpath("//input[@placeholder='Mật khẩu']");
    private By registerButton = By.xpath("//button[.//p[text()='Đăng ký'] and not(contains(@class, 'Button_disabled'))]");
    //Tiêu đề trang Đăng ký
    private By registerTitle = By.xpath("//h1[text()='Đăng ký']");

    //thông báo đăng ký thành công
    private By registerSuccess = By.xpath("//div[contains(@class, 'Toastify__toast-body')]//div[text()='Đăng ký tài khoản thành công']");
    //thông báo lỗi khi Email đã tồn tại
    private By errorMessage = By.xpath("//div[@role='alert' and contains(., 'Email đã tồn tại')]");

    //thông báo lỗi khi bỏ trống
    private By nameErrorMessage = By.xpath("(//div[@class='form__group'])[1]//p[@class='form__error']");
    private By emailErrorMessage = By.xpath("(//div[@class='form__group'])[2]//p[@class='form__error']");
    private By passwordErrorMessage = By.xpath("(//div[@class='form__group'])[3]//p[@class='form__error']");

    //thông báo lỗi khi sai định dạng
    private By emailInvalidMessage = By.xpath("//p[text()='Email không đúng, vui lòng nhập lại.']");
    private By passwordInvalidMessage = By.xpath("//p[text()='Mật khẩu phải chứa ít nhất 8 ký tự và 1 ký tự đặc biệt @-_']");

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    public boolean isRegisterTitleDisplayed() {
        return driver.findElement(registerTitle).isDisplayed();
    }

    //Name Field
    public void clickNameField(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(nameField)).click();
    }
    public void enterName(String name) {
        WebElement emailElement = wait.until(ExpectedConditions.visibilityOfElementLocated(nameField));
        emailElement.clear();
        emailElement.sendKeys(name);
    }
    public String getNameErrorMessage() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(nameErrorMessage));
            return errorElement.getText();
        } catch (TimeoutException e) {
            return "Không có lỗi bỏ trống Họ tên";
        }
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
            return "Không có lỗi bỏ trống email";
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
            return "Không có lỗi bỏ trống password";
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

    @Step("Nhấn vào nút đăng ký")
    public void clickRegisterButton() {
        WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(registerButton));
        loginBtn.click();
    }

    //kiểm tra thông báo đăng ký thành công
    @Step("Kiểm tra thông báo đăng ký thành công hiển thị")
    public boolean isSuccessMessageDisplayed(){
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(registerSuccess)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }
    @Step("Lấy nội dung thông báo thành công")
    public String getSuccessMessageText() {
        try {
            WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(registerSuccess));
            return errorElement.getText();
        } catch (TimeoutException e) {
            return "Không tìm thấy thông báo thành công!";
        }
    }
    //kiểm tra thông báo lỗi đăng ký thất bại
    @Step("Kiểm tra thông báo đăng ký thất bại hiển thị")
    public boolean isErrorMessageDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    @Step("Lấy nội dung thông báo đăng ký thất bại")
    public String getErrorMessageText() {
        try {
            WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage));
            return errorElement.getText();
        } catch (TimeoutException e) {
            return "Không tìm thấy thông báo lỗi!";
        }
    }

}
