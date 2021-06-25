package stepDef;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Hook {

    private static WebDriver driver;

    @Before
    public static void before() {
        System.setProperty("webdriver.chrome.driver", "C:\\driver\\chromedriver.exe");
        driver = new ChromeDriver();
    }

    public static WebDriver getDriver(){
        return driver;
    }

    @After
    public static void after() {
        driver.close();
    }
}
