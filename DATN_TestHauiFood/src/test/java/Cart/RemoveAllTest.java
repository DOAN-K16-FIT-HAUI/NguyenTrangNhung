package Cart;

import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import pages.LoginPage;
import utils.LoginBaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.ProductListPage;

public class RemoveAllTest extends LoginBaseTest {
    private LoginPage loginPage;
    private HomePage homePage;
    private ProductListPage productListPage;

    @BeforeMethod
    public void setUpTest() {
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        productListPage = new ProductListPage(driver);
    }

    @Test(description = "Kiểm thử xóa tất cả sản phẩm khỏi giỏ hàng thành công")
    @AllureId("Cart-6")
    @Epic("Chức năng Quản lý giỏ hàng")
    @Feature("Xóa sản phẩm khỏi giỏ hàng")
    @Story("Người dùng xóa tất cả sản phẩm trong giỏ hàng khi đã đăng nhập")
    @Severity(SeverityLevel.CRITICAL)
    public void removeItemFromCart() {
        loginPage.enterEmail("trangnhung29072003@gmail.com");
        loginPage.enterPassword("#Nhung123");
        loginPage.clickLoginButton();
        homePage.waitUntilHomePageIsLoaded();
        homePage.waitSuccessLoginDisappear();
        homePage.clickCartIcon();
        //Kiểm tra có ít nhất 1 sản phẩm trong giỏ hàng
        int oldItemCount = productListPage.getCartItemCount();
        Assert.assertTrue(oldItemCount > 0, "Giỏ hàng không có sản phẩm để xóa");

        productListPage.clickRemoveAll();
        Assert.assertTrue(productListPage.isMessageDisplayed(), "Không hiển thị thông báo");
        String actualMessage = productListPage.getNotiMessage();
        Assert.assertEquals(actualMessage,"Thành công", "Thông báo không đúng như mong đợi");
        productListPage.waitMessageDisappear();
        //Kiểm tra lại giỏ hàng sau khi xóa toàn bộ giỏ hàng
        homePage.clickCartIcon();
        Assert.assertTrue(homePage.isCartDrawerDisplayed(), "Drawer giỏ hàng không hiển thị!");
        Assert.assertTrue(homePage.isEmptyCartTextDisplayed(), "Không hiển thị thông báo 'Giỏ hàng trống'!");
    }
}
