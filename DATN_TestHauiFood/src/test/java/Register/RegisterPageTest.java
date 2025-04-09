package Register;

import pages.LoginPage;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.LoginBaseTest;

public class RegisterPageTest extends LoginBaseTest {

    @Test
    public void testAccessRegisterPage() {
        LoginPage loginPage = new LoginPage(driver);

        // Step 1: Từ Login page click qua Register page
        loginPage.clickRegister();

        // Step 2: Kiểm tra hiển thị tiêu đề Đăng ký
        By registerTitle = By.xpath("//h1[contains(text(),'Đăng ký')]");
        Assert.assertTrue(driver.findElement(registerTitle).isDisplayed(), "Không hiển thị tiêu đề Đăng ký");
    }
}
