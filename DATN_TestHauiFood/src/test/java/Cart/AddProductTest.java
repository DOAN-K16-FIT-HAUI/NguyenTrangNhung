package Cart;

import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import pages.LoginPage;
import utils.LoginBaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.ProductListPage;

public class AddProductTest extends LoginBaseTest {
    private LoginPage loginPage;
    private HomePage homePage;
    private ProductListPage productListPage;

    @BeforeMethod
    public void setUpTest() {
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        productListPage = new ProductListPage(driver);
    }

    @Test(description = "Kiểm thử thêm sản phẩm vào giỏ hàng thành công")
    @AllureId("Cart-3")
    @Epic("Chức năng Quản lý giỏ hàng")
    @Feature("Thêm sản phẩm vào giỏ hàng")
    @Story("Người dùng thêm sản phẩm vào giỏ hàng thành công")
    @Severity(SeverityLevel.CRITICAL)
    public void addProductToCartSuccess() {
        loginPage.enterEmail("kimngan@gmail.com");
        loginPage.enterPassword("#Ngan123");
        loginPage.clickLoginButton();

        homePage.waitUntilHomePageIsLoaded();
        homePage.waitSuccessLoginDisappear();

        driver.get("https://hauifood.vn/products");
        // Click vào sản phẩm đầu tiên
        productListPage.clickFirstProduct();
        Assert.assertTrue(productListPage.isProductDrawerVisible(), "Drawer sản phẩm không hiển thị");

        String expectedProductName = productListPage.getDrawerProductName();
        String expectedProductPrice = productListPage.getDrawerProductPrice();
        productListPage.clickAddBtn();

        // Kiểm tra thông báo thành công
        Assert.assertTrue(productListPage.isMessageDisplayed(), "Không hiển thị thông báo");
        String actualMessage = productListPage.getNotiMessage();
        Assert.assertEquals(actualMessage,"Thành công", "Thông báo không đúng như mong đợi");
        productListPage.waitMessageDisappear();

        homePage.clickCartIcon();

        // Kiểm tra số lượng và tổng tiền
        int quantity = productListPage.getCartQuantity();
        String totalPrice = productListPage.getCartTotalPrice();
        Assert.assertEquals(quantity, 1, "Số lượng trong biểu tượng giỏ hàng không đúng");
        Assert.assertEquals(
                extractNumericPrice(totalPrice),
                extractNumericPrice(expectedProductPrice),
                "Tổng tiền trong biểu tượng giỏ hàng không đúng"
        );

        int cartItemCount = productListPage.getCartItemCount();
        Assert.assertTrue(cartItemCount > 0, "Biểu tượng giỏ hàng không hiển thị số lượng");

        String actualProductName = productListPage.getCartProductName();
        String actualProductPrice = productListPage.getCartProductPrice();
        Assert.assertEquals(actualProductName, expectedProductName, "Tên sản phẩm không khớp");
        Assert.assertEquals(
                extractNumericPrice(actualProductPrice),
                extractNumericPrice(expectedProductPrice),
                "Giá sản phẩm không khớp"
        );
    }

    public String extractNumericPrice(String price) {
        return price.replaceAll("[^0-9.]", ""); // chỉ giữ lại số và dấu chấm
    }
}
