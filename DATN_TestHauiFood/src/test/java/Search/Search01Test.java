package Search;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import utils.HomePageBaseTest;

public class Search01Test extends HomePageBaseTest {

    @Test(description = "User tìm kiếm món ăn thành công khi nhập đúng tên món ăn")
    @AllureId("116")
    @Epic("Chức năng tìm kiếm")
    @Feature("Tìm kiếm")
    @Severity(SeverityLevel.NORMAL)
    @Story("Người dùng có thể tìm kiếm thành công khi nhập đúng tên món ăn")
    public void searchWithValidKeyword() {
        HomePage homePage = new HomePage(driver);
        homePage.enterSearchKeyword("Bánh mì chả");

        // Step 3: Kiểm tra kết quả trả về
        Assert.assertTrue(homePage.areAllResults("Bánh mì chả"), "Không phải tất cả kết quả đều chứa từ khóa 'Bánh mì chả'");
    }
}
