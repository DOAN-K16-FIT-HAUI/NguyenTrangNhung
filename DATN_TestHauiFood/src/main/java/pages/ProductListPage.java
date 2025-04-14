package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ProductListPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By productItems = By.xpath("//div[contains(@class,'RestaurantList_restaurant-list__item')]");
    private By productDrawer = By.xpath("//div[contains(@class, 'QuantityDrawer_quantity-drawer__wrapper')]"); // Drawer bên phải
    private By drawerProductName = By.xpath("//div[contains(@class,'QuantityDrawer_quantity-drawer__product-name')]");
    private By drawerProductPrice = By.xpath("//div[contains(@class,'QuantityDrawer_quantity-drawer__product-price')]");
    private By drawerProductDesc = By.xpath("div[contains(@class,'QuantityDrawer_quantity-drawer__product-desc')]");
    private By drawerAddToCartButton = By.xpath("//button[contains(@class,'QuantityDrawer_quantity-drawer__add-btn')]");

    public ProductListPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    // Lấy danh sách tất cả sản phẩm
    public List<WebElement> getAllProducts() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(productItems));
        return driver.findElements(productItems);
    }
    public int getProductCount() {
        return getAllProducts().size();
    }
    // Click vào sản phẩm đầu tiên
    public void clickFirstProduct() {
        List<WebElement> products = getAllProducts();
        if (!products.isEmpty()) {
            products.get(0).click();
        } else {
            throw new RuntimeException("Không có sản phẩm nào để click.");
        }
    }

    public boolean isProductDrawerVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(productDrawer)).isDisplayed();
    }

    //Check sản phẩm trong drawer
    public String getDrawerProductName() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(drawerProductName)).getText();
    }
    public String getDrawerProductPrice() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(drawerProductPrice)).getText();
    }
    public String getDrawerProductDesc(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(drawerProductDesc)).getText();
    }
    public boolean isAddToCartButtonVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(drawerAddToCartButton)).isDisplayed();
    }
}
