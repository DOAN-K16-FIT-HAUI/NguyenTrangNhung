package QLUser;

import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ManagementOrderPage;
import pages.ManagementUserPage;
import utils.ManagementBaseTest;

public class FilterUserTest extends ManagementBaseTest {
    private ManagementOrderPage managementPage;
    private ManagementUserPage userPage;

    @BeforeMethod
    public void setUpTest() {
        managementPage = new ManagementOrderPage(driver);
        userPage = new ManagementUserPage(driver);
    }
    @Test(description = "Kiểm thử admin xem danh sách và lọc người dùng")
    @AllureId("Admin-11")
    @Epic("Chức năng Quản lý người dùng")
    @Feature("Xem danh sách đơn người dùng")
    @Story("Admin xem danh sách và lọc người dùng thành công")
    @Severity(SeverityLevel.CRITICAL)
    public void viewListUserTest() throws InterruptedException {
        managementPage.enterEmail("haui@gmail.com");
        managementPage.enterPassword("#Haui123");
        managementPage.clickLoginButton();
        userPage.clickUserManagementMenu();
        Thread.sleep(1000);
        userPage.userTableIsDisplayed();
        userPage.userTableHasData();
        userPage.filterUsers("Người dùng", "Nữ","Mở khoá");
        userPage.verifyFilteredResultsContent("Người dùng", "Nữ", "Mở khoá");
    }
}
