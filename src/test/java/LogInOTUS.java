import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Properties;

public class LogInOTUS {
    private WebDriver driver;
    Properties property = new Properties(System.getProperties());

    private final String base_url = property.getProperty("base.url");
    private final String email = System.getProperty("login");
    private final String password = System.getProperty("pwd");
    @BeforeEach
//    Открыть Chrome
    public void openBrouser() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
    }
    @AfterEach
    public void closeBrouser(){
        if(this.driver != null) {
            this.driver.close();
            this.driver.quit();
        }

    @Test
    public void loginOtus() {
//   1. Открыть https://otus.ru
        driver.get(base_url);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
//   2. Авторизоваться на сайте
        driver.findElement(By.cssSelector(".header3__button-sign-in")).click();//нажать кнопку войти
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath
                ("//*[contains(text(), 'Войдите в свой аккаунт')]")));//проверить, что открылось окно ввода логина/пароля
        //ввести логин пароль
        driver.findElement(By.cssSelector("input.js-email-input[placeholder='Электронная почта']")).sendKeys(email);//login "dafome4086@aicogz.com"
        driver.findElement(By.cssSelector("input.js-psw-input[placeholder='Введите пароль']")).sendKeys(password);//pwd "OtusTest12#"
        driver.findElement(By.cssSelector(".new-input-line_relative>button.new-button_md")).click();//нажать войти
        //проверяем, что вход успешный
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".header3__user-info-name")));
//   3. Войти в личный кабинет

//    В разделе "О себе" заполнить все поля "Личные данные" и добавить не менее двух контактов
//    Нажать сохранить
//    Открыть https://otus.ru в "чистом браузере"
//    Авторизоваться на сайе
//    Войти в личный кабинет
//    Проверить, что в разделе "О себе" отображаются указанные ранее данные
    }


    }
}
