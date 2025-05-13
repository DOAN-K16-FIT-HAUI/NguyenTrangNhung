package QLOrder;

import io.qameta.allure.*;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.ManagementOrderPage;
import pages.ProfilePage;
import utils.ManagementBaseTest;

public class PreperingOrderTest extends ManagementBaseTest {
    private ManagementOrderPage managementPage;
    private LoginPage loginPage;
    private HomePage homePage;
    private ProfilePage profilePage;
    @BeforeMethod
    public void setUpTest() {
        managementPage = new ManagementOrderPage(driver);
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        profilePage = new ProfilePage(driver);
    }

    @Test(description = "Kiểm thử admin click giao hàng cho đơn ở trạng thái đang chuẩn bị")
    @AllureId("Admin-08")
    @Epic("Chức năng Quản lý đơn hàng")
    @Feature("Giao đơn hàng")
    @Story("Admin click giao hàng cho đơn ở trạng thái đang chuẩn bị")
    @Severity(SeverityLevel.CRITICAL)
    public void confirmOrder() throws InterruptedException {
        managementPage.enterEmail("haui@gmail.com");
        managementPage.enterPassword("#Haui123");
        managementPage.clickLoginButton();
        managementPage.clickOrderManagementMenu();
        Thread.sleep(1000);
        managementPage.orderTableIsDisplayed();
        managementPage.orderTableHasData();
        String confirmedOrderId = managementPage.firstPreperingOrder();
        // Chuyển sang giao diện người dùng
        driver.get("https://hauifood.vn/auth/login");
        loginPage.enterEmail("trangnhung29072003@gmail.com");
        loginPage.enterPassword("#Nhung123");
        loginPage.clickLoginButton();
        homePage.clickCloseToast();
        homePage.clickIconUser();
        homePage.clickProfile();
        Thread.sleep(1000);
        profilePage.clickViewOrder();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, -500);");
        profilePage.clickOrderTabByIndex(4);
        Thread.sleep(1000);
        // Kiểm tra đơn hàng có tồn tại
        boolean isFound = profilePage.isOrderIdVisibleInTab("Đang giao", confirmedOrderId);
        Assert.assertTrue(isFound, "Đơn hàng " + confirmedOrderId + " không được cập nhật trạng thái đúng từ phía user!");
    }
}
