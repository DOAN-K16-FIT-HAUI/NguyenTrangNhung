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

public class UpdateSuccessTest extends LoginBaseTest {
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
    @Test(description = "Kiểm tra người dùng thực hiện cập nhật thông tin cá nhân thành công")
    @AllureId("UpdateInfo-1")
    @Epic("Chức năng Cập nhật thông tin cá nhân")
    @Feature("Cập nhật thông tin tài khoản")
    @Story("Người dùng cập nhật thông tin cá nhân thành công")
    @Severity(SeverityLevel.NORMAL)
    public void testUpdateProfileSuccess() throws InterruptedException {
        loginPage.enterEmail("haui@gmail.com");
        loginPage.enterPassword("#Haui123");
        loginPage.clickLoginButton();
        homePage.clickCloseToast();

        homePage.clickIconUser();
        homePage.clickProfile();
        Thread.sleep(2000);
        profilePage.clickEditBtn();
        profilePage.updateProfile("Nguyễn Trang Nhung", "0366285718", "Nữ");
        profilePage.clickUpdateBtn();
        Assert.assertTrue(productListPage.isMessageDisplayed(), "Không hiển thị thông báo cập nhật thành công");
        String actualMessage = productListPage.getNotiMessage();
        Assert.assertEquals(actualMessage,"Cập nhật thành công.", "Thông báo hiển thị không đúng mong đợi");
    }
}
