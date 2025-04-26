package ViewAccount;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.ProfilePage;
import utils.LoginBaseTest;

public class ViewAccInfoTest extends LoginBaseTest{
    private LoginPage loginPage;
    private HomePage homePage;
    private ProfilePage profilePage;
    @BeforeMethod
    public void setUpTest() {
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        profilePage = new ProfilePage(driver);
    }
    @Test(description = "Kiểm tra người dùng thực hiện xem thông tin cá nhân thành công")
    @AllureId("ViewAccInfo-1")
    @Epic("Chức năng Xem thông tin tài khoản")
    @Feature("Xem thông tin tài khoản")
    @Story("Người dùng xem thông tin tài khoản thành công")
    @Severity(SeverityLevel.NORMAL)
    public void viewAccountInfo() throws InterruptedException {
        loginPage.enterEmail("trangnhung29072003@gmail.com");
        loginPage.enterPassword("#Nhung123");
        loginPage.clickLoginButton();
        homePage.waitUntilHomePageIsLoaded();
        homePage.clickCloseToast();

        homePage.clickIconUser();
        homePage.clickProfile();
        Thread.sleep(2000);
        Assert.assertEquals(profilePage.getFullName(), "Nguyễn Trang Nhung", "Full name not matched");
        Assert.assertEquals(profilePage.getEmail(), "trangnhung29072003@gmail.com", "Email not matched");
        Assert.assertEquals(profilePage.getPhone(), "0366285718", "Phone not matched");
        Assert.assertEquals(profilePage.getGender(), "Nữ", "Gender not matched");
    }
}
