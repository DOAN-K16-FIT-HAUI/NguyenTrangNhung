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

public class ValidateNewPWTest extends LoginBaseTest {
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
    @Test(description = "Kiểm tra người dùng thực hiện đổi mật khẩu thất bại khi nhập mật khẩu mới không đúng định dạng")
    @AllureId("ChangePassword-4")
    @Epic("Chức năng Đổi mật khẩu")
    @Feature("Đổi mật khẩu")
    @Story("Người dùng đổi mật khẩu thất bại khi nhập mật khẩu mới không đúng định dạng")
    @Severity(SeverityLevel.CRITICAL)
    public void testWrongConfirmPW() throws InterruptedException {
        loginPage.enterEmail("haui@gmail.com");
        loginPage.enterPassword("#Haui123");
        loginPage.clickLoginButton();
        homePage.clickCloseToast();

        homePage.clickIconUser();
        homePage.clickProfile();
        Thread.sleep(2000);
        profilePage.clickChangePW();
        profilePage.changePasswordInput("#Haui123", "Nhung", "Nhung");
        String actErrorMessage = profilePage.getErrorMessageChangePW();
        Assert.assertEquals(actErrorMessage,"Mật khẩu phải có ít nhất 8 ký tự và ít nhất 1 ký tự đặc biệt.", "Thông báo hiển thị không đúng mong đợi");

        profilePage.clickUpdateBtn();
        Assert.assertTrue(productListPage.isMessageDisplayed(), "Không hiển thị thông báo cập nhật thành công");
        String actualMessage = productListPage.getNotiMessage();
        Assert.assertEquals(actualMessage,"Vui lòng kiểm tra lại thông tin và nhập đúng định dạng.", "Thông báo hiển thị không đúng mong đợi");
    }
}
