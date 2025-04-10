package Register;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.RegisterPage;
import utils.RegisterBaseTest;

public class CheckNameTest extends RegisterBaseTest{
    private RegisterPage registerPage;

    @BeforeMethod
    public void setUpTest() {
        registerPage = new RegisterPage(driver);
    }

    @Test(description = "Kiểm thử thông báo lỗi khi bỏ trống trường Họ tên")
    @AllureId("108")
    @Epic("Chức năng đăng ký")
    @Feature("Đăng ký")
    @Story("Người dùng nhập email đã đăng ký tài khoản")
    @Severity(SeverityLevel.NORMAL)
    public void testValidateNameIsRequired(){
        registerPage.clickNameField();
        registerPage.clickEmailField();
        registerPage.clickPasswordField();
        registerPage.clickNameField();

        String expectedNameMsg = "Vui lòng nhập họ tên";
        String actualNameMsg = registerPage.getNameErrorMessage();

        String expectedEmailMsg = "Vui lòng nhập email.";
        String actualEmailMsg = registerPage.getEmailErrorMessage();

        String expectedPasswordMsg = "Vui lòng nhập mật khẩu.";
        String actualPasswordMsg = registerPage.getPasswordErrorMessage();

        Assert.assertEquals(actualNameMsg, expectedNameMsg, "Message lỗi bỏ trống Họ tên không đúng!");
        Assert.assertEquals(actualEmailMsg, expectedEmailMsg, "Message lỗi bỏ trống Email không đúng!");
        Assert.assertEquals(actualPasswordMsg, expectedPasswordMsg, "Message lỗi bỏ trống Password không đúng!");
    }
}
