package QLUser;

import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ManagementOrderPage;
import pages.ManagementUserPage;
import utils.ManagementBaseTest;

public class ViewUserInfoTest extends ManagementBaseTest {
    private ManagementOrderPage managementPage;
    private ManagementUserPage userPage;

    @BeforeMethod
    public void setUpTest() {
        managementPage = new ManagementOrderPage(driver);
        userPage = new ManagementUserPage(driver);
    }
    @Test(description = "Kiểm thử admin xem chi tiết người dùng")
    @AllureId("Admin-12")
    @Epic("Chức năng Quản lý người dùng")
    @Feature("Xem chi tiết người dùng")
    @Story("Admin xem chi tiết người dùng thành công")
    @Severity(SeverityLevel.CRITICAL)
    public void viewUserDetailTest() throws InterruptedException {
        managementPage.enterEmail("haui@gmail.com");
        managementPage.enterPassword("#Haui123");
        managementPage.clickLoginButton();
        userPage.clickUserManagementMenu();
        Thread.sleep(1000);
        userPage.userTableIsDisplayed();
        userPage.userTableHasData();
        userPage.clickViewOrderDetail(1);
        userPage.verifyUserDetailPage();
    }
}
