package CancelOrder;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;
import utils.LoginBaseTest;

public class CancelOrderTest extends LoginBaseTest {
    private LoginPage loginPage;
    private HomePage homePage;
    private ProductListPage productListPage;
    private ProfilePage profilePage;

    @BeforeMethod
    public void setUpTest() {
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        productListPage = new ProductListPage(driver);
        profilePage = new ProfilePage(driver);
    }

    @Test(description = "Kiểm tra người dùng hủy đơn hàng ở trạng thái chờ xác nhận thành công ")
    @AllureId("CancelOrder-1")
    @Epic("Chức năng Hủy đơn hàng")
    @Feature("Hủy đơn hàng")
    @Story("Người dùng hủy đơn hàng ở trạng thái chờ xác nhận thành công")
    @Severity(SeverityLevel.CRITICAL)
    public void cancelOrderSuccess() {
        loginPage.enterEmail("trangnhung29072003@gmail.com");
        loginPage.enterPassword("#Nhung123");
        loginPage.clickLoginButton();
        homePage.waitUntilHomePageIsLoaded();
        homePage.clickCloseToast();

        // Điều hướng đến đơn hàng
        homePage.clickIconUser();
        homePage.clickProfile();
        double beforeCancelCoin = profilePage.getWalletBalance();
        profilePage.clickViewOrder();

        sleep(2000);
        profilePage.clickWaitConfirm();
        sleep(2000);

        // Kiểm tra phương thức thanh toán
        String paymentMethod = profilePage.getPaymentMethodText();
        boolean isWalletPayment = paymentMethod.contains("Ví Điện Tử HaUI Food");

        // Bấm nút Hủy đơn
        profilePage.clickCancelBtn();
        Assert.assertTrue(profilePage.viewConfirmCancelDisplayed(), "Không hiển thị màn xác nhận hủy đơn hàng");
        profilePage.fillReason("Thay đổi địa chỉ nhận hàng");
        profilePage.clickConfirmCancel();

        Assert.assertTrue(productListPage.isMessageDisplayed(), "Không hiển thị thông báo hủy thành công");
        String actualMessage = productListPage.getNotiMessage();
        Assert.assertEquals(actualMessage, "Thành công", "Thông báo không đúng như mong đợi");

        // Nếu là thanh toán bằng ví thì kiểm tra HaUI Coin đã được hoàn
        if (isWalletPayment) {
            sleep(2000); // đợi coin cập nhật
            double afterCancelCoin = profilePage.getWalletBalance();
            Assert.assertTrue(afterCancelCoin > beforeCancelCoin, "Số dư HaUI Coin không được hoàn sau khi hủy đơn");
        }
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
