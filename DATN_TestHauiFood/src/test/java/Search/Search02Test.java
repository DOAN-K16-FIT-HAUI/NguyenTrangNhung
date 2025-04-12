package Search;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import utils.HomePageBaseTest;

public class Search02Test extends HomePageBaseTest {

    @Test(description = "User tìm kiếm món ăn thành công khi nhập gần đúng tên món ăn")
    @AllureId("117")
    @Epic("Chức năng tìm kiếm")
    @Feature("Tìm kiếm")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Người dùng có thể tìm kiếm thành công khi nhập gần đúng tên món ăn")
    public void testSuccessfulSearchWithValidKeyword() {
        HomePage homePage = new HomePage(driver);

        homePage.enterSearchKeyword("Bánh Mì");
        homePage.clickSearchButton();

        // Step 3: Kiểm tra kết quả trả về
        Assert.assertTrue(homePage.areAllResults("Bánh Mì"), "Không phải tất cả kết quả đều chứa từ khóa 'Bánh Mì'");
    }
}
