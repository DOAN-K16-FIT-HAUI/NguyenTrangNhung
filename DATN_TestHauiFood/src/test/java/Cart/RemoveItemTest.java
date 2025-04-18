package Cart;

import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import pages.LoginPage;
import utils.LoginBaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.ProductListPage;

public class RemoveItemTest extends LoginBaseTest {
    private LoginPage loginPage;
    private HomePage homePage;
    private ProductListPage productListPage;

    @BeforeMethod
    public void setUpTest() {
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        productListPage = new ProductListPage(driver);
    }

    @Test(description = "Kiểm thử xóa 1 sản phẩm khỏi giỏ hàng thành công")
    @AllureId("Cart-5")
    @Epic("Chức năng Quản lý giỏ hàng")
    @Feature("Xóa sản phẩm khỏi giỏ hàng")
    @Story("Người dùng xóa sản phẩm trong giỏ hàng khi đã đăng nhập")
    @Severity(SeverityLevel.CRITICAL)
    public void removeItemFromCart() {
        // 1. Đăng nhập
        loginPage.enterEmail("domai@gmai.com");
        loginPage.enterPassword("#Mai12345");
        loginPage.clickLoginButton();
        homePage.waitUntilHomePageIsLoaded();
        homePage.waitSuccessLoginDisappear();
        homePage.clickCartIcon();
        // 3. Kiểm tra có ít nhất 1 sản phẩm trong giỏ hàng
        int oldItemCount = productListPage.getCartItemCount();
        Assert.assertTrue(oldItemCount > 0, "Giỏ hàng không có sản phẩm để xóa");

        int oldTotal = productListPage.getCartTotalPriceAsInt();
        int firstItemPrice = productListPage.getFirstItemPrice();

        productListPage.removeFirstProduct();
        Assert.assertTrue(productListPage.isMessageDisplayed(), "Không hiển thị thông báo");
        String actualMessage = productListPage.getNotiMessage();
        Assert.assertEquals(actualMessage,"Thành công", "Thông báo không đúng như mong đợi");
        productListPage.waitMessageDisappear();
        //Kiểm tra số lượng và tổng tiền sau khi xóa
        int newItemCount = productListPage.getCartItemCount();
        Assert.assertEquals(newItemCount, oldItemCount - 1, "Số lượng sản phẩm trong giỏ hàng không giảm đúng");

        int newTotal = productListPage.getCartTotalPriceAsInt();
        Assert.assertEquals(newTotal, oldTotal - firstItemPrice, "Tổng tiền không giảm đúng sau khi xóa sản phẩm");
    }
}
