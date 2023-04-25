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
    void shdTestNameSurnameFieldWithInvalidInput() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.xpath("//span[@data-test-id='name']/descendant::input[@name='name']")).sendKeys("abobus3450d");
        driver.findElement(By.xpath("//span[@data-test-id='phone']/descendant::input[@name='phone']")).sendKeys("+78005553535");
        driver.findElement(By.xpath("//label[@data-test-id='agreement']")).click();
        driver.findElement(By.xpath("//div[@class='form-field form-field_size_m form-field_theme_alfa-on-white']/descendant::button[@role='button']")).click();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver.findElement(By.xpath("//span[contains(@class,'input_invalid')]/descendant::span[@class='input__sub']")).getText();
        assertEquals(expected, actual);
    }

    @Test
    void shdTestPhoneNumberFieldWithInvalidInput() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.xpath("//span[@data-test-id='name']/descendant::input[@name='name']")).sendKeys("Киктор Вислый-Анков");
        driver.findElement(By.xpath("//span[@data-test-id='phone']/descendant::input[@name='phone']")).sendKeys("+100500");
        driver.findElement(By.xpath("//label[@data-test-id='agreement']")).click();
        driver.findElement(By.xpath("//div[@class='form-field form-field_size_m form-field_theme_alfa-on-white']/descendant::button[@role='button']")).click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.xpath("//span[contains(@class,'input_invalid')]/descendant::span[@class='input__sub']")).getText();
        assertEquals(expected, actual);
    }

    @Test
    void shdWithTestBothFieldsCorrectButNoCheckbox() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.xpath("//span[@data-test-id='name']/descendant::input[@name='name']")).sendKeys("Киктор Вислый-Анков");
        driver.findElement(By.xpath("//span[@data-test-id='phone']/descendant::input[@name='phone']")).sendKeys("+78005553535");
        // пропускаем нажатие на метку чекбокса
        driver.findElement(By.xpath("//div[@class='form-field form-field_size_m form-field_theme_alfa-on-white']/descendant::button[@role='button']")).click();
        // Теперь пропуск галки проверяется, как всё остальное - через X-Path по наличию класса ошибки в разметке. И да, я сравниваю с null (300 IQ).
        boolean expected = true;
        boolean actual = false;
        if ( driver.findElement(By.xpath("//label[contains(@class,'input_invalid')]/descendant::span[@class='checkbox__text']")) != null ) {
            actual = true;
        }
        assertEquals(expected, actual);
    }

    @Test
    void shdTestFormSubmitWithNameFieldEmpty() {
        driver.get("http://localhost:9999/");
        // пропускаем ввод имени
        driver.findElement(By.xpath("//span[@data-test-id='phone']/descendant::input[@name='phone']")).sendKeys("+78005553535");
        driver.findElement(By.xpath("//label[@data-test-id='agreement']")).click();
        driver.findElement(By.xpath("//div[@class='form-field form-field_size_m form-field_theme_alfa-on-white']/descendant::button[@role='button']")).click();
        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.xpath("//span[@data-test-id='name'][contains(@class,'input_invalid')]/descendant::span[@class='input__sub']")).getText();
        assertEquals(expected, actual);
    }

    @Test
    void shdTestFormSubmitWithNumberFieldEmpty() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.xpath("//span[@data-test-id='name']/descendant::input[@name='name']")).sendKeys("Киктор Вислый-Анков");
        // пропускаем ввод номера
        driver.findElement(By.xpath("//label[@data-test-id='agreement']")).click();
        driver.findElement(By.xpath("//div[@class='form-field form-field_size_m form-field_theme_alfa-on-white']/descendant::button[@role='button']")).click();
        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.xpath("//span[@data-test-id='phone'][contains(@class,'input_invalid')]/descendant::span[@class='input__sub']")).getText();
        assertEquals(expected, actual);
    }

    @Test

    void shdTestFormSubmitWithZeroInput() {
        driver.get("http://localhost:9999/");
        // пропускаем ввод во все поля и нажатие на галку
        driver.findElement(By.xpath("//div[@class='form-field form-field_size_m form-field_theme_alfa-on-white']/descendant::button[@role='button']")).click();
        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.xpath("//span[@data-test-id='name'][contains(@class,'input_invalid')]/descendant::span[@class='input__sub']")).getText();
        assertEquals(expected, actual);
    }
}
