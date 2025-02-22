package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class InventoryPage {
	
	WebDriver driver;
	
	public InventoryPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id = "add-to-cart-sauce-labs-backpack")
	WebElement backpackAddToCart;
	
	@FindBy(css = "a[data-test='shopping-cart-link']")
	WebElement cartLink;
	
	@FindBy(css = "span[data-test='shopping-cart-badge']")
	public WebElement cartBadge;
	
	public InventoryPage addBackpackToCart() {
		backpackAddToCart.click();
		return this;
	}
	
	public InventoryPage clickCartIcon() {
		cartLink.click();
		return this;
	}

}