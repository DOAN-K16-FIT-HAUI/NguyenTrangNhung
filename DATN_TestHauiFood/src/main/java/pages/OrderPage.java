package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrderPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public OrderPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    private By nameInput = By.name("recipientName");
    private By phoneInput = By.name("phoneNumber");
    private By addressInput = By.name("address");
    private By codRadio = By.xpath("//label[.//span[contains(text(), 'Thanh toán khi nhận hàng')]]");
    private By bankRadio = By.xpath("//label[.//span[contains(text(), 'Thanh toán trực tuyến VNPay')]]");
    private By prepaidRadio = By.xpath("//label[.//span[contains(text(), 'Thanh toán bằng Ví Điện Tử HaUI Food')]]");
    private By orderBtn = By.xpath("//p[contains(text(),'Đặt đơn')]");
    private By message = By.xpath("//div[@role='alert' and contains(@class, 'Toastify__toast-body')]");

    public void fillShippingInfo(String name, String phone, String address) {
        WebElement nameField = wait.until(ExpectedConditions.visibilityOfElementLocated(nameInput));
        wait.until(ExpectedConditions.elementToBeClickable(nameField)).click();
        nameField.clear();  // Xóa tên mặc định
        nameField.sendKeys(name);

        WebElement phoneField = wait.until(ExpectedConditions.visibilityOfElementLocated(phoneInput));
        wait.until(ExpectedConditions.elementToBeClickable(phoneField)).click();
        phoneField.sendKeys(phone);

        WebElement addressField = wait.until(ExpectedConditions.visibilityOfElementLocated(addressInput));
        wait.until(ExpectedConditions.elementToBeClickable(addressField)).click();
        addressField.sendKeys(address);
    }

    public void clearField() {
        WebElement nameField = wait.until(ExpectedConditions.visibilityOfElementLocated(nameInput));
        clearInputWithBackspace(nameField);
//    WebElement phoneField = wait.until(ExpectedConditions.visibilityOfElementLocated(phoneInput));
//    WebElement addressField = wait.until(ExpectedConditions.visibilityOfElementLocated(addressInput));
//    clearInputWithBackspace(phoneField);
//    clearInputWithBackspace(addressField);
    }
    private void clearInputWithBackspace(WebElement inputField) {
        // Đợi cho đến khi value trong input có dữ liệu
        wait.until(driver -> !inputField.getAttribute("value").trim().isEmpty());
        inputField.click();
        String currentValue = inputField.getAttribute("value");
        for (int i = 0; i < currentValue.length(); i++) {
            inputField.sendKeys(Keys.BACK_SPACE);
        }
    }

    public void selectCash() {
        wait.until(ExpectedConditions.elementToBeClickable(codRadio)).click();
    }
    public void selectBank(){
        wait.until(ExpectedConditions.elementToBeClickable(bankRadio)).click();
    }
    public void selectPrepaid(){
        wait.until(ExpectedConditions.elementToBeClickable(prepaidRadio)).click();
    }
    public void clickOrder() {
        wait.until(ExpectedConditions.elementToBeClickable(orderBtn)).click();
    }

    public boolean isMessageDisplayed() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(message));
            return element.isDisplayed();
        } catch (TimeoutException | NoSuchElementException e) {
            return false;
        }
    }
    public String getNotiMessage() {
        //lấy nội dung thông báo
        try {
            WebElement element = driver.findElement(message);
            return element.getText();
        } catch (NoSuchElementException e) {
            return null;
        }
    }
    //lấy tổng tiền đơn hàng
    private By totalAmount = By.xpath("//span[contains(@class,'CheckOut_checkout__right-cost__')]");
    public int getOrderTotalAmount() {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(totalAmount));
        String totalText = element.getText();
        String numberOnly = totalText.replaceAll("[^0-9]", "");
        return Integer.parseInt(numberOnly);
    }
    //validate sđt
    private By errorName =  By.xpath("//input[@id='recipient-name']/following-sibling::span[contains(@class, 'CheckOut_error-message__9WbNj')]");
    private By errorPhoneNumber = By.xpath("//input[@id='phone-number']/following-sibling::span[contains(@class, 'CheckOut_error-message__9WbNj')]");
    private By errorAddress = By.xpath("//input[@id='address-input']/following-sibling::span[contains(@class, 'CheckOut_error-message__9WbNj')]");
    public String getErrorName() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorName)).getText();
    }
    public String getErrorPhoneNumber() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorPhoneNumber)).getText();
    }
    public String getErrorAddress() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorAddress)).getText();
    }
}
