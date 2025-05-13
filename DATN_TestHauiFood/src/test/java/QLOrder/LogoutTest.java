package QLOrder;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ManagementOrderPage;
import utils.ManagementBaseTest;

public class LogoutTest extends ManagementBaseTest {
    private ManagementOrderPage managementPage;

    @BeforeMethod
    public void setUpTest() {
        managementPage = new ManagementOrderPage(driver);
    }

    @Test(description = "Kiểm thử admin đăng xuất thành công khỏi phiên đăng nhập")
    @AllureId("Admin-03")
    @Epic("Chức năng đăng xuất - admin")
    @Feature("Đăng xuất - admin")
    @Story("Admin đăng xuất thành công khỏi phiên đăng nhập")
    @Severity(SeverityLevel.CRITICAL)
    public void testLogout() throws InterruptedException {
        managementPage.enterEmail("haui@gmail.com");
        managementPage.enterPassword("#Haui123");
        managementPage.clickLoginButton();
        Thread.sleep(1000);
        managementPage.clickAvt();
        managementPage.clickLogout();
        String toastMessage = managementPage.getErrorMessage();
        Assert.assertEquals(toastMessage.trim(), "Bạn đã đăng xuất thành công.", "Thông báo không đúng hoặc không hiển thị.");
    }
}
