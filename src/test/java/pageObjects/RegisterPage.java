package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RegisterPage extends BasePage {

	//constructor
	public RegisterPage(WebDriver driver) {
		super(driver);
	}
	
	//locators
	@FindBy (xpath="//input[@id='input-username']") WebElement username;
	
	@FindBy (xpath="//input[@name='firstname']") WebElement fname;
	
	@FindBy (xpath="//input[@name='lastname']") WebElement lname;
	
	@FindBy (xpath="//input[@name='email']") WebElement email;
	
	@FindBy (xpath="//select[@name='country_id']") WebElement dropdown;
	
	@FindBy (xpath="//option[@value='1']") WebElement option1;
	
	@FindBy (xpath="//input[@name='password']") WebElement password;
	
	@FindBy (xpath="//button[@type='submit']") WebElement regbutton;
	
	
	public void setUsername(String un) {
		username.sendKeys(un);
	}
	
	public void setfirstname(String fnam) {
		fname.sendKeys(fnam);
	}
	
	public void setlastname(String lnam) {
		lname.sendKeys(lnam);
	}
	
	public void setemail(String eml) {
		email.sendKeys(eml);		
		}
	public void drpdown() {
		dropdown.click();
		
		}
	public void option1() {
		option1.click();
		
		}	
	
	public void setpasswrd(String psd) {
		password.sendKeys(psd);
		
		}
	
	public void clickbutton2() {
		regbutton.click();
		
		}
}
