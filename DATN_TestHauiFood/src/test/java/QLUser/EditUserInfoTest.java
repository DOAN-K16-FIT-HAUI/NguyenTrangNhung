package QLUser;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ManagementOrderPage;
import pages.ManagementUserPage;
import utils.ManagementBaseTest;

public class EditUserInfoTest extends ManagementBaseTest {
    private ManagementOrderPage managementPage;
    private ManagementUserPage userPage;

    @BeforeMethod
    public void setUpTest() {
        managementPage = new ManagementOrderPage(driver);
        userPage = new ManagementUserPage(driver);
    }
    @Test(description = "Kiểm thử admin chỉnh sửa thông tin người dùng")
    @AllureId("Admin-13")
    @Epic("Chức năng Quản lý người dùng")
    @Feature("Chỉnh sửa người dùng")
    @Story("Admin chỉnh sửa thông tin người dùng thành công")
    @Severity(SeverityLevel.NORMAL)
    public void editUserTest() throws InterruptedException {
        managementPage.enterEmail("haui@gmail.com");
        managementPage.enterPassword("#Haui123");
        managementPage.clickLoginButton();
        userPage.clickUserManagementMenu();
        Thread.sleep(1000);
        userPage.clickEditUser(3);
        userPage.setUserName("Minh Qúy");
        userPage.setPhone("0918716182");
        userPage.clickEditBtn();
        Assert.assertTrue(managementPage.isToastDisplayed(), "Không hiển thị thông báo");
        Assert.assertEquals(managementPage.getErrorMessage(),"Chỉnh sửa người dùng thành công", "Thông báo hiển thị không đúng mong đợi");
    }
}
