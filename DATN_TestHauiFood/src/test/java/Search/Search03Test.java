package Search;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import utils.HomePageBaseTest;

public class Search03Test extends HomePageBaseTest {

    @Test(description = "User tìm kiếm món ăn thất bại khi nhập tên món ăn không tồn tại")
    @AllureId("118")
    @Epic("Chức năng tìm kiếm")
    @Feature("Tìm kiếm")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Người dùng có thể tìm kiếm thất bại khi nhập tên món ăn không tồn tại")
    public void testSuccessfulSearchWithValidKeyword() {
        HomePage homePage = new HomePage(driver);
        homePage.enterSearchKeyword("Bánh Mỳ");
        Assert.assertTrue(homePage.areAllResults("Bánh Mỳ"), "Không trả ra kết quả, hiển thị hiện không có nhà hàng nào");
    }
}
