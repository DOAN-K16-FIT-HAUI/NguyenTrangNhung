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

public class PhoneLengthTest extends LoginBaseTest {
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

    @Test(description = "Người dùng nhập vào số điện thoại không hợp lệ về độ dài")
    @AllureId("Purchase-5")
    @Epic("Chức năng Đặt hàng")
    @Feature("Validate số điện thoại")
    @Story("Người dùng đặt đơn hàng thất bại khi nhập số điện thoại không hợp lệ về độ dài")
    @Severity(SeverityLevel.NORMAL)
    public void testPhoneLength() {
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
        orderPage.fillShippingInfo("Nguyễn Văn A","0","123 Hà Nội" );
        try {
            Thread.sleep(2000); // đợi số dư ví hiển thị
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Kiểm tra thông báo
        String actualMessage = orderPage.getErrorPhoneNumber();
        Assert.assertEquals(actualMessage,"Số điện thoại không hợp lệ", "Thông báo không đúng như mong đợi");
    }
}
