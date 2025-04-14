package Login;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import utils.LoginBaseTest;

public class LoginTest extends LoginBaseTest {
    private LoginPage loginPage;
    private HomePage homePage;

    @BeforeMethod
    public void setUpTest() {
        loginPage = new LoginPage(driver);
    }

    @Test(description = "Kiểm thử đăng nhập thành công và hiển thị thông báo")
    @AllureId("103")
    @Epic("Chức năng đăng nhập")
    @Feature("Đăng nhập")
    @Story("Người dùng đăng nhập thành công với thông tin hợp lệ và hiển thị toast")
    @Severity(SeverityLevel.CRITICAL)
    public void testLoginSuccess() {
        loginPage.enterEmail("trangnhung29072003@gmail.com");
        loginPage.enterPassword("#Nhung123");
        homePage = loginPage.clickLoginButton();

        String toastMessage = homePage.getSuccessLoginMessage();
        Assert.assertEquals(toastMessage.trim(), "Đăng nhập thành công", "Thông báo không đúng hoặc không hiển thị.");
    }
}
