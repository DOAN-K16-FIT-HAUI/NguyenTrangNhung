package Cart;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import utils.LoginBaseTest;

public class CartEmptyViewTest extends LoginBaseTest {
    private LoginPage loginPage;
    private HomePage homePage;

    @BeforeMethod
    public void setUpTest() {
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
    }

    @Test(description = "Kiểm thử xem giỏ hàng khi chưa có sản phẩm nào trong giỏ hàng")
    @AllureId("Cart-1")
    @Epic("Chức năng Quản lý giỏ hàng")
    @Feature("Xem giỏ hàng")
    @Story("Người dùng xem chi tiết giỏ hàng đang trống thành công")
    @Severity(SeverityLevel.NORMAL)
    public void viewEmptyCartSuccess() {
        loginPage.enterEmail("haui@gmail.com");
        loginPage.enterPassword("#Haui123");
        loginPage.clickLoginButton();

        homePage.waitUntilHomePageIsLoaded();
        homePage.waitSuccessLoginDisappear();
        homePage.clickCartIcon();

        Assert.assertTrue(homePage.isCartDrawerDisplayed(), "Drawer giỏ hàng không hiển thị!");
        Assert.assertTrue(homePage.isEmptyCartTextDisplayed(), "Không hiển thị thông báo 'Giỏ hàng trống'!");
    }
}
