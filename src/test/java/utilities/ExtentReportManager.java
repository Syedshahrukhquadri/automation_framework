package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testCases.RegTestclass;



public class ExtentReportManager implements ITestListener {

    public ExtentSparkReporter spReport;
    public ExtentReports extentReport;
    public ExtentTest testReport;

    String repName;

    @Override
    public void onStart(ITestContext testContext) {

        /*
         * Option 1: Create a file with a timestamp.
         *
         * SimpleDateFormat df =
         *         new SimpleDateFormat("yyyy.MM.dd.HH.ss");
         * Date dt = new Date();
         * String currentDateTimeStamp = df.format(dt);
         */

        // Option 2
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.ss")
                .format(new Date());

        repName = "Test Report-" + timeStamp + ".html";

        spReport = new ExtentSparkReporter(".\\reports\\" + repName);

        spReport.config().setDocumentTitle("OpenCart Automation Report");
        spReport.config().setReportName("OpenCart Functional Testing");
        spReport.config().setTheme(Theme.DARK);

        extentReport = new ExtentReports();
        extentReport.attachReporter(spReport);

        extentReport.setSystemInfo("Application", "OpenCart");
        extentReport.setSystemInfo("Module", "Admin");
        extentReport.setSystemInfo("Submodule", "Customers");
        extentReport.setSystemInfo(
                "User Name", System.getProperty("user.name"));
        extentReport.setSystemInfo("Environment", "QA");

        String os = testContext.getCurrentXmlTest().getParameter("os");
        extentReport.setSystemInfo("Operating System", os);

        String browser = testContext.getCurrentXmlTest()
                .getParameter("browser");
        extentReport.setSystemInfo("Browser", browser);

        List<String> includedGroups = testContext.getCurrentXmlTest()
                .getIncludedGroups();

        if (!includedGroups.isEmpty()) {
            extentReport.setSystemInfo("Groups", includedGroups.toString());
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {

        testReport = extentReport.createTest(
                result.getTestClass().getName());

        testReport.assignCategory(result.getMethod().getGroups());

        testReport.log(
                Status.PASS,
                result.getName() + " got successfully executed");
    }

    @Override
    public void onTestFailure(ITestResult result) {

        testReport = extentReport.createTest(
                result.getTestClass().getName());

        testReport.assignCategory(result.getMethod().getGroups());

        testReport.log(
                Status.FAIL,
                result.getName() + " got failed");

        testReport.log(
                Status.INFO,
                result.getThrowable().getMessage());

        try {
            RegTestclass testInstance = (RegTestclass) result.getInstance();

            String imgPath = testInstance.captureScreen(result.getName());

            testReport.addScreenCaptureFromPath(imgPath);

        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {

        testReport = extentReport.createTest(
                result.getTestClass().getName());

        testReport.assignCategory(result.getMethod().getGroups());

        testReport.log(
                Status.SKIP,
                result.getName() + " got skipped");

        testReport.log(
                Status.INFO,
                result.getThrowable().getMessage());
    }

    @Override
    public void onFinish(ITestContext testContext) {

        extentReport.flush();

        String pathOfExtentRep = System.getProperty("user.dir")
                + "\\reports\\" + repName;

        File extentReportFile = new File(pathOfExtentRep);

        try {
            Desktop.getDesktop().browse(extentReportFile.toURI());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}