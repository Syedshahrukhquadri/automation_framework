package automation_framework1;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class Frstpgm {
	
	@Test
	void display1() {
		System.out.println("hi");
		WebDriver driver=new ChromeDriver();
		driver.get("https://www.opencart.com/");
	}


		
		
	
		


}
