package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Homepage extends BasePage {
	
	//constuctor
	public Homepage(WebDriver driver) {
		super(driver);
	}
	
	//Locators
	@FindBy(xpath="//a[@class='btn btn-black navbar-btn']") WebElement RegisterLink;
	
	//Action methods
	
	public void clickregisterLink() {
		RegisterLink.click();
	}

	

}
