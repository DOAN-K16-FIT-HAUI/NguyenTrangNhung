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

    @Test(description = "Kiểm thử đăng nhập thành công")
    @AllureId("101")
    @Epic("Chức năng đăng nhập")
    @Feature("Đăng nhập")
    @Story("Người dùng đăng nhập thành công với thông tin hợp lệ")
    @Severity(SeverityLevel.CRITICAL)

    public void testLoginSuccess() {
        loginPage.enterEmail("trangnhung29072003@gmail.com");
        loginPage.enterPassword("#Nhung09061983");
        homePage = loginPage.clickLoginButton();

        // Đợi trang chủ tải xong
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Assert.assertTrue(homePage.isHomePageDisplayed(), "Đăng nhập không thành công, không chuyển hướng về trang chủ!");
    }

}
