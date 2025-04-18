package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class HomePage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By successLoginMessage = By.xpath("//div[text()='Đăng nhập thành công']");
    private By successLogoutMessage = By.xpath("//div[text()='Đăng xuất thành công']");
    private By iconUser = By.xpath("//img[contains(@class, 'Header_header__actions-avatar')]");
    private By btnLogout = By.xpath("//p[text()='Đăng xuất']");
    //tìm kiếm
    private By searchInput = By.xpath("//input[@id='banner-search']");
    //giỏ hàng
    private By cartIcon = By.xpath("//div[contains(@class,'Header_header__actions-cart')]");
    private By cartDrawer = By.xpath("//div[contains(@class,'Cart_cart--show')]");
    private By emptyCartText = By.xpath("//h5[contains(@class,'Cart_cart__empty-title')]");
    private By closeToastBtn = By.xpath("//button[contains(@class,'Toastify__close-button')]");
    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }
    public String getSuccessLoginMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(successLoginMessage)).getText();
    }
    public void waitSuccessLoginDisappear() {
        try {
            WebElement toast = driver.findElement(successLoginMessage);
            wait.until(ExpectedConditions.stalenessOf(toast));
        } catch (NoSuchElementException e) {
        }
    }

    public String getSuccessLogoutMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(successLogoutMessage)).getText();
    }
    //đóng thông báo
    public void clickCloseToast(){
        wait.until(ExpectedConditions.elementToBeClickable(closeToastBtn)).click();
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
    public List<WebElement> getSearchResultProducts() {
        By searchResultsLocator = By.xpath("//div[contains(@class, 'home__search-result-container')]//div[contains(@class, 'ProductCard_product__name__')]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchResultsLocator));
        return driver.findElements(searchResultsLocator);
    }
    public boolean areAllResults(String keyword) {
        keyword = keyword.toLowerCase();
        List<WebElement> products = getSearchResultProducts();
        for (WebElement product : products) {
            if (!product.getText().toLowerCase().contains(keyword)) {
                return false;
            }
        }
        return true;
    }
    public boolean noResultDisplayed() {
        By noResultContainer = By.xpath("//div[contains(@class,'NoResult_no-result__container')]");
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(noResultContainer));
            return driver.findElement(noResultContainer).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }
    //Giỏ hàng
    public void waitUntilHomePageIsLoaded() {
        wait.until(driver -> driver.findElement(cartIcon).isDisplayed());
    }

    public void clickCartIcon() {
        WebElement icon = wait.until(driver -> driver.findElement(cartIcon));
        icon.click();
    }

    public boolean isCartDrawerDisplayed() {
        return wait.until(driver -> driver.findElement(cartDrawer).isDisplayed());
    }
    public boolean isEmptyCartTextDisplayed() {
        return wait.until(driver -> driver.findElement(emptyCartText).isDisplayed());
    }
    //trang cá nhân
    private By profileBtn = By.xpath("//p[text()='Trang cá nhân']");
    public void clickProfile(){
        wait.until(ExpectedConditions.elementToBeClickable(profileBtn)).click();
    }
}
