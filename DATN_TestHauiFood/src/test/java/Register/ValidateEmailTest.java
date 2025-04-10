package Register;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.RegisterPage;
import utils.RegisterBaseTest;

public class ValidateEmailTest extends RegisterBaseTest{
    private RegisterPage registerPage;

    @BeforeMethod
    public void setUpTest() {
        registerPage = new RegisterPage(driver);
    }

    @Test(description = "Kiểm thử validate Email khi nhập email sai định dạng")
    @AllureId("112")
    @Epic("Chức năng đăng ký")
    @Feature("Đăng ký")
    @Story("Hiển thị thông báo lỗi khi nhập sai định dạng Email")
    @Severity(SeverityLevel.NORMAL)
    public void testValidateEmail() {
        registerPage.enterName("Nhung");
        registerPage.enterEmail("abc");
        registerPage.clickPasswordField();
        registerPage.clickEmailField();

        String actualMessage = registerPage.getEmailInvalidMessage();
        String expectedMessage = "Email không đúng, vui lòng nhập lại.";

        Assert.assertEquals(actualMessage, expectedMessage, "Message lỗi email sai định dạng chưa đúng!");
    }
}
