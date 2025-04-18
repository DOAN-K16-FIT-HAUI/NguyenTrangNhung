package Purchase;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;
import utils.LoginBaseTest;

public class PrepaidSuccessTest extends LoginBaseTest {
    private LoginPage loginPage;
    private HomePage homePage;
    private ProductListPage productListPage;
    private OrderPage orderPage;
    private ProfilePage profilePage;

    @BeforeMethod
    public void setUpTest() {
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        productListPage = new ProductListPage(driver);
        orderPage = new OrderPage(driver);
        profilePage = new ProfilePage(driver);
    }

    @Test(description = "Người dùng mua hàng thành công với hình thức Thanh toán bằng Ví Điện Tử HaUI Food")
    @AllureId("Purchase-2")
    @Epic("Chức năng Đặt hàng")
    @Feature("Thanh toán bằng Ví Điện Tử HaUI Food")
    @Story("Người dùng đặt đơn hàng thành công với hình thức Thanh toán bằng Ví Điện Tử  HaUI Food")
    @Severity(SeverityLevel.CRITICAL)
    public void purchaseWithWalletSuccess() {
        loginPage.enterEmail("trangnhung29072003@gmail.com");
        loginPage.enterPassword("#Nhung123");
        loginPage.clickLoginButton();
        homePage.waitUntilHomePageIsLoaded();
        homePage.clickCloseToast();

        //lấy số dư ví
        homePage.clickIconUser();
        homePage.clickProfile();
        try {
            Thread.sleep(2000); // đợi số dư ví hiển thị
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        double oldBalance = profilePage.getWalletBalance();

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
        double orderTotal = orderPage.getOrderTotalAmount();
        orderPage.clickOrder();

        //Kiểm tra thông báo
        Assert.assertTrue(orderPage.isMessageDisplayed(), "Không hiển thị thông báo");
        String actualMessage = orderPage.getNotiMessage();
        Assert.assertEquals(actualMessage,"Đặt hàng thành công", "Thông báo không đúng như mong đợi");

        //Kiểm tra số dư ví
        homePage.clickCloseToast();
        driver.get("https://hauifood.vn/auth/profile");
        try {
            Thread.sleep(2000); // đợi số dư ví hiển thị
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        double newBalance = profilePage.getWalletBalance();
        double expectedBalance = oldBalance - orderTotal;
        Assert.assertEquals(newBalance, expectedBalance, 0.01, "Số dư ví sau khi thanh toán không đúng");
    }
}
