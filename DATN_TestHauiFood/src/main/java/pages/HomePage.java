package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class HomePage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By successLoginMessage = By.xpath("//div[text()='Đăng nhập thành công']");
    private By successLogoutMessage = By.xpath("//div[text()='Đăng xuất thành công']");
    private By homePageElement = By.cssSelector(".Banner_banner__content__WxUF8");
    private By iconUser = By.xpath("//img[contains(@class, 'Header_header__actions-avatar')]");
    private By btnLogout = By.xpath("//p[text()='Đăng xuất']");
    //tìm kiếm
    private By searchInput = By.xpath("//input[@id='banner-search']");
    private By searchButton = By.xpath("//button[text()='Tìm kiếm']");
    private By productName = By.xpath("//div[contains(@class, 'ProductCard_product__name__')]");
    private By restaurantName = By.xpath("//div[@class='RestaurantCard_restaurant-card__name__R1eVd']");
    private By noRestaurantMessage = By.xpath("//h3[contains(text(), 'hiện không có nhà hàng')]");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public String getSuccessLoginMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(successLoginMessage)).getText();
    }

    public String getSuccessLogoutMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(successLogoutMessage)).getText();
    }

    public boolean isHomePageDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(homePageElement)).isDisplayed();
    }

    public void clickIconUser() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(successLoginMessage));  // chờ toast biến mất
        driver.findElement(iconUser).click();
    }

    public void clickLogout() {
        wait.until(ExpectedConditions.elementToBeClickable(btnLogout)).click();
    }

    //Module Tìm kiếm
    public void enterSearchKeyword(String keyword) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));
        input.clear();
        input.sendKeys(keyword);
    }

    public void clickSearchButton() {
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
    }
    public List<WebElement> getProductResults() {
        return driver.findElements(productName);
    }
    public List<WebElement> getRestaurantResults() {
        return driver.findElements(restaurantName);
    }
    public boolean areAllResults(String keyword) {
        keyword = keyword.toLowerCase();
        // Kiểm tra danh sách sản phẩm
        List<WebElement> products = getProductResults();
        for (WebElement product : products) {
            if (!product.getText().toLowerCase().contains(keyword)) {
                return false;
            }
        }
        // Kiểm tra nếu không có nhà hàng
        List<WebElement> noRestaurant = driver.findElements(noRestaurantMessage);
        List<WebElement> restaurants = getRestaurantResults();

        if (!noRestaurant.isEmpty()) {
            return true;
        }
        // Nếu có danh sách nhà hàng, kiểm tra nội dung
        for (WebElement restaurant : restaurants) {
            if (!restaurant.getText().toLowerCase().contains(keyword)) {
                return false;
            }
        }
        return true;
    }
}
