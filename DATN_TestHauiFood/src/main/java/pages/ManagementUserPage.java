package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import static org.testng.Assert.*;

public class ManagementUserPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public ManagementUserPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }
    //Quản lý người dùng
    private By userManagementMenu = By.xpath("//a[normalize-space()='Quản lý người dùng']");
    private By userTable = By.xpath("//table[contains(@class,'border-collapse')]");
    private By tableRows = By.xpath("//table//tbody/tr");
    public void clickUserManagementMenu() {
        WebElement menu = wait.until(ExpectedConditions.presenceOfElementLocated(userManagementMenu));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", menu);
    }
    public void userTableIsDisplayed() {
        WebElement table = driver.findElement(userTable);
        assertTrue(table.isDisplayed(), "Bảng danh sách người dùng không hiển thị");
    }
    public void userTableHasData() {
        List<WebElement> rows = driver.findElements(tableRows);
        assertTrue(rows.size() > 0, "Không có dòng dữ liệu nào trong bảng");
    }

    //lọc đơn hàng
    // Các selector của dropdown
    private By roleStatusBtn = By.xpath("//span[contains(.,'Vai trò')]");
    private By genderStatusBtn = By.xpath("//span[contains(.,'Giới tính')]");
    private By userStatusBtn = By.xpath("//span[contains(.,'Trạng thái tài khoản')]");
    private By searchButton = By.xpath("//button[contains(.,'Tìm kiếm')]");
    // Click checkbox theo label text
    private void selectCheckboxInDropdown(String labelText) {
        WebElement checkboxLabel = driver.findElement(By.xpath("//div[@class='flex items-center']/span[normalize-space()='" + labelText + "']/preceding-sibling::input[@type='checkbox']"));
        if (!checkboxLabel.isSelected()) {
            checkboxLabel.click();
        }
    }
    // Lọc đơn hàng theo 3 điều kiện
    public void filterUsers(String role, String gender, String userStatus) throws InterruptedException {
        driver.findElement(roleStatusBtn).click();
        selectCheckboxInDropdown(role);

        driver.findElement(genderStatusBtn).click();
        selectCheckboxInDropdown(gender);

        driver.findElement(userStatusBtn).click();
        selectCheckboxInDropdown(userStatus);

        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
        Thread.sleep(1000);
    }
    // Kiểm tra kết quả lọc có đúng từng dòng không
    public void verifyFilteredResultsContent(String expectedRoleStatus, String expectedGender, String expectedUserStatus) {
        List<WebElement> rows = driver.findElements(tableRows);
        assertTrue(rows.size() > 0, "Không có dòng dữ liệu nào để kiểm tra nội dung");
        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));

            String roleStatus = cells.get(4).getText().trim();
            String genderStatus = cells.get(5).getText().trim();
            String userStatus = cells.get(6).getText().trim();

            assertEquals(roleStatus, expectedRoleStatus, "Sai vai trò người dùng");
            assertEquals(genderStatus, expectedGender, "Sai giới tính");
            assertEquals(userStatus, expectedUserStatus, "Sai trạng thái tài khoản");
        }
    }

    //xem chi tiết người dùng
    private By viewUserDetailsBtn = By.xpath("//a[contains(@href, '/user/detail') and @target='_blank']");
    public void clickViewOrderDetail(int rowIndex) {
        List<WebElement> viewOrderDetailButtons = driver.findElements(viewUserDetailsBtn);
        viewOrderDetailButtons.get(rowIndex).click();
        // Chờ và chuyển sang tab mới
        Set<String> windowHandles = driver.getWindowHandles();
        Iterator<String> it = windowHandles.iterator();
        String mainTab = driver.getWindowHandle();
        while (it.hasNext()) {
            String child = it.next();
            if (!child.equals(mainTab)) {
                driver.switchTo().window(child);
                break;
            }
        }
        // Đợi nội dung trang chi tiết hiển thị
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(), 'Chi tiết người dùng')]")));
    }
    public void verifyUserDetailPage() {
        String[][] labelsToCheck = {
                {"Tên người dùng:", "Tên người dùng"},
                {"Email:", "Email"},
                {"Số điện thoại:", "Số điện thoại"},
                {"Giới tính:", "Giới tính"},
        };
        for (String[] pair : labelsToCheck) {
            String label = pair[0];
            String nameForError = pair[1];
            try {
                WebElement valueElement = driver.findElement(
                        By.xpath("//div[text()='" + label + "']/following-sibling::div")
                );
                String value = valueElement.getText().trim();
                assertFalse(value.isEmpty(), "Giá trị của '" + nameForError + "' đang bị rỗng.");
            } catch (NoSuchElementException e) {
                fail("Không tìm thấy nhãn hoặc giá trị của: " + nameForError);
            }
        }
    }

    //Chỉnh sửa người dùng
    private By editUserBtn = By.xpath("//a[contains(@href, '/user/edit/') and contains(@class, 'text-blue')]");
    private By userNameInput = By.xpath("//label[p[text()='Tên người dùng:']]/following-sibling::div//input");
    private By emailInput = By.xpath("//label[p[text()='Email:']]/following-sibling::div//input");
    private By passwordInput = By.xpath("//label[p[text()='Mật khẩu']]/following-sibling::div//input");
    private By confirmPasswordInput = By.xpath("//label[p[text()='Xác nhận mật khẩu']]/following-sibling::div//input");
    private By phoneInput = By.xpath("//label[p[text()='Số điện thoại:']]/following-sibling::div//input");

    private By adminRoleRadio = By.xpath("//input[@type='radio' and @value='admin']");
    private By userRoleRadio = By.xpath("//input[@type='radio' and @value='user']");

    private By lockedRadio = By.xpath("//input[@type='radio' and @value='true']");
    private By unlockedRadio = By.xpath("//input[@type='radio' and @value='false']");
    private By editButton = By.xpath("//span[contains(text(),'Chỉnh sửa')]");
    // ===================== Actions =====================
    public void clickEditUser(int index) {
        List<WebElement> editButtons = wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(editUserBtn, index - 1));
        editButtons.get(index - 1).click();
    }
    public void setUserName(String name) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(userNameInput));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", input);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", input);
        input.clear();
        input.sendKeys(name);
    }
    public void setEmail(String email) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", input);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", input);
        input.clear();
        input.sendKeys(email);
    }
    public void setPassword(String password) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", input);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", input);
        input.clear();
        input.sendKeys(password);
    }

    public void setPhone(String phone) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(phoneInput));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", input);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", input);
        input.clear();
        input.sendKeys(phone);
    }
    public void selectRole(String role) {
        if (role.equalsIgnoreCase("admin")) {
            wait.until(ExpectedConditions.elementToBeClickable(adminRoleRadio)).click();
        } else {
            wait.until(ExpectedConditions.elementToBeClickable(userRoleRadio)).click();
        }
    }
    public void setLocked(String locked) {
        if (locked.equalsIgnoreCase("lock")) {
            wait.until(ExpectedConditions.elementToBeClickable(lockedRadio)).click();
        } else {
            wait.until(ExpectedConditions.elementToBeClickable(unlockedRadio)).click();
        }
    }
    public void clickEditBtn() {
        wait.until(ExpectedConditions.elementToBeClickable(editButton)).click();
    }
    //Khóa người dùng
    private By lockUserBtn = By.xpath("//button[.//span[contains(@class, 'x-icon')]]");
    private By confirmPopupTitle = By.xpath("//div[contains(@class,'font-semibold')]");
    private By confirmPopupButton = By.xpath("//button[.//span[normalize-space()='Xác nhận']]");
    private By message = By.xpath("//div[@role='status']");
    public void clickLockBtn(int index) {
        List<WebElement> lockButtons = wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(lockUserBtn, index - 1));
        WebElement lockButton = lockButtons.get(index - 1);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", lockButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", lockButton);
    }
    public void lockUser(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(confirmPopupTitle));
        wait.until(ExpectedConditions.elementToBeClickable(confirmPopupButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(message));
    }
    //thêm người dùng
    private By addUser = By.xpath("//span[contains(text(),'Thêm người dùng')]");
    private By addMessage = By.xpath("//div[@role='status' and contains(text(), 'người dùng')]");
    public void clickAddUserBtn(){
        wait.until(ExpectedConditions.elementToBeClickable(addUser)).click();
    }
    private boolean isElementVisible(By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }
    public boolean isToastDisplayed() {
        return isElementVisible(addMessage);
    }
    public String getToastMessage() {
        WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(addMessage));
        return error.getText();
    }
}
