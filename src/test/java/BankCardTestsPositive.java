import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BankCardTestsPositive {

    WebDriver driver;

    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void teardown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shdTestShortPathCorrect() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.xpath("//span[@data-test-id='name']/descendant::input[@name='name']")).sendKeys("Киктор Вислый-Анков");
        driver.findElement(By.xpath("//span[@data-test-id='phone']/descendant::input[@name='phone']")).sendKeys("+78005553535");
        driver.findElement(By.xpath("//label[@data-test-id='agreement']")).click();
        driver.findElement(By.xpath("//button[@role='button']/descendant::span[contains(text(), \"Продолжить\")]")).click();
        // Здесь и далее - применён более надёжный локатор для кнопки отправки формы.
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText().trim();
        assertEquals(expected, actual);
    }

}
