package Cart;

import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ProductListPage;
import utils.ProductListBaseTest;

public class ErrorAddProductTest extends ProductListBaseTest {

    private ProductListPage productListPage;

    @BeforeMethod
    public void setUpTest() {
        productListPage = new ProductListPage(driver);
    }

    @Test(description = "Kiểm thử thêm sản phẩm vào giỏ hàng thất bại khi chưa đăng nhập")
    @AllureId("Cart-4")
    @Epic("Chức năng Quản lý giỏ hàng")
    @Feature("Thêm sản phẩm vào giỏ hàng")
    @Story("Người dùng thêm sản phẩm vào giỏ hàng thất bại khi chưa đăng nhập")
    @Severity(SeverityLevel.CRITICAL)
    public void addProductToCartError() {
        // Click vào sản phẩm đầu tiên
        productListPage.clickFirstProduct();
        Assert.assertTrue(productListPage.isProductDrawerVisible(), "Drawer sản phẩm không hiển thị");

        productListPage.clickAddBtn();

        // Kiểm tra thông báo lỗi
        Assert.assertTrue(productListPage.isMessageDisplayed(), "Không hiển thị thông báo");
        String actualMessage = productListPage.getNotiMessage();
        Assert.assertEquals(actualMessage,"Vui lòng đăng nhập để thêm vào giỏ hàng", "Thông báo không đúng như mong đợi");
    }
}
