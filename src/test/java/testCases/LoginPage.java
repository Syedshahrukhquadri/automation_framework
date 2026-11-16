package testCases;

import org.testng.annotations.Test;

import pageObjects.Homepage;
import pageObjects.LoginOb;
import testBase.BaseClassTC;

public class LoginPage extends BaseClassTC {

    @Test
    public void verifyLogin() throws InterruptedException {

        logger.info("Login test started");

        // Open login page
        Homepage hp = new Homepage(getDriver());
        hp.clickloginLink();

        LoginOb lp = new LoginOb(getDriver());

        lp.enterEmail(p.getProperty("email"));
        lp.enterPassword(p.getProperty("password"));
        lp.clickLogin();

        logger.info("Login button clicked");

        System.out.println(getDriver().getCurrentUrl());
        
        Thread.sleep(5000);
    }
}