package QLOrder;

import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ManagementOrderPage;
import utils.ManagementBaseTest;

public class ViewListOrderTest extends ManagementBaseTest {
    private ManagementOrderPage managementPage;

    @BeforeMethod
    public void setUpTest() {
        managementPage = new ManagementOrderPage(driver);
    }

    @Test(description = "Kiểm thử admin xem danh sách đơn đặt hàng")
    @AllureId("Admin-04")
    @Epic("Chức năng Quản lý đơn hàng")
    @Feature("Xem danh sách đơn đặt hàng")
    @Story("Admin xem danh sách đơn đặt hàng thành công")
    @Severity(SeverityLevel.CRITICAL)
    public void viewListOrderTest() throws InterruptedException {
        managementPage.enterEmail("haui@gmail.com");
        managementPage.enterPassword("#Haui123");
        managementPage.clickLoginButton();
        managementPage.clickOrderManagementMenu();
        Thread.sleep(1000);
        managementPage.orderTableIsDisplayed();
        managementPage.orderTableHasData();
        managementPage.clickViewOrderDetail(0);
        managementPage.verifyOrderDetailPage();
    }
}
