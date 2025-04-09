package Login;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.LoginBaseTest;

public class CheckPasswordTest extends LoginBaseTest {
    private LoginPage loginPage;

    @BeforeMethod
    public void setUpTest() {
        loginPage = new LoginPage(driver);
    }

    @Test(description = "Kiểm thử validate Password khi để trống")
    @AllureId("105")
    @Epic("Chức năng đăng nhập")
    @Feature("Đăng nhập")
    @Story("Hiển thị thông báo lỗi khi bỏ trống Password")
    @Severity(SeverityLevel.NORMAL)
    public void testValidateEmailIsRequired() {
        loginPage.enterEmail("trangnhung29072003@gmail.com");
        loginPage.clickPasswordField();
        loginPage.clickEmailField();

        String expectedPasswordMsg = "Vui lòng nhập mật khẩu.";
        String actualPasswordMsg = loginPage.getPasswordErrorMessage();

        Assert.assertEquals(actualPasswordMsg, expectedPasswordMsg, "Message lỗi Password không đúng!");
    }
}
