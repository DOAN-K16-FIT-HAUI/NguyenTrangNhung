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

    @Test(description = "Kiểm thử validate Email khi nhập email sai định dạng")
    @AllureId("106")
    @Epic("Chức năng đăng nhập")
    @Feature("Đăng nhập")
    @Story("Hiển thị thông báo lỗi khi nhập sai định dạng Email")
    @Severity(SeverityLevel.NORMAL)
    public void testValidateEmail() {
        loginPage.enterEmail("abc");
        loginPage.clickPasswordField();
        loginPage.clickEmailField();

        String actualMessage = loginPage.getEmailInvalidMessage();
        String expectedMessage = "Email không đúng, vui lòng nhập lại.";

        Assert.assertEquals(actualMessage, expectedMessage, "Message lỗi email sai định dạng chưa đúng!");
    }
}
