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

    @Test(description = "Kiểm thử validate Password khi nhập password sai định dạng")
    @AllureId("107")
    @Epic("Chức năng đăng nhập")
    @Feature("Đăng nhập")
    @Story("Hiển thị thông báo lỗi khi nhập sai định dạng Password")
    @Severity(SeverityLevel.NORMAL)
    public void testValidateEmail() {
        loginPage.enterEmail("trangnhung29072003@gmail.com");
        loginPage.enterPassword("123");
        loginPage.clickEmailField();

        String actualMessage = loginPage.getPasswordInvalidMessage();
        String expectedMessage = "Mật khẩu phải chứa ít nhất 8 ký tự và 1 ký tự đặc biệt @-_";

        Assert.assertEquals(actualMessage, expectedMessage, "Message lỗi password sai định dạng chưa đúng!");
    }
}

