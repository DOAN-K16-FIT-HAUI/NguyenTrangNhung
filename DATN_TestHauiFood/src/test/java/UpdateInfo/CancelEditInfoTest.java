package UpdateInfo;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.ProfilePage;
import utils.LoginBaseTest;

public class CancelEditInfoTest extends LoginBaseTest {
    private LoginPage loginPage;
    private HomePage homePage;
    private ProfilePage profilePage;

    @BeforeMethod
    public void setUpTest() {
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        profilePage = new ProfilePage(driver);
    }
    @Test(description = "Kiểm tra người dùng thực hiện hủy cập nhật thông tin cá nhân")
    @AllureId("UpdateInfo-2")
    @Epic("Chức năng Cập nhật thông tin cá nhân")
    @Feature("Cập nhật thông tin tài khoản")
    @Story("Người dùng hủy cập nhật thông tin cá nhân")
    @Severity(SeverityLevel.NORMAL)
    public void cancelEditInfo() throws InterruptedException {
        loginPage.enterEmail("haui@gmail.com");
        loginPage.enterPassword("#Haui123");
        loginPage.clickLoginButton();
        homePage.clickCloseToast();

        homePage.clickIconUser();
        homePage.clickProfile();
        profilePage.clickEditBtn();
        Thread.sleep(2000);
        String oldName = profilePage.getFullName();
        String oldPhone = profilePage.getPhone();
        String oldGender = profilePage.getGender();

        profilePage.updateProfile("Nguyễn A", "0978866912", "Nam");

        profilePage.clickCancelEditBtn();
        Thread.sleep(2000);
        //Kiểm tra thông tin vẫn giữ nguyên
        Assert.assertEquals(profilePage.getFullName(), oldName, "Họ tên không giữ nguyên sau khi hủy cập nhật");
        Assert.assertEquals(profilePage.getPhone(), oldPhone, "Số điện thoại không giữ nguyên sau khi hủy cập nhật");
        Assert.assertEquals(profilePage.getGender(), oldGender, "Giới tính không giữ nguyên sau khi hủy cập nhật");
    }

}
