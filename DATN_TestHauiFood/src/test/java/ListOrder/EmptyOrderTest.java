package ListOrder;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;
import utils.LoginBaseTest;

public class EmptyOrderTest extends LoginBaseTest {
    private LoginPage loginPage;
    private HomePage homePage;
    private ProductListPage productListPage;
    private ProfilePage profilePage;
    private OrderPage orderPage;

    @BeforeMethod
    public void setUpTest() {
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        productListPage = new ProductListPage(driver);
        orderPage = new OrderPage(driver);
        profilePage = new ProfilePage(driver);
    }

    @Test(description = "Kiểm tra người dùng xem danh sách đơn hàng khi chưa có đơn hàng nào được đặt")
    @AllureId("ListOrder-1")
    @Epic("Chức năng Xem danh sách đơn hàng")
    @Feature("Xem danh sách đơn hàng")
    @Story("Người dùng xem danh sách đơn hàng khi chưa có đơn hàng nào được đặt")
    @Severity(SeverityLevel.NORMAL)
    public void viewEmptyOrder() {
        loginPage.enterEmail("thanhnt@gmail.com");
        loginPage.enterPassword("#Thanh123");
        loginPage.clickLoginButton();
        homePage.waitUntilHomePageIsLoaded();
        homePage.clickCloseToast();

        homePage.clickIconUser();
        homePage.clickProfile();
        profilePage.clickViewOrder();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Kiểm tra thông báo
        Assert.assertTrue(profilePage.viewEmptyOrder(),"Không hiển thị thông báo chưa có đơn hàng nào");
    }
}
