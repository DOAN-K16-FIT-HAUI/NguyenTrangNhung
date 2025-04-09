package Login;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.LoginBaseTest;

public class ValidateEmailTest extends LoginBaseTest {
    private LoginPage loginPage;

    @BeforeMethod
    public void setUpTest() {
        loginPage = new LoginPage(driver);
    }

    @Test(description = "Kiểm thử validate Email khi để trống")
    @AllureId("104")
    @Epic("Chức năng đăng nhập")
    @Feature("Đăng nhập")
    @Story("Hiển thị thông báo lỗi khi bỏ trống Email")
    @Severity(SeverityLevel.NORMAL)
    public void testValidateEmailIsRequired() {
        loginPage.clickEmailField();
        loginPage.clickPasswordField();
        loginPage.clickEmailField();

        String expectedEmailMsg = "Vui lòng nhập email.";
        String actualEmailMsg = loginPage.getEmailErrorMessage();

        Assert.assertEquals(actualEmailMsg, expectedEmailMsg, "Message lỗi Email không đúng!");
    }
}

