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

public class PhoneInvalidTest extends LoginBaseTest {
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

    @Test(description = "Người dùng nhập vào số điện thoại không hợp lệ")
    @AllureId("Purchase-6")
    @Epic("Chức năng Đặt hàng")
    @Feature("Validate fields")
    @Story("Người dùng đặt đơn hàng thất bại khi nhập số điện thoại không hợp lệ")
    @Severity(SeverityLevel.CRITICAL)
    public void testPhoneInvalid() {
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

        //Nhập sđt không hợp lệ
        orderPage.fillShippingInfo("Nguyễn Văn A","1234567890","Hà Nội" );
        orderPage.selectCash();
        orderPage.clickOrder();
        //Kiểm tra thông báo
        Assert.assertTrue(orderPage.isMessageDisplayed(),"Không hiển thị thông báo");
        String actualMessage = orderPage.getNotiMessage();
        Assert.assertEquals(actualMessage,"Số điện thoại không hợp lệ, vui lòng kiểm tra lại", "Thông báo không đúng như mong đợi");
    }
}
