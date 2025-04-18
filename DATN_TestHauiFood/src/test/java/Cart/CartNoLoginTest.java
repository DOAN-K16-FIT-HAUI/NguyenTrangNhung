package Cart;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import utils.HomePageBaseTest;

public class CartNoLoginTest extends HomePageBaseTest {
    private HomePage homePage;

    @BeforeMethod
    public void setUpTest() {
        homePage = new HomePage(driver);
    }

    @Test(description = "Kiểm thử xem giỏ hàng khi chưa đăng nhập")
    @AllureId("Cart-2")
    @Epic("Chức năng Quản lý giỏ hàng")
    @Feature("Xem giỏ hàng")
    @Story("Người dùng xem giỏ hàng khi chưa đăng nhập")
    @Severity(SeverityLevel.CRITICAL)
    public void viewEmptyCart() {

        homePage.clickCartIcon();

        Assert.assertTrue(homePage.isCartDrawerDisplayed(), "Drawer giỏ hàng không hiển thị!");
        Assert.assertTrue(homePage.isEmptyCartTextDisplayed(), "Không hiển thị thông báo 'Chưa đăng nhập'!");
    }
}
