package Register;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.RegisterPage;
import utils.RegisterBaseTest;

public class RegisteredEmailTest extends RegisterBaseTest {
    private RegisterPage registerPage;

    @BeforeMethod
    public void setUpTest() {
        registerPage = new RegisterPage(driver);
    }

    @Test(description = "Kiểm thử đăng ký thất bại khi nhập sai email đã đăng ký")
    @AllureId("109")
    @Epic("Chức năng đăng ký")
    @Feature("Đăng ký")
    @Story("Người dùng nhập email đã đăng ký tài khoản")
    @Severity(SeverityLevel.CRITICAL)
    public void testLoginWrongEmail() {
        registerPage.enterName("Nhung");
        registerPage.enterEmail("trangnhung29072003@gmail.com");
        registerPage.enterPassword("#Nhung09061983");
        registerPage.clickRegisterButton();

        // Kiểm tra hiển thị thông báo lỗi
        Assert.assertTrue(registerPage.isErrorMessageDisplayed(), "Không tìm thấy thông báo lỗi!");


        // Kiểm tra nội dung thông báo lỗi
        String actualMessage = registerPage.getErrorMessageText();
        String expectedMessage = "Email đã tồn tại";
        Assert.assertEquals(actualMessage, expectedMessage, "Nội dung thông báo lỗi không đúng!");
    }
}
