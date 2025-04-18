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

public class EmptyInforTest extends LoginBaseTest {
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

    @Test(description = "Kiểm tra thông báo lỗi khi người dùng để trống tất cả các thông tin đặt hàng")
    @AllureId("Purchase-7")
    @Epic("Chức năng Đặt hàng")
    @Feature("Validate số điện thoại")
    @Story("Người dùng để trống tất cả các thông tin đặt hàng")
    @Severity(SeverityLevel.CRITICAL)
    public void purchaseWithWalletFail() {
        loginPage.enterEmail("test@gmail.com");
        loginPage.enterPassword("#Nhung123");
        loginPage.clickLoginButton();
        homePage.waitUntilHomePageIsLoaded();
        homePage.clickCloseToast();

        //Thêm sản phẩm đầu tiên vào giỏ hàng
        driver.get("https://hauifood.vn/products");
        productListPage.clickFirstProduct();
        Assert.assertTrue(productListPage.isProductDrawerVisible(), "Drawer sản phẩm không hiển thị");

        productListPage.clickAddBtn();
        homePage.clickCloseToast();
        homePage.clickCartIcon();
        productListPage.clickCheckoutBtn();

        orderPage.clearField();
        orderPage.selectBank();
        //Kiểm tra thông báo
        String actualNameMessage = orderPage.getErrorName();
        String actualPhoneMessage = orderPage.getErrorPhoneNumber();
        String actualAddressMessage = orderPage.getErrorAddress();
        Assert.assertEquals(actualNameMessage,"Tên người nhận không được để trống", "Thông báo không đúng như mong đợi");
        Assert.assertEquals(actualPhoneMessage,"Số điện thoại không được để trống", "Thông báo không đúng như mong đợi");
        Assert.assertEquals(actualAddressMessage,"Địa chỉ không được để trống", "Thông báo không đúng như mong đợi");
    }
}
