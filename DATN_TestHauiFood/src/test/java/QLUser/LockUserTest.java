package QLUser;

import io.qameta.allure.*;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.ManagementOrderPage;
import pages.ManagementUserPage;
import utils.ManagementBaseTest;

public class LockUserTest extends ManagementBaseTest {
    private ManagementOrderPage managementPage;
    private ManagementUserPage userPage;
    private LoginPage loginPage;

    @BeforeMethod
    public void setUpTest() {
        managementPage = new ManagementOrderPage(driver);
        userPage = new ManagementUserPage(driver);
        loginPage = new LoginPage(driver);
    }
    @Test(description = "Kiểm thử admin khóa người dùng")
    @AllureId("Admin-14")
    @Epic("Chức năng Quản lý người dùng")
    @Feature("Khóa người dùng")
    @Story("Admin khóa người dùng thành công")
    @Severity(SeverityLevel.CRITICAL)
    public void lockUserTest() throws InterruptedException {
        managementPage.enterEmail("haui@gmail.com");
        managementPage.enterPassword("#Haui123");
        managementPage.clickLoginButton();
        userPage.clickUserManagementMenu();
        Thread.sleep(1000);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 500);");
        userPage.clickLockBtn(3);
        userPage.lockUser();
        // Chuyển sang giao diện người dùng
        driver.get("https://hauifood.vn/auth/login");
        loginPage.enterEmail("syvu@gmail.com");
        loginPage.enterPassword("#Sy12345");
        loginPage.clickLoginButton();
        String err = loginPage.getErrLock();
        Assert.assertEquals(err, "Tài khoản đã bị khóa vui lòng liên hệ Quản trị viên", "Thông báo hiển thị không đúng mong đợi");

    }
}
