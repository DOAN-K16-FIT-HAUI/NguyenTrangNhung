package Cart;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.ProductListPage;
import utils.LoginBaseTest;

public class AddProductOverLimitTest extends LoginBaseTest {
    private LoginPage loginPage;
    private HomePage homePage;
    private ProductListPage productListPage;

    @BeforeMethod
    public void setUpTest() {
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        productListPage = new ProductListPage(driver);
    }

    @Test(description = "User thêm sản phẩm thất bại khi chọn số lượng vượt quá giới hạn")
    @AllureId("Cart-9")
    @Epic("Chức năng Quản lý giỏ hàng")
    @Feature("Thêm sản phẩm vào giỏ hàng")
    @Story("Người dùng thêm sản phẩm thất bại khi chọn số lượng vượt quá 100")
    @Severity(SeverityLevel.CRITICAL)
    public void addProductOverLimit() {
        loginPage.enterEmail("trangnhung29072003@gmail.com");
        loginPage.enterPassword("#Nhung123");
        loginPage.clickLoginButton();

        homePage.waitUntilHomePageIsLoaded();
        homePage.waitSuccessLoginDisappear();
        driver.get("https://hauifood.vn/products");
        productListPage.clickFirstProduct();
        Assert.assertTrue(productListPage.isProductDrawerVisible(), "Drawer sản phẩm không hiển thị");
        //click 101 lần
        for (int i = 1; i <= 101; i++) {
            productListPage.clickIncreaseQuantity();
        }
        // Kiểm tra thông báo lỗi hiển thị
        Assert.assertTrue(productListPage.isMessageDisplayed(), "Không hiển thị thông báo");
        String actualMessage = productListPage.getNotiMessage();
        Assert.assertEquals(actualMessage,"Số lượng sản phẩm không được vượt quá 100 sản phẩm", "Thông báo không đúng như mong đợi");
    }
}
