package Register;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.RegisterPage;
import utils.RegisterBaseTest;

public class ValidatePasswordTest extends RegisterBaseTest{
    private RegisterPage registerPage;

    @BeforeMethod
    public void setUpTest() {
        registerPage = new RegisterPage(driver);
    }

    @Test(description = "Kiểm thử validate Password khi nhập password sai định dạng")
    @AllureId("114")
    @Epic("Chức năng đăng ký")
    @Feature("Đăng ký")
    @Story("Hiển thị thông báo lỗi khi nhập sai định dạng Password")
    @Severity(SeverityLevel.NORMAL)
    public void testValidateEmail() {
        registerPage.enterName("Nhung");
        registerPage.enterEmail("nhungnt@gmail.com");
        registerPage.enterPassword("123");
        registerPage.clickEmailField();

        String actualMessage = registerPage.getPasswordInvalidMessage();
        String expectedMessage = "Mật khẩu phải chứa ít nhất 8 ký tự và 1 ký tự đặc biệt @-_";

        Assert.assertEquals(actualMessage, expectedMessage, "Message lỗi password sai định dạng chưa đúng!");
    }
}
