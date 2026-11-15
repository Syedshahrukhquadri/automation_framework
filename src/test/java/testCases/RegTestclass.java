
package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import org.openqa.selenium.remote.RemoteWebDriver;

import pageObjects.Homepage;
import pageObjects.RegisterPage;
import testBase.BaseClassTC;

public class RegTestclass extends BaseClassTC {

    @Test
    public void verifyAccount() {

        logger.info("Registration test started");

        // Verify Grid session
        System.out.println(
            "Session ID: " +
            ((RemoteWebDriver) getDriver()).getSessionId()
        );

        System.out.println(
            "Browser URL: " + getDriver().getCurrentUrl()
        );

        // Homepage object creation
        Homepage hp = new Homepage(getDriver());

        hp.clickregisterLink();

        logger.info("Clicked on registration link");

        // Register page object creation
        RegisterPage rp = new RegisterPage(getDriver());

        // Read test data from config.properties
        rp.setUsername(p.getProperty("username"));

        rp.setfirstname(p.getProperty("fname"));

        rp.setlastname(p.getProperty("lname"));

        rp.setemail(p.getProperty("email"));

        rp.drpdown();

        rp.option1();

        rp.setpasswrd(p.getProperty("password"));

        rp.clickbutton2();

        // Verify page title
        String title = getDriver().getTitle();

        System.out.println("Actual page title: " + title);

        Assert.assertTrue(
            title.contains("Register"),
            "Registration page title verification failed. Actual title: "
            + title
        );

        logger.info("Registration test completed");

        // Temporary: keep browser open for Grid verification
        try {

            Thread.sleep(10000);

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

        }
    }
}
