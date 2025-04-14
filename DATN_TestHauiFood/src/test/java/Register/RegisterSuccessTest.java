package Register;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.RegisterPage;
import utils.RegisterBaseTest;

public class RegisterSuccessTest extends RegisterBaseTest {
    private RegisterPage registerPage;

    @BeforeMethod
    public void setUpTest() {
        registerPage = new RegisterPage(driver);
    }

    @Test(description = "Kiểm thử đăng ký thành công khi nhập đủ các trường hợp lệ")
    @AllureId("111")
    @Epic("Chức năng đăng ký")
    @Feature("Đăng ký")
    @Story("Người dùng đăng ký thành công với thông tin hợp lệ")
    @Severity(SeverityLevel.CRITICAL)
    public void testRegisterSuccess() {
        registerPage.enterName("Minh");
        registerPage.enterEmail("minhnh@gmail.com");
        registerPage.enterPassword("#Minh123");
        registerPage.clickRegisterButton();

        // Kiểm tra hiển thị thông báo thành công
        Assert.assertTrue(registerPage.isSuccessMessageDisplayed(), "Không tìm thấy thông báo thành công!");

        // Kiểm tra nội dung thông báo thành công
        String actualMessage = registerPage.getSuccessMessageText();
        String expectedMessage = "Đăng ký tài khoản thành công";
        Assert.assertEquals(actualMessage, expectedMessage, "Nội dung thông báo thành công không đúng!");
    }
}
