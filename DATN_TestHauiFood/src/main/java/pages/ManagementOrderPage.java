package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import static org.testng.Assert.*;

public class ManagementOrderPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public ManagementOrderPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }
    private By emailField = By.xpath("//input[@type='text' and @placeholder='Từ khoá']");
    private By passwordField = By.xpath("//input[@type='password' and @placeholder='Từ khoá']");
    private By loginButton = By.xpath("//button[.//span[text()='Đăng nhập']]");
    private By message = By.xpath("//div[@role='status']");

    public void enterEmail(String email) {
        WebElement emailElement = wait.until(ExpectedConditions.visibilityOfElementLocated(emailField));
        emailElement.clear();
        emailElement.sendKeys(email);
    }

    public void enterPassword(String password) {
        WebElement passwordElement = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
        passwordElement.clear();
        passwordElement.sendKeys(password);
    }

    public void clickLoginButton() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }
    private boolean isElementVisible(By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }
    public boolean isToastDisplayed() {
        return isElementVisible(message);
    }
    public String getErrorMessage() {
        WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(message));
        return error.getText();
    }
    //Đăng xuất
    private By avatar = By.xpath("//div[contains(@class, 'relative')]");
    private By logoutBtn = By.xpath("//button[span[normalize-space()='Đăng xuất']]");
    public void clickAvt() {
        WebElement avatarElement = wait.until(ExpectedConditions.elementToBeClickable(avatar));
        Actions actions = new Actions(driver);
        actions.moveToElement(avatarElement).pause(Duration.ofMillis(500)).click().perform();
    }
    public void clickLogout(){
        wait.until(ExpectedConditions.elementToBeClickable(logoutBtn)).click();
    }

    //Quản lý đơn hàng
    private By orderManagementMenu = By.xpath("//a[normalize-space()='Quản lý đơn hàng']");
    private By orderTable = By.xpath("//table[contains(@class,'border-collapse')]");
    private By tableRows = By.xpath("//table//tbody/tr");
    public void clickOrderManagementMenu() {
        WebElement menu = wait.until(ExpectedConditions.presenceOfElementLocated(orderManagementMenu));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", menu);
    }
    public void orderTableIsDisplayed() {
        WebElement table = driver.findElement(orderTable);
        assertTrue(table.isDisplayed(), "Bảng danh sách đơn hàng không hiển thị");
    }
    public void orderTableHasData() {
        List<WebElement> rows = driver.findElements(tableRows);
        assertTrue(rows.size() > 0, "Không có dòng dữ liệu nào trong bảng đơn hàng");
    }

    //lọc đơn hàng
    // Các selector của dropdown
    private By orderStatusBtn = By.xpath("//span[contains(.,'Trạng thái đơn hàng')]");
    private By paymentMethodBtn = By.xpath("//span[contains(.,'Phương thức thanh toán')]");
    private By paymentStatusBtn = By.xpath("//span[contains(.,'Trạng thái thanh toán')]");
    private By searchButton = By.xpath("//button[contains(.,'Tìm kiếm')]");

    // Click checkbox theo label text
    private void selectCheckboxInDropdown(String labelText) {
        WebElement checkboxLabel = driver.findElement(By.xpath("//div[@class='flex items-center']/span[normalize-space()='" + labelText + "']/preceding-sibling::input[@type='checkbox']"));
        if (!checkboxLabel.isSelected()) {
            checkboxLabel.click();
        }
    }

    // Lọc đơn hàng theo 3 điều kiện
    public void filterOrders(String status, String paymentMethod, String paymentStatus) throws InterruptedException {
        driver.findElement(orderStatusBtn).click();
        selectCheckboxInDropdown(status);

        driver.findElement(paymentMethodBtn).click();
        selectCheckboxInDropdown(paymentMethod);

        driver.findElement(paymentStatusBtn).click();
        selectCheckboxInDropdown(paymentStatus);

        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
        Thread.sleep(1000);
    }
    // Kiểm tra kết quả lọc có đúng từng dòng không
    public void verifyFilteredResultsContent(String expectedStatus, String expectedPaymentMethod, String expectedPaymentStatus) {
        List<WebElement> rows = driver.findElements(tableRows);
        assertTrue(rows.size() > 0, "Không có dòng dữ liệu nào để kiểm tra nội dung");

        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            String paymentMethod = cells.get(6).getText().trim();
            String paymentStatus = cells.get(7).getText().trim();
            String orderStatus = cells.get(9).getText().trim();

            assertEquals(orderStatus, expectedStatus, "Sai trạng thái đơn hàng");
            assertEquals(paymentMethod, expectedPaymentMethod, "Sai phương thức thanh toán");
            assertEquals(paymentStatus, expectedPaymentStatus, "Sai trạng thái thanh toán");
        }
    }
    //xem chi tiết đơn hàng
    private By viewOrderDetailsBtn = By.xpath("//a[contains(@href, '/order/detail') and @target='_blank']");
    public void clickViewOrderDetail(int rowIndex) {
        List<WebElement> viewOrderDetailButtons = driver.findElements(viewOrderDetailsBtn);
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
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(), 'Chi tiết đơn hàng')]")));
    }
    public void verifyOrderDetailPage() {
        //Danh sách các nhãn cần kiểm tra và vị trí value tương ứng
        String[][] labelsToCheck = {
                {"Mã đơn hàng:", "Mã đơn hàng"},
                {"Hình thức thanh toán:", "Hình thức thanh toán"},
                {"Trạng thái:", "Trạng thái"},
                {"Trạng thái thanh toán:", "Trạng thái thanh toán"},
                {"Tên người nhận:", "Tên người nhận"},
                {"Số điện thoại người nhận:", "Số điện thoại người nhận"},
                {"Tỉnh/Thành Phố:", "Tỉnh/Thành Phố"},
                {"Xã/Phường:", "Xã/Phường"}
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
        //Kiểm tra table sản phẩm có ít nhất 1 dòng dữ liệu
        List<WebElement> productRows = driver.findElements(
                By.xpath("//table//tbody/tr")
        );
        assertTrue(productRows.size() > 0, "Bảng sản phẩm không có dữ liệu.");
    }

    private By firstPendingOrderRow = By.xpath("//table//tbody/tr[td[10][normalize-space()='Chờ xác nhận']][1]");
    private By confirmButtonInTable = By.xpath("//table//tbody/tr[td[10][normalize-space()='Chờ xác nhận']][1]//button[contains(@class, 'text-blueConfirm')]");
    private By confirmPopupTitle = By.xpath("//div[contains(@class,'font-semibold')]");
    private By confirmPopupButton = By.xpath("//button[.//span[normalize-space()='Xác nhận']]");
    private By orderCodeCell = By.xpath(".//td[2]"); // trong dòng đơn hàng
    private By cancelBtn = By.xpath("//table//tbody/tr[td[10][normalize-space()='Chờ xác nhận']][1]//button[contains(@class, 'text-red-500')]");
    private By reasonCancel = By.xpath("//textarea[@placeholder='Nhập lý do từ chối...']");
    //xác nhận đơn hàng
    public String confirmFirstPendingOrder() {
        // Tìm dòng đơn hàng đầu tiên đang chờ xác nhận
        WebElement row = wait.until(ExpectedConditions.presenceOfElementLocated(firstPendingOrderRow));
        assertNotNull(row, "Không tìm thấy đơn hàng nào đang ở trạng thái 'Chờ xác nhận'");

        // Lấy mã đơn hàng từ cột 1 trong dòng đó
        String orderCode = row.findElement(orderCodeCell).getText().trim();
        System.out.println("Mã đơn hàng được chọn: " + orderCode);
        row.findElement(confirmButtonInTable).click();

        // Đợi popup hiện và bấm nút xác nhận
        wait.until(ExpectedConditions.visibilityOfElementLocated(confirmPopupTitle));
        wait.until(ExpectedConditions.elementToBeClickable(confirmPopupButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(message));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Đợi trạng thái đơn hàng tương ứng được cập nhật lại
        By updatedStatusCell = By.xpath("//table//tbody/tr[td[2][normalize-space()='" + orderCode + "']]/td[10]");
        WebElement statusCell = new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(driver -> {
                    try {
                        WebElement cell = driver.findElement(updatedStatusCell);
                        String statusText = cell.getText().trim();
                        return statusText.isEmpty() ? null : cell;
                    } catch (Exception e) {
                        return null;
                    }
                });
        assertNotNull(statusCell, "Không tìm thấy trạng thái đơn hàng sau khi xác nhận");
        String updatedStatus = statusCell.getText().trim();
        System.out.println("Trạng thái đơn hàng sau xác nhận: " + updatedStatus);
        assertEquals(updatedStatus, "Đang chuẩn bị", "Trạng thái đơn hàng không được cập nhật chính xác");
        return orderCode;
    }
    //từ chối đơn hàng
    public String deniedFirstPendingOrder(String text) {
        WebElement row = wait.until(ExpectedConditions.presenceOfElementLocated(firstPendingOrderRow));
        assertNotNull(row, "Không tìm thấy đơn hàng nào đang ở trạng thái 'Chờ xác nhận'");

        String orderCode = row.findElement(orderCodeCell).getText().trim();
        System.out.println("Mã đơn hàng được chọn: " + orderCode);
        row.findElement(cancelBtn).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(confirmPopupTitle));
        WebElement reason = wait.until(ExpectedConditions.visibilityOfElementLocated(reasonCancel));
        reason.clear();
        reason.sendKeys(text);
        wait.until(ExpectedConditions.elementToBeClickable(confirmPopupButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(message));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        By updatedStatusCell = By.xpath("//table//tbody/tr[td[2][normalize-space()='" + orderCode + "']]/td[10]");
        WebElement statusCell = new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(driver -> {
                    try {
                        WebElement cell = driver.findElement(updatedStatusCell);
                        String statusText = cell.getText().trim();
                        return statusText.isEmpty() ? null : cell;
                    } catch (Exception e) {
                        return null;
                    }
                });
        assertNotNull(statusCell, "Không tìm thấy trạng thái đơn hàng sau khi xác nhận");
        String updatedStatus = statusCell.getText().trim();
        System.out.println("Trạng thái đơn hàng sau xác nhận: " + updatedStatus);
        assertEquals(updatedStatus, "Từ chối", "Trạng thái đơn hàng không được cập nhật chính xác");

        return orderCode;
    }
    private By blankMessage = By.xpath("//div[@role='status' and contains(text(), 'không được rỗng')]");
    public void blankReasonCancel(){
        WebElement row = wait.until(ExpectedConditions.presenceOfElementLocated(firstPendingOrderRow));
        assertNotNull(row, "Không tìm thấy đơn hàng nào đang ở trạng thái 'Chờ xác nhận'");

        row.findElement(cancelBtn).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(confirmPopupTitle));
        wait.until(ExpectedConditions.elementToBeClickable(confirmPopupButton)).click();
        WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(blankMessage));
        assertEquals(error.getText().trim(), "Lý do không được bỏ trống", "Thông báo không đúng mong đợi");
    }
    //Giao hàng cho đơn hàng đang chuẩn bị
    private By firstPrependingOrder = By.xpath("//table//tbody/tr[td[10][normalize-space()='Đang chuẩn bị']][1]");
    private By deliveryBtnInTable = By.xpath("//table//tbody/tr[td[10][normalize-space()='Đang chuẩn bị']][1]//button[contains(@class, 'text-blueConfirm')]");
    private By shipperDropdown = By.xpath("//span[contains(text(), 'Chọn người giao hàng')]");
    public void selectShipperByName(String shipperName) {
        wait.until(ExpectedConditions.elementToBeClickable(shipperDropdown)).click();
        By shipperOption = By.xpath("//div[contains(text(),'" + shipperName + "') and contains(@class, 'cursor-pointer')]");
        WebElement option = wait.until(ExpectedConditions.visibilityOfElementLocated(shipperOption));
        Actions actions = new Actions(driver);
        actions.moveToElement(option).click().perform();
    }
    public String firstPreperingOrder() {
        WebElement row = wait.until(ExpectedConditions.presenceOfElementLocated(firstPrependingOrder));
        assertNotNull(row, "Không tìm thấy đơn hàng nào đang ở trạng thái 'Đang chuẩn bị'");
        String orderCode = row.findElement(orderCodeCell).getText().trim();
        System.out.println("Mã đơn hàng được chọn: " + orderCode);
        row.findElement(deliveryBtnInTable).click();
        // Đợi popup hiện và bấm nút xác nhận
        wait.until(ExpectedConditions.visibilityOfElementLocated(confirmPopupTitle));
        selectShipperByName("Minh Tuấn");
        wait.until(ExpectedConditions.elementToBeClickable(confirmPopupButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(message));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Đợi trạng thái đơn hàng tương ứng được cập nhật lại
        By updatedStatusCell = By.xpath("//table//tbody/tr[td[2][normalize-space()='" + orderCode + "']]/td[10]");
        WebElement statusCell = new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(driver -> {
                    try {
                        WebElement cell = driver.findElement(updatedStatusCell);
                        String statusText = cell.getText().trim();
                        return statusText.isEmpty() ? null : cell;
                    } catch (Exception e) {
                        return null;
                    }
                });
        assertNotNull(statusCell, "Không tìm thấy trạng thái đơn hàng sau khi xác nhận");
        String updatedStatus = statusCell.getText().trim();
        System.out.println("Trạng thái đơn hàng sau xác nhận: " + updatedStatus);
        assertEquals(updatedStatus, "Đang giao", "Trạng thái đơn hàng không được cập nhật chính xác");
        return orderCode;
    }

    //hoàn thành đơn hàng
    private By firstDeliveryOrder = By.xpath("//table//tbody/tr[td[10][normalize-space()='Đang giao']][1]");
    private By completeBtnInTable = By.xpath("//table//tbody/tr[td[10][normalize-space()='Đang giao']][1]//button[contains(@class, 'text-emerald')]");
    public String completeOrder() {
        WebElement row = wait.until(ExpectedConditions.presenceOfElementLocated(firstDeliveryOrder));
        assertNotNull(row, "Không tìm thấy đơn hàng nào đang ở trạng thái 'Chờ xác nhận'");
        // Lấy mã đơn hàng từ cột 1 trong dòng đó
        String orderCode = row.findElement(orderCodeCell).getText().trim();
        System.out.println("Mã đơn hàng được chọn: " + orderCode);
        row.findElement(completeBtnInTable).click();
        // Đợi popup hiện và bấm nút xác nhận
        wait.until(ExpectedConditions.visibilityOfElementLocated(confirmPopupTitle));
        wait.until(ExpectedConditions.elementToBeClickable(confirmPopupButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(message));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Đợi trạng thái đơn hàng tương ứng được cập nhật lại
        By updatedStatusCell = By.xpath("//table//tbody/tr[td[2][normalize-space()='" + orderCode + "']]/td[10]");
        WebElement statusCell = new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(driver -> {
                    try {
                        WebElement cell = driver.findElement(updatedStatusCell);
                        String statusText = cell.getText().trim();
                        return statusText.isEmpty() ? null : cell;
                    } catch (Exception e) {
                        return null;
                    }
                });
        assertNotNull(statusCell, "Không tìm thấy trạng thái đơn hàng sau khi xác nhận");
        String updatedStatus = statusCell.getText().trim();
        System.out.println("Trạng thái đơn hàng sau xác nhận: " + updatedStatus);
        assertEquals(updatedStatus, "Đã giao", "Trạng thái đơn hàng không được cập nhật chính xác");
        return orderCode;
    }
}
