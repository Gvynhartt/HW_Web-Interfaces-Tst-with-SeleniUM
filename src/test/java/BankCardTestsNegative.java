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

import static org.junit.jupiter.api.Assertions.assertEquals;

class BankCardTestsNegative {

    WebDriver driver;

    @BeforeAll
    static void setupAll() {
        System.setProperty("webdriver.chrome.driver", "./driver/chromedriver_win32/chromedriver.exe");
    }

    @BeforeEach
    void setup() {
        //driver = new ChromeDriver();
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
    void shdTestNameSurnameFieldWithInvalidInput() {
        driver.get("http://localhost:9999/");
        List<WebElement> inputControls = driver.findElements(By.className("input__control"));

        inputControls.get(0).sendKeys("abobus3450d");
        inputControls.get(1).sendKeys("+10050013371453");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button_view_extra")).click();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        List<WebElement> inputSubs = driver.findElements(By.className("input__sub"));
        String actual = inputSubs.get(0).getText();
        assertEquals(expected, actual);
    }

    @Test
    void shdTestPhoneNumberFieldWithInvalidInput() {
        driver.get("http://localhost:9999/");
        List<WebElement> inputControls = driver.findElements(By.className("input__control"));

        inputControls.get(0).sendKeys("Киктор Вислый");
        inputControls.get(1).sendKeys("+10050013371453");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button_view_extra")).click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        List<WebElement> inputSubs = driver.findElements(By.className("input__sub"));
        String actual = inputSubs.get(1).getText();
        assertEquals(expected, actual);
    }

    @Test
    void shdWithTestBothFieldsCorrectButNoCheckbox() {
        driver.get("http://localhost:9999/");
        List<WebElement> inputControls = driver.findElements(By.className("input__control"));

        inputControls.get(0).sendKeys("Киктор Вислый");
        inputControls.get(1).sendKeys("+78005553535");
        // нажатие на галку пропускаем, само собой
        driver.findElement(By.className("button_view_extra")).click();
        List<WebElement> gdeGalkaTvar = driver.findElements(By.className("input_invalid"));

        String expected = "rgba(255, 92, 92, 1)";
        String actual = gdeGalkaTvar.get(0).getCssValue("color");
        assertEquals(expected, actual);
    }
}
