package VendorPortal;

import Utils.Config;
import Utils.Constants;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class VendorPortalTest extends AbstractTest {
    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private String userName, password;

    @BeforeTest
    @Parameters({"userName", "password"})
    public void setPageObjects(String userName, String password) {
        this.loginPage = new LoginPage(driver);
        this.dashboardPage = new DashboardPage(driver);
        this.userName = userName;
        this.password = password;

    }

    @Test
    public void loginTest() {
        loginPage.goTo(Config.get(Constants.VENDOR_PORTAL_URL));
        Assert.assertTrue(loginPage.isAt());
        loginPage.login(userName, password);
    }

    @Test(dependsOnMethods = "loginTest")
    public void dashboardTest() {
        Assert.assertTrue(dashboardPage.isAt());

        switch (userName) {
            case "sam":
                // finance metrics
                Assert.assertEquals(dashboardPage.getMonthlyEarning(), "$40,000");
                Assert.assertEquals(dashboardPage.getAnnualEarning(), "$215,000");
                Assert.assertEquals(dashboardPage.getProfitMargin(), "50%");
                Assert.assertEquals(dashboardPage.getAvailableInventory(), "18");

                // order history search
                dashboardPage.searchOrderHistoryBy("adams");
                Assert.assertEquals(dashboardPage.getSearchResultsCount(), 8);
                break;
            case "john":// finance metrics
                Assert.assertEquals(dashboardPage.getMonthlyEarning(), "$3,453");
                Assert.assertEquals(dashboardPage.getAnnualEarning(), "$34,485");
                Assert.assertEquals(dashboardPage.getProfitMargin(), "-16%");
                Assert.assertEquals(dashboardPage.getAvailableInventory(), "67");

                // order history search
                dashboardPage.searchOrderHistoryBy("2024/01/01");
                Assert.assertEquals(dashboardPage.getSearchResultsCount(), 0);
                break;
            case "mike":// finance metrics
                Assert.assertEquals(dashboardPage.getMonthlyEarning(), "$55,000");
                Assert.assertEquals(dashboardPage.getAnnualEarning(), "$563,300");
                Assert.assertEquals(dashboardPage.getProfitMargin(), "80%");
                Assert.assertEquals(dashboardPage.getAvailableInventory(), "45");

                // order history search
                dashboardPage.searchOrderHistoryBy("miami");
                Assert.assertEquals(dashboardPage.getSearchResultsCount(), 10);
                break;
        }

//        // finance metrics
//        Assert.assertEquals(dashboardPage.getMonthlyEarning(), "$40,000");
//        Assert.assertEquals(dashboardPage.getAnnualEarning(), "$215,000");
//        Assert.assertEquals(dashboardPage.getProfitMargin(), "50%");
//        Assert.assertEquals(dashboardPage.getAvailableInventory(), "18");
//
//        // order history search
//        dashboardPage.searchOrderHistoryBy("adams");
//        Assert.assertEquals(dashboardPage.getSearchResultsCount(), 8);

    }

    @Test(dependsOnMethods = "dashboardTest")
    public void logoutTest() {
        dashboardPage.logout();
        Assert.assertTrue(loginPage.isAt());
    }
}
