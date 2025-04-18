package Cart;

import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import pages.LoginPage;
import utils.LoginBaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.ProductListPage;

public class UpdateQuantityTest extends LoginBaseTest {
    private LoginPage loginPage;
    private HomePage homePage;
    private ProductListPage productListPage;

    @BeforeMethod
    public void setUpTest() {
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        productListPage = new ProductListPage(driver);
    }

    @Test(description = "Kiểm thử cập nhật số lượng sản phẩm trong giỏ hàng thành công")
    @AllureId("Cart-7")
    @Epic("Chức năng Quản lý giỏ hàng")
    @Feature("Cập nhật số lượng sản phẩm")
    @Story("Người dùng cập nhật số lượng sản phẩm trong giỏ hàng khi đã đăng nhập")
    @Severity(SeverityLevel.CRITICAL)
    public void updateQuantityInCart() {
        loginPage.enterEmail("hado@gmail.com");
        loginPage.enterPassword("#Nhung123");
        loginPage.clickLoginButton();
        homePage.waitUntilHomePageIsLoaded();
        homePage.waitSuccessLoginDisappear();

        int oldQuantity = productListPage.getCartQuantity();
        homePage.clickCartIcon();
        int oldItemCount = productListPage.getCartItemCount();
        Assert.assertTrue(oldItemCount > 0, "Giỏ hàng không có sản phẩm để cập nhật");

        int unitPrice = productListPage.getFirstItemPrice();
        int oldTotal = productListPage.getCartTotalPriceAsInt();

        //Click vào sản phẩm để hiển thị drawer cập nhật số lượng
        productListPage.clickQuantityBtn();
        Assert.assertTrue(productListPage.isUpdateQuantityDrawer(), "Drawer cập nhật không hiển thị");

        // 5. Tăng số lượng thêm 1
        productListPage.increaseProductQuantity();
        productListPage.clickUpdateQuantityBtn();
        Assert.assertTrue(productListPage.isMessageDisplayed(), "Không hiển thị thông báo");
        String actualMessage = productListPage.getNotiMessage();
        Assert.assertEquals(actualMessage,"Thành công", "Thông báo không đúng như mong đợi");
        productListPage.waitMessageDisappear();

        // 6. Kiểm tra lại số lượng và tổng tiền
        productListPage.clickCloseCart();
        int newQuantity = productListPage.getCartQuantity();
        Assert.assertEquals(newQuantity, oldQuantity + 1, "Số lượng không cập nhật đúng");

        int newTotal = productListPage.getCartTotalPriceAsInt();
        Assert.assertEquals(newTotal, oldTotal + unitPrice, "Tổng tiền không cập nhật đúng");
    }
}
