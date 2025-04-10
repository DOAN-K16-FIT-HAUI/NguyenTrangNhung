package Register;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.RegisterPage;
import utils.RegisterBaseTest;

public class ValidateNameTest extends RegisterBaseTest{
    private RegisterPage registerPage;

    @BeforeMethod
    public void setUpTest() {
        registerPage = new RegisterPage(driver);
    }

    @Test(description = "Kiểm thử validate Họ tên khi nhập Họ tên sai định dạng")
    @AllureId("113")
    @Epic("Chức năng đăng ký")
    @Feature("Đăng ký")
    @Story("Hiển thị thông báo lỗi khi nhập sai định dạng Họ tên")
    @Severity(SeverityLevel.NORMAL)
    public void testValidateName() {
        registerPage.enterName("Hà");
        registerPage.enterEmail("doha@gmail.com");
        registerPage.enterPassword("@HaDo123");
        registerPage.clickRegisterButton();

        // Kiểm tra hiển thị thông báo lỗi
        Assert.assertTrue(registerPage.isErrorMessageDisplayed(), "Không tìm thấy thông báo lỗi!");


        String actualMessage = registerPage.getNameInvalidMessage();
        String expectedMessage = "Họ tên từ 3 đến 50 ký tự";

        Assert.assertEquals(actualMessage, expectedMessage, "Message lỗi Họ tên sai định dạng chưa đúng!");
    }
}
