package Cart;

import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import pages.LoginPage;
import utils.LoginBaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.ProductListPage;

public class NoChangeQuantityTest extends LoginBaseTest {
    private LoginPage loginPage;
    private HomePage homePage;
    private ProductListPage productListPage;

    @BeforeMethod
    public void setUpTest() {
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        productListPage = new ProductListPage(driver);
    }

    @Test(description = "Kiểm thử cập nhật số lượng nhưng không thay đổi số lượng hiện tại")
    @AllureId("Cart-8")
    @Epic("Chức năng Quản lý giỏ hàng")
    @Feature("Cập nhật số lượng sản phẩm")
    @Story("Người dùng cập nhật số lượng nhưng không thay đổi giá trị hiện tại")
    @Severity(SeverityLevel.NORMAL)
    public void updateQuantityWithoutChange() {
        loginPage.enterEmail("hado@gmail.com");
        loginPage.enterPassword("#Nhung123");
        loginPage.clickLoginButton();
        homePage.waitUntilHomePageIsLoaded();
        homePage.waitSuccessLoginDisappear();

        homePage.clickCartIcon();
        Assert.assertTrue(productListPage.getCartItemCount() > 0, "Giỏ hàng không có sản phẩm");

        productListPage.clickQuantityBtn();
        Assert.assertTrue(productListPage.isUpdateQuantityDrawer(), "Drawer cập nhật không hiển thị");
        productListPage.clickUpdateQuantityBtn();

        // Kiểm tra hiển thị thông báo "Số lượng không thay đổi"
        Assert.assertTrue(productListPage.isMessageDisplayed(), "Không hiển thị thông báo 'Số lượng không thay đổi'");
        String actualMessage = productListPage.getNotiMessage();
        Assert.assertEquals(actualMessage,"Số lượng sản phẩm không thay đổi", "Thông báo không đúng như mong đợi");
    }
}
