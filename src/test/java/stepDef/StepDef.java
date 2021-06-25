package stepDef;

import com.google.common.io.Files;
import io.cucumber.java.ParameterType;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Пусть;
import io.cucumber.java.ru.Тогда;
import io.qameta.allure.Attachment;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StepDef {


    private static WebDriverWait waiter;

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


    @Пусть("открыт ресурс Авито")
    public static void openAvito() throws IOException {
        Hook.getDriver().manage().window().maximize();
        Hook.getDriver().get("https://www.avito.ru/");
        saveScreenshot(Hook.getDriver());
    }

    @ParameterType(".*")
    public Category category(String categoryName) throws IOException {
        return Category.valueOf(categoryName);
    }

    @И("в выпадающем списке категорий выбрана {category}")
    public static void chooseCat(Category category) throws IOException {
        Select categories = new Select(Hook.getDriver().findElement(By.xpath("//select[@name='category_id']")));
        categories.selectByValue(category.getNumber());
        saveScreenshot(Hook.getDriver());
    }

    @И("в поле поиска введено значение {word}")
    public static void printPrinter(String string) throws InterruptedException, IOException {
        waiter = new WebDriverWait(Hook.getDriver(), 3000);
        synchronized (waiter) {
            waiter.wait(2000);
        }
        Hook.getDriver().findElement(By.xpath("//*[@id='search']")).sendKeys(string);
        saveScreenshot(Hook.getDriver());
    }

    @Тогда("кликнуть по выпадающему списку региона")
    public static void clickReg() throws IOException {
        Hook.getDriver().findElement(By.xpath("//*[@data-marker='search-form/region']")).click();
        saveScreenshot(Hook.getDriver());
    }

    @Тогда("в поле регион ввести значение {word}")
    public static void printVladivostok(String string) throws IOException {
        Hook.getDriver().findElement(By.xpath("//*[@data-marker='popup-location/region/input']"))
                .sendKeys(string);
        saveScreenshot(Hook.getDriver());
    }

    @И("нажать кнопка показать объявления")
    public static void showAds() throws InterruptedException, IOException {
        synchronized (waiter) {
            waiter.wait(1500);
        }
        Hook.getDriver().findElement(By.xpath("//*[@data-marker='suggest(0)']")).click();
        Hook.getDriver().findElement(By.xpath("//*[@data-marker='popup-location/save-button']")).click();
        saveScreenshot(Hook.getDriver());
    }

    @Тогда("открылась страница результаты по запросу {word}")
    public static void openPage(String expected) throws IOException {
        String actual = Hook.getDriver().findElement(By.xpath("//*[@data-marker='search-form/suggest']"))
                .getAttribute("value");
        Assertions.assertEquals(expected, actual);
        saveScreenshot(Hook.getDriver());
    }

    @И("активирован чекбокс только с фотографией")
    public static void checkBox() throws IOException {
        Hook.getDriver().findElement(By.xpath("//*[@data-marker='search-form/with-images']")).click();
        saveScreenshot(Hook.getDriver());
    }

    @И("в выпадающем списке сортировка выбрано значение {category}")
    public static void sortBy(Category category) throws IOException {
        //
        Hook.getDriver().findElement(By.xpath("//*[@data-marker='search-form/with-images']")).click();
        Hook.getDriver().findElement(By.xpath
                ("/html/body/div[1]/div[3]/div[3]/div[3]/div[1]/div[2]/select/option["
                        + category.getNumber() + "]")).click();
        saveScreenshot(Hook.getDriver());
    }

    @И("в консоль выведено значение название и цены {int} первых товаров")
    public static void printFirstThree(int firstItemsCount) throws IOException {
        List<WebElement> elements = Hook.getDriver().findElements(By.xpath("//div[@data-marker='item']"));
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
        saveScreenshot(Hook.getDriver());
    }
}
