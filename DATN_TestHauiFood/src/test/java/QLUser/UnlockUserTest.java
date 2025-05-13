package QLUser;

import io.qameta.allure.*;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.ManagementOrderPage;
import pages.ManagementUserPage;
import utils.ManagementBaseTest;

public class UnlockUserTest extends ManagementBaseTest {
    private ManagementOrderPage managementPage;
    private ManagementUserPage userPage;
    private LoginPage loginPage;
    private HomePage homePage;

    @BeforeMethod
    public void setUpTest() {
        managementPage = new ManagementOrderPage(driver);
        userPage = new ManagementUserPage(driver);
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
    }
    @Test(description = "Kiểm thử admin mở khóa người dùng")
    @AllureId("Admin-15")
    @Epic("Chức năng Quản lý người dùng")
    @Feature("Khóa người dùng")
    @Story("Admin mở khóa người dùng thành công")
    @Severity(SeverityLevel.CRITICAL)
    public void unlockUserTest() throws InterruptedException {
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
        String toastMessage = homePage.getSuccessLoginMessage();
        Assert.assertEquals(toastMessage.trim(), "Đăng nhập thành công", "Thông báo không đúng hoặc không hiển thị.");
    }
}
