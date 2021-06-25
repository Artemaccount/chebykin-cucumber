import com.google.common.io.Files;
import io.cucumber.java.ParameterType;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Пусть;
import io.cucumber.java.ru.Тогда;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
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

public class Runner {

    private static String pathScreenshots = "./screenshots/";
    private static String filename = "screenshot";
    private static int count = 0;

    @Attachment(value = "Page screenshot", type = "image/png")
    public static byte[] saveScreenshot(WebDriver driver) throws IOException {
        Screenshot screenshot = new AShot().takeScreenshot(driver);
        count++;
        File currentScreenshot = new File(pathScreenshots + filename + count + ".png");
        ImageIO.write(screenshot.getImage(), "png", currentScreenshot);
        return Files.toByteArray(currentScreenshot);
    }

    private static WebDriver driver;
    private static WebDriverWait waiter;

    @Step("Открываем ресурс Авито")
    @Пусть("открыт ресурс Авито")
    public static void openAvito() throws IOException {
        System.setProperty("webdriver.chrome.driver", "C:\\driver\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.avito.ru/");
        saveScreenshot(driver);
    }

    @ParameterType(".*")
    public Category category(String categoryName) throws IOException {
        return Category.valueOf(categoryName);
    }

    @Step("В выпадающем списке категорий выбираем ОРГТЕХНИКА")
    @И("в выпадающем списке категорий выбрана {category}")
    public static void chooseCat(Category category) throws IOException {
        Select categories = new Select(driver.findElement(By.xpath("//select[@name='category_id']")));
        categories.selectByValue(category.getNumber());
        saveScreenshot(driver);
    }

    @Step("В поле поиска вводим значение принтер")
    @И("в поле поиска введено значение {word}")
    @org.junit.jupiter.api.Test
    public static void printPrinter(String string) throws InterruptedException, IOException {
         waiter = new WebDriverWait(driver, 3000);
        synchronized (waiter) {
            waiter.wait(1000);
        }
        driver.findElement(By.xpath("//*[@id='search']")).sendKeys(string);
        saveScreenshot(driver);
    }

    @Step("Кликаем по выпадающему списку региона")
    @Тогда("кликнуть по выпадающему списку региона")
    @org.junit.jupiter.api.Test
    public static void clickReg() throws IOException {
        driver.findElement(By.xpath("//*[@data-marker='search-form/region']")).click();
        saveScreenshot(driver);
    }

    @Step("В поле регион вводим значение Владивосток")
    @Тогда("в поле регион ввести значение {word}")
    public static void printVladivostok(String string) throws IOException {
        driver.findElement(By.xpath("//*[@data-marker='popup-location/region/input']"))
                .sendKeys(string);
        saveScreenshot(driver);
    }

    @Step("Нажимаем кнопку показать объявления")
    @И("нажать кнопка показать объявления")
    public static void showAds() throws InterruptedException, IOException {
        synchronized (waiter) {
            waiter.wait(1500);
        }
        driver.findElement(By.xpath("//*[@data-marker='suggest(0)']")).click();
        driver.findElement(By.xpath("//*[@data-marker='popup-location/save-button']")).click();
        saveScreenshot(driver);
    }

    @Step("Открываем страницу результаты по запросу принтер")
    @Тогда("открылась страница результаты по запросу {word}")
    public static void openPage(String string) throws IOException {
        driver.findElement(By.xpath("//*[@data-marker='search-filters/submit-button']")).click();
        saveScreenshot(driver);
    }

    @Step("Активируем чекбокс только с фотографией")
    @И("активирован чекбокс только с фотографией")
    public static void checkBox() throws IOException {
        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/div/div[4]/div[1]/label[2]")).click();
        driver.findElement(By.xpath("//*[@data-marker='search-form/submit-button']")).click();
        saveScreenshot(driver);
    }

    @Step("В выпадающем списке сортировка выбираем значение ДОРОЖЕ")
    @И("в выпадающем списке сортировка выбрано значение {category}")
    public static void sortBy(Category category) throws IOException {
        driver.findElement(By.xpath
                ("/html/body/div[1]/div[3]/div[3]/div[3]/div[1]/div[2]/select/option["
                        + category.getNumber() + "]")).click();
        saveScreenshot(driver);
    }

    @Step("В консоль выводим значение название и цены 3 первых товаров")
    @И("в консоль выведено значение название и цены {int} первых товаров")
    public static void printFirstThree(int firstItemsCount) throws IOException {
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
        saveScreenshot(driver);
    }
}
