import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Properties;

public class LogInOTUSTest {
    private WebDriver driver;
    Properties property = new Properties(System.getProperties());

    private final String base_url = property.getProperty("base.url");
    private final String email = System.getProperty("login");
    private final String password = System.getProperty("pwd");

    @BeforeEach
//    Открыть Chrome
    public void openBrouser() {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("-fullscreen");
        driver = new FirefoxDriver(options);
    }
    @AfterEach
    public void closeBrouser() {
        if (this.driver != null) {
            this.driver.close();
            this.driver.quit();
        }
    }

    @Test
    public void loginOtusTest() {

        JavascriptExecutor js = (JavascriptExecutor) driver;

//   1. Открыть https://otus.ru
        driver.get(base_url);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));//ждем окончания загрузки сайта
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[contains(text(), 'Войти')]")));
//   2. Авторизоваться на сайте
        driver.findElement(By.xpath("//button[contains(text(), 'Войти')]")).click();//нажать кнопку войти
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath
                ("//div[contains(text(), 'Войдите в свой аккаунт')]")));//проверить, что открылось окно ввода логина/пароля
        //ввести логин пароль

        driver.findElement(By.xpath("//div[contains(text(), 'Войдите в свой аккаунт')]/..//label[contains(text(),'Электронная почта')]/..")).click();
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys(email);//login "dafome4086@aicogz.com"
//        driver.findElement(By.xpath("//input[@name='email']")).sendKeys("dafome4086@aicogz.com");//login "dafome4086@aicogz.com"
        driver.findElement(By.xpath("//div[contains(text(), 'Войдите в свой аккаунт')]/..//label[contains(text(),'Пароль')]/..")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type='password']")));
        driver.findElement(By.xpath("//input[@type='password']")).sendKeys(password);//pwd "OtusTest12#"
//        driver.findElement(By.xpath("//input[@type='password']")).sendKeys("OtusTest12#");//pwd "OtusTest12#"
        driver.findElement(By.xpath("//div[contains(text(), 'Войти')]"))
                .click();//нажать войти
        //проверяем, что вход успешный
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("img[src*='owl-']")));
//   3. Войти в личный кабинет
        driver.get(base_url + "/learning");
        //проверяем, что перешли в личный кабинет
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("h1.title__text")));
//    В разделе "О себе" заполнить все поля "Личные данные" и добавить не менее двух контактов
        //перейти в раздел "Осебе"
        driver.get(base_url + "/lk/biography/personal/");
        //проверяем, что перешли в раздел О себе
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("h3.text.text_h2.text_pad")));
        //Заполняем все поля "Личные данные"
        clearAndEnter(By.id("id_fname"),"Тест");
        clearAndEnter(By.id("id_lname"),"Тестов");
        clearAndEnter(By.id("id_blog_name"),"Test Testoff");
        clearAndEnter(By.id("id_fname_latin"),"Test");
        clearAndEnter(By.id("id_lname_latin"),"Testoff");
        clearAndEnter(By.cssSelector("input[name='date_of_birth']"),"12.10.2003");
        driver.findElement(By.cssSelector(".js-lk-cv>.container.container-padding-bottom")).click();


        driver.findElement(By.cssSelector("div[data-ajax-slave='/lk/biography/cv/lookup/cities/by_country/']")).click();
        wait.until(ExpectedConditions
                .presenceOfElementLocated(By.cssSelector(".lk-cv-block__select-scroll_country")));
        driver.findElement(By.cssSelector(".lk-cv-block__select-scroll_country.js-custom-select-options>button[data-value='2']")).click();
        driver.findElement(By.cssSelector(".js-lk-cv>.container.container-padding-bottom")).click();


        driver.findElement(By.xpath("//input[@data-title='Город']/../div")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("button[data-empty='Город']")));
        driver.findElement(By.cssSelector("button[title='Борисов']")).click();

       // wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[data-title='Уровень знания английского языка']")));
        driver.findElement(By.xpath("//input[@data-title='Уровень знания английского языка']/../div")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("button[title='Начальный уровень (Beginner)']")));
        driver.findElement(By.cssSelector("button[title='Начальный уровень (Beginner)']")).click();
       // wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.container__row.lk-cv-block__line.lk-cv-block__line_double")));

        driver.findElement(By.xpath("//input[@value='True']/..")).click();
        driver.findElement(By.xpath("//input[@title='Гибкий график']/..")).click();
        //добавить не менее двух контактов
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".hide[data-modal-id='change-phone']")));//переписать провер
        driver.findElement(By.xpath("//button[contains(text(),'Указать телефон')]")).click();
        wait.until()
//    Нажать сохранить
//    Открыть https://otus.ru в "чистом браузере"
//    Авторизоваться на сайе
//    Войти в личный кабинет
//    Проверить, что в разделе "О себе" отображаются указанные ранее данные
    }
    private void clearAndEnter(By by, String text){
        driver.findElement(by).clear();
        driver.findElement(by).sendKeys(text);
    }

}
