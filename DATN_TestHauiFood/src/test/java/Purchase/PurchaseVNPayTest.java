package Purchase;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.OrderPage;
import pages.ProductListPage;
import utils.LoginBaseTest;

public class PurchaseVNPayTest extends LoginBaseTest {
    private LoginPage loginPage;
    private HomePage homePage;
    private ProductListPage productListPage;
    private OrderPage orderPage;

    @BeforeMethod
    public void setUpTest() {
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        productListPage = new ProductListPage(driver);
        orderPage = new OrderPage(driver);
    }

    @Test(description = "Người dùng mua hàng thành công khi thanh toán chuyển khoản qua VNPay")
    @AllureId("Purchase-4")
    @Epic("Chức năng Đặt hàng")
    @Feature("Thanh toán trực tuyến VNPay")
    @Story("Người dùng đặt đơn hàng thành công với hình thức thanh toán VNPay")
    @Severity(SeverityLevel.CRITICAL)
    public void purchaseWithVNPay() {
        loginPage.enterEmail("test@gmail.com");
        loginPage.enterPassword("#Nhung123");
        loginPage.clickLoginButton();
        homePage.waitUntilHomePageIsLoaded();
        homePage.clickCloseToast();

        // Thêm sản phẩm đầu tiên vào giỏ hàng
        driver.get("https://hauifood.vn/products");
        productListPage.clickFirstProduct();
        Assert.assertTrue(productListPage.isProductDrawerVisible(), "Drawer sản phẩm không hiển thị");

        productListPage.clickAddBtn();
        homePage.clickCloseToast();
        homePage.clickCartIcon();
        productListPage.clickCheckoutBtn();

        // Nhập thông tin giao hàng & chọn thanh toán VNPay
        orderPage.fillShippingInfo("Nguyễn Văn B", "0912345678", "123 Hà Nội");
        orderPage.selectBank();
        String beforeClickOrderUrl = driver.getCurrentUrl();// Ghi lại URL trước khi đặt hàng
        orderPage.clickOrder();

        // Kiểm tra: không được hiển thị thông báo "Đặt hàng thành công" trước khi điều hướng sang trang VNPay
        boolean messageAppeared = orderPage.isMessageDisplayed();
        String afterClickOrderUrl = driver.getCurrentUrl();
        if (messageAppeared && beforeClickOrderUrl.equals(afterClickOrderUrl)) {
            Assert.fail("Thông báo 'Đặt hàng thành công' hiển thị trước khi điều hướng sang trang thanh toán VNPay");
        }
        // Kiểm tra điều hướng đúng đến VNPay
        Assert.assertTrue(afterClickOrderUrl.contains("sandbox.vnpayment.vn"), "Không điều hướng tới trang thanh toán VNPay");
    }
}
