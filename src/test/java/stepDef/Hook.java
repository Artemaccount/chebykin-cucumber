package stepDef;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Hook {

    private static WebDriver driver;
    private static WebDriverWait waiter;

    @Before
    public static void before() {
        System.setProperty("webdriver.chrome.driver", "C:\\driver\\chromedriver.exe");
        driver = new ChromeDriver();
        waiter = new WebDriverWait(driver, 3000);
    }

    public static WebDriverWait getWaiter(){
        return waiter;
    }

    public static WebDriver getDriver(){
        return driver;
    }

    @After
    public static void after() {
        driver.close();
    }
}
