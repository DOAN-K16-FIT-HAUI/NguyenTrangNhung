package Purchase;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;
import utils.LoginBaseTest;

public class PrepaidFailTest extends LoginBaseTest {
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

    @Test(description = "Người dùng mua hàng thất bại với hình thức Thanh toán bằng Ví Điện Tử HaUI Food nhưng số dư không đủ")
    @AllureId("Purchase-3")
    @Epic("Chức năng Đặt hàng")
    @Feature("Thanh toán bằng Ví Điện Tử HaUI Food")
    @Story("Người dùng đặt đơn hàng thất bại với hình thức Thanh toán bằng Ví Điện Tử  HaUI Food nhưng số dư không đủ")
    @Severity(SeverityLevel.CRITICAL)
    public void purchaseWithWalletFail() {
        loginPage.enterEmail("test@gmail.com");
        loginPage.enterPassword("#Nhung123");
        loginPage.clickLoginButton();
        homePage.waitUntilHomePageIsLoaded();
        homePage.waitSuccessLoginDisappear();

        //Thêm sản phẩm đầu tiên vào giỏ hàng
        driver.get("https://hauifood.vn/products");
        productListPage.clickFirstProduct();
        Assert.assertTrue(productListPage.isProductDrawerVisible(), "Drawer sản phẩm không hiển thị");

        productListPage.clickAddBtn();
        homePage.clickCloseToast();
        homePage.clickCartIcon();
        productListPage.clickCheckoutBtn();

        //Nhập thông tin giao hàng & chọn thanh toán khi nhận hàng
        orderPage.fillShippingInfo("Nguyễn Văn A","0912345678","123 Hà Nội" );
        orderPage.selectPrepaid();
        orderPage.clickOrder();

        //Kiểm tra thông báo
        Assert.assertTrue(orderPage.isMessageDisplayed(), "Không hiển thị thông báo");
        String actualMessage = orderPage.getNotiMessage();
        Assert.assertEquals(actualMessage,"Số dư tài khoản không đủ để thực hiện thanh toán", "Thông báo không đúng như mong đợi");
    }
}
