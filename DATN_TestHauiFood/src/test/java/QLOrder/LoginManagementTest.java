package QLOrder;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ManagementOrderPage;
import utils.ManagementBaseTest;

public class LoginManagementTest extends ManagementBaseTest {
    private ManagementOrderPage managementPage;

    @BeforeMethod
    public void setUpTest() {
        managementPage = new ManagementOrderPage(driver);
    }

    @Test(description = "Kiểm thử admin đăng nhập thành công và hiển thị thông báo")
    @AllureId("Admin-01")
    @Epic("Chức năng đăng nhập - admin")
    @Feature("Đăng nhập - admin")
    @Story("Admin đăng nhập thành công với thông tin hợp lệ và hiển thị toast")
    @Severity(SeverityLevel.CRITICAL)
    public void testLoginSuccess() {
        managementPage.enterEmail("haui@gmail.com");
        managementPage.enterPassword("#Haui123");
        managementPage.clickLoginButton();
        String toastMessage = managementPage.getErrorMessage();
        Assert.assertEquals(toastMessage.trim(), "Đăng nhập thành công", "Thông báo không đúng hoặc không hiển thị.");
    }
}
