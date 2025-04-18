package ProductDetails;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ProductListPage;
import utils.ProductListBaseTest;

public class ProductDetailsTest extends ProductListBaseTest {

    @Test(description = "User xem chi tiết sản phẩm thành công")
    @AllureId("113")
    @Epic("Chức năng Xem chi tiết sản phẩm")
    @Feature("Xem chi tiết sản phẩm")
    @Severity(SeverityLevel.NORMAL)
    @Story("Người dùng có thể xem chi tiết sản phẩm thành công")
    public void testViewProductDetailDrawer() {
        ProductListPage productListPage = new ProductListPage(driver);

        int productCount = productListPage.getProductCount();
        Assert.assertTrue(productCount > 0, "Không có sản phẩm nào trong danh sách!");
        productListPage.clickFirstProduct();

        Assert.assertTrue(productListPage.isProductDrawerVisible(), "Drawer chi tiết sản phẩm không hiển thị!");
        String productName = productListPage.getDrawerProductName();
        String productPrice = productListPage.getDrawerProductPrice();
        boolean isAddToCartVisible = productListPage.isAddToCartButtonVisible();

        //Assert thông tin không rỗng và nút hiển thị
        Assert.assertFalse(productName.isEmpty(), "Tên sản phẩm bị rỗng!");
        Assert.assertFalse(productPrice.isEmpty(), "Giá sản phẩm bị rỗng!");
        Assert.assertTrue(isAddToCartVisible, "Nút 'Thêm vào giỏ hàng' không hiển thị!");
    }
}
