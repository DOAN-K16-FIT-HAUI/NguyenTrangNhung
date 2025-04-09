package Login;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.LoginBaseTest;

public class LoginWrongEmailTest extends LoginBaseTest {
    private LoginPage loginPage;

    @BeforeMethod
    public void setUpTest() {
        loginPage = new LoginPage(driver);
    }

    @Test(description = "Kiểm thử đăng nhập thất bại khi nhập sai Email")
    @AllureId("102")
    @Epic("Chức năng đăng nhập")
    @Feature("Đăng nhập")
    @Story("Người dùng nhập sai email nhưng đúng mật khẩu")
    @Severity(SeverityLevel.CRITICAL)
    public void testLoginWrongEmail() {
        loginPage.enterEmail("trangnhung2907@gmail.com");
        loginPage.enterPassword("#Nhung09061983");
        loginPage.clickLoginButton();

        // Kiểm tra hiển thị thông báo lỗi
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Không tìm thấy thông báo lỗi!");

        loginPage.attachScreenshot();

        // Kiểm tra nội dung thông báo lỗi
        String actualMessage = loginPage.getErrorMessageText();
        String expectedMessage = "Tài khoản hoặc mật khẩu không chính xác";
        Assert.assertEquals(actualMessage, expectedMessage, "Nội dung thông báo lỗi không đúng!");
    }
}
