package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import pages.InventoryPage;
import testng.apis.Hooks;

public class SauceDemoE2ETest extends Hooks {
	
	@Test
	public void addBackpackProductCart() {
		InventoryPage inventory = new InventoryPage(driver);
		inventory.addBackpackToCart();
		Assert.assertTrue(inventory.cartBadge.isEnabled());
	}

}