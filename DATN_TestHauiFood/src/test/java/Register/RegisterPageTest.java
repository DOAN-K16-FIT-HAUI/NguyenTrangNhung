package Register;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.RegisterPage;
import utils.LoginBaseTest;

public class RegisterPageTest extends LoginBaseTest {
    private LoginPage loginPage;
    private RegisterPage registerPage;

    @BeforeMethod
    public void setUpTest() {
        loginPage = new LoginPage(driver);
    }
    @Test(description = "Kiểm tra truy cập trang Đăng ký từ Login page")
    @AllureId("110")
    @Epic("Chức năng đăng ký")
    @Feature("Đăng ký")
    @Severity(SeverityLevel.NORMAL)
    @Story("Người dùng có thể truy cập trang Đăng ký")
    @Description("Thực hiện truy cập trang Đăng ký từ Login page")
    public void testAccessRegisterPage() {
        registerPage = new RegisterPage(driver);

        Allure.step("Click button Đăng ký trên trang Login");
        loginPage.clickRegister();

        Allure.step("Verify tiêu đề Đăng ký hiển thị");
        try {
            Assert.assertTrue(registerPage.isRegisterTitleDisplayed(), "Không hiển thị tiêu đề Đăng ký");
        } catch (AssertionError e) {
            throw e;  //Bắt buộc để TestNG nhận fail
        }
    }
}
