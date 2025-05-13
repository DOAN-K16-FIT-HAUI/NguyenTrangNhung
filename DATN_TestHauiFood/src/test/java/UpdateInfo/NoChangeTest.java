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

public class NoChangeTest extends LoginBaseTest {
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
    @Test(description = "Kiểm tra thông báo khi người dùng cập nhật nhưng không có thông tin nào thay đổi")
    @AllureId("UpdateInfo-3")
    @Epic("Chức năng Cập nhật thông tin cá nhân")
    @Feature("Cập nhật thông tin tài khoản")
    @Story("Người dùng cập nhật thông tin cá nhân nhưng không có thông tin nào thay đổi")
    @Severity(SeverityLevel.NORMAL)
    public void noChangeMessage() throws InterruptedException {
        loginPage.enterEmail("haui@gmail.com");
        loginPage.enterPassword("#Haui123");
        loginPage.clickLoginButton();
        homePage.clickCloseToast();

        homePage.clickIconUser();
        homePage.clickProfile();
        Thread.sleep(2000);
        profilePage.clickEditBtn();
        profilePage.clickUpdateBtn();
        Assert.assertTrue(productListPage.isMessageDisplayed(), "Không hiển thị thông báo");
        String actualMessage = productListPage.getNotiMessage();
        Assert.assertEquals(actualMessage,"Không có thay đổi nào được thực hiện", "Thông báo hiển thị không đúng mong đợi");
    }
}
