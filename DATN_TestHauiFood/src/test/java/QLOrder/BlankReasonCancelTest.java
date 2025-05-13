package QLOrder;

import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ManagementOrderPage;
import utils.ManagementBaseTest;

public class BlankReasonCancelTest extends ManagementBaseTest {
    private ManagementOrderPage managementPage;

    @BeforeMethod
    public void setUpTest() {
        managementPage = new ManagementOrderPage(driver);
    }
    @Test(description = "Kiểm thử thông báo khi admin bỏ trống lý do từ chối đơn hàng đang ở trạng thái chờ xác nhận")
    @AllureId("Admin-07")
    @Epic("Chức năng Quản lý đơn hàng")
    @Feature("Từ chối đơn hàng")
    @Story("Admin bỏ trống lý do từ chối đơn hàng đang ở trạng thái chờ xác nhận")
    @Severity(SeverityLevel.NORMAL)
    public void errorCancelOrder() throws InterruptedException {
        managementPage.enterEmail("haui@gmail.com");
        managementPage.enterPassword("#Haui123");
        managementPage.clickLoginButton();
        managementPage.clickOrderManagementMenu();
        Thread.sleep(1000);
        managementPage.orderTableIsDisplayed();
        managementPage.orderTableHasData();
        managementPage.blankReasonCancel();
        Thread.sleep(1000);
    }
}
