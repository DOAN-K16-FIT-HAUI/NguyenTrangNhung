package ChangePassword;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.ProductListPage;
import pages.ProfilePage;
import utils.LoginBaseTest;

public class ChangePWSuccessTest extends LoginBaseTest {
    private LoginPage loginPage;
    private HomePage homePage;
    private ProfilePage profilePage;
    private ProductListPage productListPage;
    @BeforeMethod
    public void setUpTest() {
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        productListPage = new ProductListPage(driver);
        profilePage = new ProfilePage(driver);
    }
    @Test(description = "Kiểm tra người dùng thực hiện đổi mật khẩu thành công")
    @AllureId("ChangePassword-1")
    @Epic("Chức năng Đổi mật khẩu")
    @Feature("Đổi mật khẩu")
    @Story("Người dùng đổi mật khẩu thành công")
    @Severity(SeverityLevel.CRITICAL)
    public void testUpdateProfileSuccess() throws InterruptedException {
        loginPage.enterEmail("haui@gmail.com");
        loginPage.enterPassword("#Haui123");
        loginPage.clickLoginButton();
        homePage.clickCloseToast();

        homePage.clickIconUser();
        homePage.clickProfile();
        Thread.sleep(2000);
        profilePage.clickChangePW();
        profilePage.changePasswordInput("#Haui123", "#Nhung123", "#Nhung123");
        profilePage.clickUpdateBtn();
        Assert.assertTrue(productListPage.isMessageDisplayed(), "Không hiển thị thông báo cập nhật thành công");
        String actualMessage = productListPage.getNotiMessage();
        Assert.assertEquals(actualMessage,"Đổi mật khẩu thành công", "Thông báo hiển thị không đúng mong đợi");
    }
}
