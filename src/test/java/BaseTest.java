import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class BaseTest {

    public static WebDriver driver;

    @Before
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "drv/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get("https://www.rgs.ru");
    }

    @AfterClass
    public static void close() {
        driver.quit();
    }

    public void fillForm(By locator, String text){
        driver.findElement(locator).click();
        driver.findElement(locator).clear();
        driver.findElement(locator).sendKeys(text);

    }
    public void compareText(String actual, String expected) {
        Assert.assertTrue(("Искомого текста нет: " + expected + " вместо него " + actual), actual.contains(expected));
        System.out.println("Искомый текст есть: " + expected);
    }

    public String setData(){
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd.MM.yyyy");
        Calendar i = Calendar.getInstance();
        i.setTime(new Date());
        i.add(Calendar.DAY_OF_MONTH, 14);
        String newDate = formatForDateNow.format(i.getTime());
        return newDate;
    }

    public void checkBoxCheck(WebElement checkbox){
        if(!checkbox.isSelected()){
            checkbox.click();
        }
    }
    public void waitFieldisDisplayed(By locator) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until((WebDriver d) -> d.findElement(locator).isDisplayed());
            return;
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
        Assert.fail("Поле не отображено");
    }

    public boolean isElementPresented(By locator) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until((WebDriver d) -> d.findElement(locator));
            return true;
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void click(By locator) {
        if (!isElementPresented(locator)) {
        }
        waitFieldisDisplayed(locator);
        driver.findElement(locator).click();
    }

    public void scrollToElement(WebElement find){
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", find);
        Wait<WebDriver> wait = new WebDriverWait(driver, 20, 1000);
        wait.until(ExpectedConditions.visibilityOf(find));
    }
}
