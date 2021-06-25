import io.cucumber.java.ParameterType;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Пусть;
import io.cucumber.java.ru.Тогда;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/features",
        glue = "ru.savkk.test",
        tags = "@all",
        dryRun = false,
        snippets = CucumberOptions.SnippetType.UNDERSCORE
)


public class RunnerTest {

    private static WebDriver driver;
    private static WebDriverWait waiter;


    @Пусть("открыт ресурс Авито")
    public static void openAvito() {
        System.setProperty("webdriver.chrome.driver", "C:\\driver\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.avito.ru/");
    }

    @ParameterType(".*")
    public Category category(String categoryName) {
        return Category.valueOf(categoryName);
    }

    @И("в выпадающем списке категорий выбрана {category}")
    public static void chooseCat(Category category) {
        Select categories = new Select(driver.findElement(By.xpath("//select[@name='category_id']")));
        categories.selectByValue(category.getNumber());
    }

    @И("в поле поиска введено значение {word}")
    public static void printPrinter(String string) throws InterruptedException {
         waiter = new WebDriverWait(driver, 3000);
        synchronized (waiter) {
            waiter.wait(1000);
        }
        driver.findElement(By.xpath("//*[@id='search']")).sendKeys(string);


    }

    @Тогда("кликнуть по выпадающему списку региона")
    public static void clickReg() {
        driver.findElement(By.xpath("//*[@data-marker='search-form/region']")).click();
    }

    @Тогда("в поле регион ввести значение {word}")
    public static void printVladivostok(String string) {
        driver.findElement(By.xpath("//*[@data-marker='popup-location/region/input']"))
                .sendKeys(string);
    }

    @И("нажать кнопка показать объявления")
    public static void showAds() throws InterruptedException {
        synchronized (waiter) {
            waiter.wait(1500);
        }
        driver.findElement(By.xpath("//*[@data-marker='suggest(0)']")).click();
        driver.findElement(By.xpath("//*[@data-marker='popup-location/save-button']")).click();
    }

    @Тогда("открылась страница результаты по запросу {word}")
    public static void openPage(String string) {
        driver.findElement(By.xpath("//*[@data-marker='search-filters/submit-button']")).click();
    }

    @И("активирован чекбокс только с фотографией")
    public static void checkBox() {
        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/div/div[4]/div[1]/label[2]")).click();
        driver.findElement(By.xpath("//*[@data-marker='search-form/submit-button']")).click();
    }

    @И("в выпадающем списке сортировка выбрано значение {category}")
    public static void sortBy(Category category) {
        driver.findElement(By.xpath
                ("/html/body/div[1]/div[3]/div[3]/div[3]/div[1]/div[2]/select/option["
                        + category.getNumber() + "]")).click();
    }

    @И("в консоль выведено значение название и цены {int} первых товаров")
    public static void printFirstThree(int firstItemsCount) {
        List<WebElement> elements = driver.findElements(By.xpath("//div[@data-marker='item']"));
        List<Item> items = new ArrayList<>();

        for (int i = 0; i < firstItemsCount; i++) {
            String input = (elements.get(i)
                    .findElement(By.xpath(".//*[@data-marker='item-title']")))
                    .getAttribute("title");
            String name = input.replace(" в Владивостоке", "");
            int price = Integer.parseInt(elements.get(i)
                    .findElement(By.xpath(".//*[@itemprop='price']"))
                    .getAttribute("content"));
            Item item = new Item(name, price);
            items.add(item);
        }
        items.forEach(System.out::println);
        driver.close();
    }
}
