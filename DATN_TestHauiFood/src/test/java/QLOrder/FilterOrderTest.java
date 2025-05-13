package QLOrder;

import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ManagementOrderPage;
import utils.ManagementBaseTest;

public class FilterOrderTest extends ManagementBaseTest {
    private ManagementOrderPage managementPage;

    @BeforeMethod
    public void setUpTest() {
        managementPage = new ManagementOrderPage(driver);
    }

    @Test(description = "Kiểm thử admin lọc danh sách đơn đặt hàng")
    @AllureId("Admin-10")
    @Epic("Chức năng Quản lý đơn hàng")
    @Feature("Xem danh sách đơn đặt hàng")
    @Story("Admin lọc danh sách đơn đặt hàng thành công")
    @Severity(SeverityLevel.CRITICAL)
    public void viewListOrderTest() throws InterruptedException {
        managementPage.enterEmail("haui@gmail.com");
        managementPage.enterPassword("#Haui123");
        managementPage.clickLoginButton();
        managementPage.clickOrderManagementMenu();
        Thread.sleep(1000);
        managementPage.orderTableIsDisplayed();
        managementPage.orderTableHasData();
        managementPage.filterOrders("Đã giao", "Khi nhận hàng", "Đã thanh toán");
        Thread.sleep(1000);
        managementPage.verifyFilteredResultsContent("Đã giao", "Khi nhận hàng", "Đã thanh toán");
    }
}
