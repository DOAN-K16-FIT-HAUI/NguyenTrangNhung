package ViewAccount;

import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ProfilePage;
import utils.HomePageBaseTest;

public class ViewAccInfoFailTest extends HomePageBaseTest {
    private ProfilePage profilePage;
    @BeforeMethod
    public void setUpTest() {
        profilePage = new ProfilePage(driver);
    }
    @Test(description = "Kiểm tra người dùng truy cập trang thông tin cá nhân khi chưa đăng nhập")
    @AllureId("ViewAccInfo-2")
    @Epic("Chức năng Xem thông tin tài khoản")
    @Feature("Xem thông tin tài khoản")
    @Story("Người dùng truy cập trang xem thông tin cá nhân khi chưa đăng nhập")
    @Severity(SeverityLevel.NORMAL)
    public void viewAccountInfo() throws InterruptedException {
        driver.get("https://hauifood.vn/auth/profile");
        Thread.sleep(2000);
        // Kiểm tra xem có chuyển sang trang lỗi 403 không (bằng cách check nội dung "403" hoặc nút "Quay lại trang chủ")
        WebElement errorImg = driver.findElement(By.xpath("//img[contains(@class, 'Forbidden_image-403__')]"));
        WebElement backButton = driver.findElement(By.xpath("//a[contains(text(), 'Quay lại trang chủ')]"));

        // Xác nhận cả hai phần tử đều hiển thị
        Assert.assertTrue(errorImg.isDisplayed(), "Không hiển thị tiêu đề lỗi 403");
        Assert.assertTrue(backButton.isDisplayed(), "Không hiển thị nút quay lại trang chủ");
    }
}
