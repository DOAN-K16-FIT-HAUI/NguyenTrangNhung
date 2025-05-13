package QLUser;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ManagementOrderPage;
import pages.ManagementUserPage;
import utils.ManagementBaseTest;

public class AddUserTest extends ManagementBaseTest {
    private ManagementOrderPage managementPage;
    private ManagementUserPage userPage;

    @BeforeMethod
    public void setUpTest() {
        managementPage = new ManagementOrderPage(driver);
        userPage = new ManagementUserPage(driver);
    }
    @Test(description = "Kiểm thử admin thêm mới người dùng")
    @AllureId("Admin-16")
    @Epic("Chức năng Quản lý người dùng")
    @Feature("Thêm mới người dùng")
    @Story("Admin thêm mới người dùng thành công")
    @Severity(SeverityLevel.CRITICAL)
    public void addUserTest() throws InterruptedException {
        managementPage.enterEmail("haui@gmail.com");
        managementPage.enterPassword("#Haui123");
        managementPage.clickLoginButton();
        userPage.clickUserManagementMenu();
        Thread.sleep(1000);
        userPage.clickAddUserBtn();
        userPage.setUserName("Test");
        userPage.setEmail("pp@gmail.com");
        userPage.setPhone("0918716182");
        userPage.setPassword("#Haui123");
        userPage.clickAddUserBtn();
        Assert.assertTrue(userPage.isToastDisplayed(), "Không hiển thị thông báo");
        Assert.assertEquals(userPage.getToastMessage(),"Tạo người dùng thành công", "Thông báo hiển thị không đúng mong đợi");
    }
}
