
package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import org.openqa.selenium.remote.RemoteWebDriver;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseClassTC {

    // Thread-safe WebDriver for parallel execution
    private static ThreadLocal<WebDriver> tlDriver =
            new ThreadLocal<>();

    public Logger logger;
    public Properties p;

    public WebDriver getDriver() {
        return tlDriver.get();
    }

    @BeforeClass
    @Parameters({"browser"})
    public void setup(@Optional("chrome") String browserFromXML)
            throws IOException {

        // Load configuration
        FileReader file = new FileReader(
                "./src/test/resources/config.properties");

        p = new Properties();
        p.load(file);
        file.close();

        // Logger initialization
        logger = LogManager.getLogger(this.getClass());

        logger.info("Test setup started");

        String executionMode = p.getProperty(
                "executionMode", "local").trim();

        String browser = browserFromXML.trim();

        System.out.println("Execution Mode: " + executionMode);
        System.out.println("Browser: " + browser);

        // Remote execution using Selenium Grid
        if (executionMode.equalsIgnoreCase("remote")) {

            String gridAddress = p.getProperty("gridURL");

            if (gridAddress == null || gridAddress.isBlank()) {
                throw new RuntimeException("Grid URL is missing");
            }

            URL gridURL = new URL(gridAddress);

            if (browser.equalsIgnoreCase("chrome")) {

                ChromeOptions options = new ChromeOptions();

                tlDriver.set(
                        new RemoteWebDriver(gridURL, options));

            } else if (browser.equalsIgnoreCase("edge")) {

                EdgeOptions options = new EdgeOptions();

                tlDriver.set(
                        new RemoteWebDriver(gridURL, options));

            } else {
                throw new RuntimeException(
                        "Unsupported remote browser: " + browser);
            }

            logger.info(browser + " launched through Selenium Grid");
        }

        // Local execution
        else if (executionMode.equalsIgnoreCase("local")) {

            if (browser.equalsIgnoreCase("chrome")) {

                tlDriver.set(new ChromeDriver());

            } else if (browser.equalsIgnoreCase("edge")) {

                tlDriver.set(new EdgeDriver());

            } else {
                throw new RuntimeException(
                        "Unsupported local browser: " + browser);
            }

            logger.info(browser + " launched locally");
        }

        else {
            throw new RuntimeException(
                    "Invalid execution mode: " + executionMode);
        }

        // Session details
        RemoteWebDriver remoteDriver =
                (RemoteWebDriver) getDriver();

        System.out.println(
                "Session ID: " + remoteDriver.getSessionId());

        // Common browser settings
        getDriver().manage().deleteAllCookies();

        getDriver().manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(5));

        getDriver().get(p.getProperty("appurl"));

        getDriver().manage().window().maximize();

        System.out.println(
                "Application URL: " + getDriver().getCurrentUrl());

        logger.info("Application launched successfully");
    }

    @AfterClass(alwaysRun = true)
    public void teardown() {

        if (getDriver() != null) {

            try {
                getDriver().quit();
                logger.info("Browser closed successfully");
            } finally {
                tlDriver.remove();
            }
        }
    }

    // Screenshot method
    public String captureScreen(String tname) throws IOException {

        String timeStamp = new SimpleDateFormat(
                "yyyy.MM.dd.HH.mm.ss").format(new Date());

        TakesScreenshot ts = (TakesScreenshot) getDriver();

        File sourceFile = ts.getScreenshotAs(OutputType.FILE);

        File screenshotDir = new File(
                System.getProperty("user.dir"), "Screenshots");

        screenshotDir.mkdirs();

        File targetFile = new File(
                screenshotDir,
                tname + "_" + timeStamp + ".png");

        java.nio.file.Files.copy(
                sourceFile.toPath(),
                targetFile.toPath());

        return targetFile.getAbsolutePath();
    }
}
