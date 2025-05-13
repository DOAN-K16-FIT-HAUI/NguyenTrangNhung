package UpdateInfo;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.ProductListPage;
import pages.ProfilePage;
import utils.LoginBaseTest;

public class ValidatePhoneTest extends LoginBaseTest {
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
    @Test(description = "Kiểm tra thông báo lỗi khi người dùng nhập số điện thoại không hợp lệ")
    @AllureId("UpdateInfo-4")
    @Epic("Chức năng Cập nhật thông tin cá nhân")
    @Feature("Cập nhật thông tin tài khoản")
    @Story("Người dùng nhập số điện thoại không hợp lệ")
    @Severity(SeverityLevel.NORMAL)
    public void testValidateMessage() throws InterruptedException {
        loginPage.enterEmail("haui@gmail.com");
        loginPage.enterPassword("#Haui123");
        loginPage.clickLoginButton();
        homePage.clickCloseToast();

        homePage.clickIconUser();
        homePage.clickProfile();
        Thread.sleep(2000);
        profilePage.clickEditBtn();
        profilePage.updateProfile("Nguyễn Trang Nhung", "0", "Nữ");
        profilePage.clickUpdateBtn();
        Assert.assertTrue(productListPage.isMessageDisplayed(), "Không hiển thị thông báo lỗi");
        String actualMessage = productListPage.getNotiMessage();
        Assert.assertEquals(actualMessage,"Vui lòng kiểm tra lại thông tin và nhập đúng định dạng.", "Thông báo hiển thị không đúng mong đợi");
    }
}
