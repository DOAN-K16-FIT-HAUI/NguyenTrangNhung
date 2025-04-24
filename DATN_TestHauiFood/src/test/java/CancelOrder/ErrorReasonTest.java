package CancelOrder;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;
import utils.LoginBaseTest;

public class ErrorReasonTest extends LoginBaseTest {
    private LoginPage loginPage;
    private HomePage homePage;
    private ProductListPage productListPage;
    private ProfilePage profilePage;

    @BeforeMethod
    public void setUpTest() {
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        productListPage = new ProductListPage(driver);
        profilePage = new ProfilePage(driver);
    }

    @Test(description = "Kiểm tra thông báo lỗi khi người dùng bỏ trống lý do hủy đơn hàng")
    @AllureId("CancelOrder-2")
    @Epic("Chức năng Hủy đơn hàng")
    @Feature("Hủy đơn hàng")
    @Story("Người dùng bỏ trống lý do hủy đơn hàng")
    @Severity(SeverityLevel.NORMAL)
    public void errorReasonBlank() {
        loginPage.enterEmail("trangnhung29072003@gmail.com");
        loginPage.enterPassword("#Nhung123");
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
        profilePage.clickWaitConfirm();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        profilePage.clickCancelBtn();
        Assert.assertTrue(profilePage.viewConfirmCancelDisplayed(), "Không hiển thị màn xác nhận hủy đơn hàng");
        profilePage.clickConfirmCancel();
        //Kiểm tra thông báo
        Assert.assertTrue(productListPage.isMessageDisplayed(),"Không hiển thị thông báo lỗi");
        String actualMessage = productListPage.getNotiMessage();
        Assert.assertEquals(actualMessage, "Không được để trống lý do hủy đơn hàng, vui lòng nhập lại", "Thông báo không đúng như mong đợi");
    }
}
