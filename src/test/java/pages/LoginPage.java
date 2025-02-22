package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
	
	WebDriver driver;
	
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id = "user-name")
	WebElement userNameInput;
	
	@FindBy(id = "password")
	WebElement passwordInput;
	
	@FindBy(id = "login-button")
	WebElement loginButton;
	
	public void login(String username, String password) {
		userNameInput.sendKeys(username);
		passwordInput.sendKeys(password);
		loginButton.click();
	}

}