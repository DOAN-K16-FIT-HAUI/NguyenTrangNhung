package Login;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.LoginBaseTest;

public class CheckEmailTest extends LoginBaseTest {
    private LoginPage loginPage;

    @BeforeMethod
    public void setUpTest() {
        loginPage = new LoginPage(driver);
    }

    @Test(description = "Kiểm thử validate Email khi để trống")
    @AllureId("101")
    @Epic("Chức năng đăng nhập")
    @Feature("Đăng nhập")
    @Story("Hiển thị thông báo lỗi khi bỏ trống Email")
    @Severity(SeverityLevel.NORMAL)
    public void testValidateEmailIsRequired() {
        loginPage.clickEmailField();
        loginPage.clickPasswordField();
        loginPage.clickEmailField();

        String expectedEmailMsg = "Email không đúng, vui lòng nhập lại.";
        String actualEmailMsg = loginPage.getEmailErrorMessage();

        Assert.assertEquals(actualEmailMsg, expectedEmailMsg, "Message lỗi bỏ trống Email không đúng!");
    }
}
