package Login;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.LoginBaseTest;

public class LoginWrongPasswordTest extends LoginBaseTest {
    private LoginPage loginPage;

    @BeforeMethod
    public void setUpTest() {
        loginPage = new LoginPage(driver);
    }

    @Test(description = "Kiểm thử đăng nhập thất bại khi nhập sai Password")
    @AllureId("105")
    @Epic("Chức năng đăng nhập")
    @Feature("Đăng nhập")
    @Story("Người dùng nhập đúng email nhưng sai password")
    @Severity(SeverityLevel.CRITICAL)
    public void testLoginWrongPassword() {
        loginPage.enterEmail("trangnhung29072003@gmail.com");
        loginPage.enterPassword("#Nhung12");
        loginPage.clickLoginButton();

        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Không tìm thấy thông báo lỗi!");

        captureScreenshot(); // Screenshot manual

        String actualMessage = loginPage.getErrorMessageText();
        String expectedMessage = "Tài khoản hoặc mật khẩu không chính xác";
        Assert.assertEquals(actualMessage, expectedMessage, "Nội dung thông báo lỗi không đúng!");
    }
}
