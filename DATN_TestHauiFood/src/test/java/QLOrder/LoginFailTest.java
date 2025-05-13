package QLOrder;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ManagementOrderPage;
import utils.ManagementBaseTest;

public class LoginFailTest extends ManagementBaseTest {
    private ManagementOrderPage managementPage;

    @BeforeMethod
    public void setUpTest() {
        managementPage = new ManagementOrderPage(driver);
    }

    @Test(description = "Kiểm thử admin đăng nhập thất bại khi nhập tài khoản không phải của admin")
    @AllureId("Admin-02")
    @Epic("Chức năng đăng nhập - admin")
    @Feature("Đăng nhập - admin")
    @Story("Admin đăng nhập thất bại với thông tin tài khoản không hợp lệ")
    @Severity(SeverityLevel.CRITICAL)
    public void testLoginFail() {
        managementPage.enterEmail("domai@gmail.com");
        managementPage.enterPassword("#Mai1234");
        managementPage.clickLoginButton();

        String toastMessage = managementPage.getErrorMessage();
        Assert.assertEquals(toastMessage.trim(), "Email or password is invalid", "Thông báo không đúng hoặc không hiển thị.");
    }
}
