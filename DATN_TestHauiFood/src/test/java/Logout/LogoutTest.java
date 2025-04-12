package Logout;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import utils.LoginBaseTest;

public class LogoutTest extends LoginBaseTest {

    @Test(description = "Kiểm thử đăng xuất thành công")
    @AllureId("115")
    @Epic("Chức năng đăng xuất")
    @Feature("Đăng xuất")
    @Story("Người dùng đăng xuất thành công khỏi phiên đăng nhập")
    @Severity(SeverityLevel.NORMAL)
    public void TC_Logout_Success() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail("trangnhung29072003@gmail.com");
        loginPage.enterPassword("#Nhung09061983");
        loginPage.clickLoginButton();

        HomePage homePage = new HomePage(driver);

        Assert.assertEquals(homePage.getSuccessLoginMessage(), "Đăng nhập thành công");

        homePage.clickIconUser();
        homePage.clickLogout();

        //Verify thông báo "Đăng xuất thành công"
        Assert.assertEquals(homePage.getSuccessLogoutMessage(),"Đăng xuất thành công");
    }
}
