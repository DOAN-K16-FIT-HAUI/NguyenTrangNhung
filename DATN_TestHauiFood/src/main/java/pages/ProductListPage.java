package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class ProductListPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By productItems = By.xpath("//div[contains(@class,'RestaurantList_restaurant-list__item')]");
    private By message = By.xpath("//div[@role='alert' and contains(@class, 'Toastify__toast-body')]");
    //draw chi tiết sản phẩm
    private By productDrawer = By.xpath("//div[contains(@class, 'QuantityDrawer_quantity-drawer__wrapper')]");
    private By drawerProductName = By.xpath("//div[contains(@class,'QuantityDrawer_quantity-drawer__product-name')]");
    private By drawerProductPrice = By.xpath("//div[contains(@class,'QuantityDrawer_quantity-drawer__product-price')]");
    private By drawerAddToCartButton = By.xpath("//button[contains(@class,'QuantityDrawer_quantity-drawer__add-btn')]");
    //giỏ hàng
    private By cartQuantity = By.xpath("//span[contains(@class,'Header_header__actions-quantity')]");
    private By cartTotalPrice = By.xpath("//p[contains(@class, 'Button_title__RQLZg')]");
    private By cartProductName = By.xpath("//p[contains(@class,'CartItem_product__detail-name')]");
    private By cartProductPrice = By.xpath("//span[contains(@class,'CartItem_product__detail-price__')]");
    public ProductListPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
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
    public boolean isAddToCartButtonVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(drawerAddToCartButton)).isDisplayed();
    }
    //thêm sp vào giỏ hàng
    public void clickAddBtn(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(drawerAddToCartButton)).click();
    }
    public boolean isMessageDisplayed() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(message));
            return element.isDisplayed();
        } catch (TimeoutException | NoSuchElementException e) {
            return false;
        }
    }
    public void waitMessageDisappear() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(message));
    }
    public String getNotiMessage() {
        //lấy nội dung thông báo
        try {
            WebElement element = driver.findElement(message);
            return element.getText();
        } catch (NoSuchElementException e) {
            return null;
        }
    }
    public int getCartQuantity() {
        String quantityText = wait.until(ExpectedConditions.visibilityOfElementLocated(cartQuantity)).getText().trim();
        return Integer.parseInt(quantityText);
    }

    public String getCartTotalPrice() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(cartTotalPrice)).getText().trim();
    }
    public int getCartItemCount() {
        return driver.findElements(cartProductName).size();
    }

    public String getCartProductName() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(cartProductName)).getText().trim();
    }

    public String getCartProductPrice() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(cartProductPrice)).getText().trim();
    }
    //xóa 1 sp khỏi giỏ hàng
    private By removeBtn = By.xpath("(//button[contains(@class,'CartItem_product__detail-delete__')])[1]");
    public void removeFirstProduct() {
        wait.until(ExpectedConditions.elementToBeClickable(removeBtn)).click();
    }
    public int getFirstItemPrice() {
        WebElement priceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("(//span[starts-with(@class,'CartItem_product__detail-price__')])[1]")));
        String priceText = priceElement.getText().trim();

        if (priceText.isEmpty()) {
            throw new RuntimeException("Không lấy được giá của sản phẩm đầu tiên trong giỏ hàng.");
        }

        return convertPriceToInt(priceText);
    }

    private int convertPriceToInt(String priceText) {
        return Integer.parseInt(priceText.replace(".", "").replace("₫", "").trim());
    }
    public int getCartTotalPriceAsInt() {
        return convertPriceToInt(getCartTotalPrice());
    }
    //xóa toàn bộ giỏ hàng
    private By removeAllBtn = By.xpath("//button[contains(@class,'Cart_cart__products-delete-all__')]");
    public void clickRemoveAll(){
        wait.until(ExpectedConditions.elementToBeClickable(removeAllBtn)).click();
    }
    //thêm quá 100 sản phẩm
    private By plusBtn = By.xpath("//div[contains(@class, 'QuantityDrawer_quantity-drawer__quantity-plus__2ZkQD')]");
    public void clickIncreaseQuantity() {
        wait.until(ExpectedConditions.elementToBeClickable(plusBtn)).click();
    }

    // cập nhật số lượng sản phẩm trong giỏ hàng
    private By quantityBtn = By.xpath("//button[contains(@class, 'CartItem_product__mobile-quantity-btn')]");
    private By updateQuantityDrawer = By.xpath("//div[contains(@class, 'CartItem_change-quantity--show')]");
    private By increaseBtn = By.xpath("//div[contains(@class,'CartItem_change-quantity__btn-group')]//button[contains(@class,'CartItem_change-quantity__quantity-btn')][2]");
    private By decreaseBtn = By.xpath("//div[contains(@class,'CartItem_change-quantity__btn-group')]//button[contains(@class,'CartItem_change-quantity__quantity-btn')][1]");
    private By updateBtn = By.xpath("//p[text()='Cập nhật']/parent::button");

    public void clickQuantityBtn(){
        wait.until(ExpectedConditions.elementToBeClickable(quantityBtn)).click();
    }
    public boolean isUpdateQuantityDrawer() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(updateQuantityDrawer)).isDisplayed();
    }
    // Tăng số lượng sản phẩm
    public void increaseProductQuantity() {
        WebElement btn = wait.until(ExpectedConditions.visibilityOfElementLocated(increaseBtn));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
    }

    public void decreaseProductQuantity() {
        wait.until(ExpectedConditions.elementToBeClickable(decreaseBtn)).click();
    }

    public void clickUpdateQuantityBtn() {
        WebElement btn = wait.until(ExpectedConditions.visibilityOfElementLocated(updateBtn));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(updateQuantityDrawer));
    }

    //đóng drawer chi tiết giỏ hàng
    private By closeCartBtn = By.xpath("//button[contains(@class,'Cart_cart__close__')]");
    public void clickCloseCart(){
        wait.until(ExpectedConditions.elementToBeClickable(closeCartBtn)).click();
    }
    //thanh toán
    private By checkoutBtn = By.xpath("//p[contains(text(),'Thanh toán')]");
    public void clickCheckoutBtn(){
        wait.until(ExpectedConditions.elementToBeClickable(checkoutBtn)).click();
    }
}
