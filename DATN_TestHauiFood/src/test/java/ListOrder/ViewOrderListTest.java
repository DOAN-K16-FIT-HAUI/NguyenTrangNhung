package ListOrder;

import io.qameta.allure.*;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.OrderPage;
import pages.ProfilePage;
import utils.LoginBaseTest;

public class ViewOrderListTest extends LoginBaseTest {
    private LoginPage loginPage;
    private HomePage homePage;
    private ProfilePage profilePage;
    private OrderPage orderPage;

    @BeforeMethod
    public void setUpTest() {
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        profilePage = new ProfilePage(driver);
        orderPage = new OrderPage(driver);
    }
    @Test(description = "Người dùng xem danh sách các đơn mua ở từng trạng thái")
    @AllureId("ListOrders-2")
    @Epic("Chức năng Quản lý đơn hàng")
    @Feature("Xem danh sách đơn hàng")
    @Story("Người dùng xem các đơn hàng đã mua trước đó theo từng trạng thái")
    @Severity(SeverityLevel.NORMAL)
    public void viewOrdersByStatus() {
        loginPage.enterEmail("trangnhung29072003@gmail.com");
        loginPage.enterPassword("#Nhung123");
        loginPage.clickLoginButton();
        homePage.waitUntilHomePageIsLoaded();
        homePage.clickCloseToast();

        homePage.clickIconUser();
        homePage.clickProfile();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        profilePage.clickViewOrder();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, -500);");
        // Lần lượt click từng tab và kiểm tra
        profilePage.clickOrderTabByIndex(2); // Chờ xác nhận
        Assert.assertTrue(profilePage.isOrderListVisibleForTab("Chờ xác nhận"), "Không hiển thị đơn hàng cho tab Chờ xác nhận");

        profilePage.clickOrderTabByIndex(3); // Đã xác nhận
        Assert.assertTrue(profilePage.isOrderListVisibleForTab("Đã xác nhận"), "Không hiển thị đơn hàng cho tab Đã xác nhận");

        profilePage.clickOrderTabByIndex(4); // Đang giao
        Assert.assertTrue(profilePage.isOrderListVisibleForTab("Đang giao"), "Không hiển thị đơn hàng cho tab Đang giao");

        profilePage.clickOrderTabByIndex(5); // Hoàn thành
        Assert.assertTrue(profilePage.isOrderListVisibleForTab("Hoàn thành"), "Không hiển thị đơn hàng cho tab Hoàn thành");

        profilePage.clickOrderTabByIndex(6); // Đã hủy
        Assert.assertTrue(profilePage.isOrderListVisibleForTab("Đã hủy"), "Không hiển thị đơn hàng cho tab Đã hủy");

        profilePage.clickOrderTabByIndex(7); // Đang giao
        Assert.assertTrue(profilePage.isOrderListVisibleForTab("Từ chối"), "Không hiển thị đơn hàng cho tab Từ chối");
    }
}
