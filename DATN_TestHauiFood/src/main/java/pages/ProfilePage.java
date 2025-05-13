package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

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
    //Xem danh sách đơn hàng
    private By yourOrderBtn = By.xpath("//p[text()='Đơn hàng']/parent::button");
    private By waitConfirmList = By.xpath("//div[@class='HistoryOder_order-status__item__AHlZi' and contains(text(), 'Chờ xác nhận')]");
    private By cancelBtn = By.xpath("//button[p[text()='Hủy'] and contains(@class, 'Button_cancel')]");
    private By viewConfirmCancel = By.xpath("//div[contains(@class,'HistoryOderItem_modal-content__')]");
    private By reasonInput = By.xpath("//textarea[contains(@class,'HistoryOderItem_form-textarea__')]");
    private By confirmBtn = By.xpath("//button[.//p[text()='Xác nhận']]");
    private By paymentMethod = By.xpath("//div[contains(@class,'HistoryOderItem_payment-method__')]");
    public void clickViewOrder(){
        WebElement button = wait.until(ExpectedConditions.presenceOfElementLocated(yourOrderBtn));
        // Scroll đến element
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", button);
        // Move chuột đến element
        Actions actions = new Actions(driver);
        actions.moveToElement(button).perform();
        wait.until(ExpectedConditions.elementToBeClickable(button));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
    }
    public String getPaymentMethodText() {
        WebElement paymentElement = wait.until(ExpectedConditions.visibilityOfElementLocated(paymentMethod));
        return paymentElement.getText();
    }

    public void clickWaitConfirm() {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(waitConfirmList));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    public void clickCancelBtn(){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 500);");
        wait.until(ExpectedConditions.elementToBeClickable(cancelBtn)).click();
    }
    public boolean viewConfirmCancelDisplayed(){
        return wait.until(driver -> driver.findElement(viewConfirmCancel).isDisplayed());
    }
    public void fillReason(String reason) {
        WebElement reasonField = wait.until(ExpectedConditions.visibilityOfElementLocated(reasonInput));
        wait.until(ExpectedConditions.elementToBeClickable(reasonField)).click();
        reasonField.clear();
        reasonField.sendKeys(reason);
    }
    public void clickConfirmCancel(){
        wait.until(ExpectedConditions.elementToBeClickable(confirmBtn)).click();
    }
    //Chưa có đơn hàng nào
    private By emptyOrder = By.xpath("//div[contains(@class,'HistoryOder_empty-order__')]");
    public boolean viewEmptyOrder(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(emptyOrder)).isDisplayed();
    }
    // Tab đơn hàng theo tên
    public void clickOrderTabByIndex(int index) {
        String xpath = "(//div[contains(@class,'HistoryOder_order-status__item')])[" + index + "]";
        WebElement tab = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        tab.click();
    }
    // Kiểm tra đơn hàng có hiển thị không
    public boolean isOrderListVisibleForTab(String expectedStatus) {
        List<WebElement> statusElements = driver.findElements(By.xpath("//div[contains(@class,'HistoryOderItem_order-status__')]"));

        if (statusElements.isEmpty()) {
            return true; // Không có đơn hàng nào trong tab này → coi là hợp lệ (nếu tab rỗng)
        }
        for (WebElement status : statusElements) {
            String actualStatus = status.getText().trim();
            if (!actualStatus.equalsIgnoreCase(expectedStatus)) {
                System.out.println("Trạng thái không khớp: " + actualStatus + " != " + expectedStatus);
                return false;
            }
        }
        return true;
    }
    //xem thông tin tài khoản
    private By fullNameInput = By.name("fullName");
    private By emailInput = By.name("email");
    private By phoneInput = By.name("phone");
    private By genderValue = By.cssSelector(".Profile_gender__selected-value__XeSvF");

    public String getFullName() {
        return driver.findElement(fullNameInput).getAttribute("value");
    }
    public String getEmail() {
        return driver.findElement(emailInput).getAttribute("value");
    }
    public String getPhone() {
        return driver.findElement(phoneInput).getAttribute("value");
    }
    public String getGender() {
        return driver.findElement(genderValue).getText();
    }
    //update thông tin cá nhân
    private By editBtn = By.xpath("//button[contains(@class, 'Button_wrapper') and .//p[text()='Chỉnh sửa']]");
    private By genderDropdown = By.xpath("//div[contains(@class,'Profile_gender-container__')]");
    private By updateBtn = By.xpath("//button[contains(@class, 'Button_wrapper') and .//p[text()='Cập Nhật']]");
    private By cancelEditBtn = By.xpath("//button[contains(@class, 'Button_wrapper') and .//p[text()='Hủy']]");

    public void clickEditBtn(){
        wait.until(ExpectedConditions.elementToBeClickable(editBtn)).click();
    }
    public void updateProfile(String newName, String newPhone, String gender) {
        Actions actions = new Actions(driver);

        WebElement nameField = wait.until(ExpectedConditions.visibilityOfElementLocated(fullNameInput));
        nameField.click();
        actions.keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).sendKeys(Keys.DELETE).perform();
        nameField.sendKeys(newName);

        WebElement phoneField = wait.until(ExpectedConditions.visibilityOfElementLocated(phoneInput));
        phoneField.click();
        actions.keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).sendKeys(Keys.DELETE).perform();
        phoneField.sendKeys(newPhone);

        WebElement genderDrop = wait.until(ExpectedConditions.elementToBeClickable(genderDropdown));
        genderDrop.click();

        String genderOptionXPath = String.format("//ul[contains(@class, 'Profile_gender__options')]//li[normalize-space()='%s']", gender);
        WebElement genderOption = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(genderOptionXPath)));
        genderOption.click();
    }
    public void clickUpdateBtn(){
        wait.until(ExpectedConditions.elementToBeClickable(updateBtn)).click();
    }
    public void clickCancelEditBtn(){
        wait.until(ExpectedConditions.elementToBeClickable(cancelEditBtn)).click();
    }

    //Đổi mật khẩu
    private By changePassword = By.xpath("//p[text()='Đổi mật khẩu']/parent::button");
    private By oldPWInput = By.name("oldPassword");
    private By newPWInput = By.name("newPassword");
    private By confirmPWInput = By.name("confirmPassword");
    public void clickChangePW(){
        WebElement button = wait.until(ExpectedConditions.presenceOfElementLocated(changePassword));
        // Move chuột đến element
        Actions actions = new Actions(driver);
        actions.moveToElement(button).perform();
        wait.until(ExpectedConditions.elementToBeClickable(button));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
    }
    public void changePasswordInput(String oldPW, String newPW, String confirmPW){
        WebElement oldPWField = wait.until(ExpectedConditions.visibilityOfElementLocated(oldPWInput));
        oldPWField.click();
        oldPWField.sendKeys(oldPW);

        WebElement newPWField = wait.until(ExpectedConditions.visibilityOfElementLocated(newPWInput));
        newPWField.click();
        newPWField.sendKeys(newPW);

        WebElement confirmPWField = wait.until(ExpectedConditions.visibilityOfElementLocated(confirmPWInput));
        confirmPWField.click();
        confirmPWField.sendKeys(confirmPW);
    }
    private By errorMessageChangePW = By.xpath("//div[contains(@class,'Profile_errors-message__')]");
    public String getErrorMessageChangePW(){
        WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessageChangePW));
        return errorElement.getText();
    }
    // Kiểm tra xem đơn hàng có mã cụ thể có hiển thị trong tab tương ứng không
    // Lấy danh sách mã đơn hàng hiển thị ở tab hiện tại
    public List<String> getAllOrderIdsInCurrentTab() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(text(), 'Mã đơn hàng:')]")
        ));
        List<WebElement> orderIdElements = driver.findElements(
                By.xpath("//div[contains(text(), 'Mã đơn hàng:')]")
        );
        return orderIdElements.stream()
                .map(e -> e.getText().replace("Mã đơn hàng: ", "").trim())
                .collect(Collectors.toList());

    }
    public boolean isOrderIdVisibleInTab(String expectedStatus, String orderId) {
        // Kiểm tra trạng thái đơn hàng có khớp không (bạn đã có phương thức khác cho việc này nếu muốn)
        List<String> allOrderIds = getAllOrderIdsInCurrentTab();
        System.out.println("Danh sách mã đơn hàng ở tab '" + expectedStatus + "': " + allOrderIds);
        return allOrderIds.contains(orderId);
    }
}
