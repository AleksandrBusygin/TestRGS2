import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


public class BaseTest {

    public static WebDriver driver;
    public static Properties properties = TestProperties.getInstance().getProperties();
    protected static String baseUrl;

    @BeforeClass
    public static void setup() {
        switch (properties.getProperty("browser2")) {
            case "firefox":
                System.setProperty("webdriver.gecko.driver", properties.getProperty("webdriver.gecko.driver"));
                driver = new FirefoxDriver();
                break;
            case "chrome":
                System.setProperty("webdriver.chrome.driver", properties.getProperty("webdriver.chrome.driver"));
                driver = new ChromeDriver();
                break;
            case "explorer":
                System.setProperty("webdriver.ie.driver", properties.getProperty("webdriver.ie.driver"));
                driver = new InternetExplorerDriver();
                break;
        }
        baseUrl = properties.getProperty("app.url");
        driver.get(baseUrl);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }
    @BeforeClass
    public static void rgs1() throws InterruptedException {
        System.out.println("Тестовое задание для сайта РГС");

        WebElement elementSt = driver.findElement(By.xpath("//ol/li/a[contains(text(),'Страхование')]"));
        click(elementSt);

        WebElement elementPt = driver.findElement(By.xpath("//*[@class='hidden-xs'][contains(text(),'Путешествия')]"));
        click(elementPt);

        WebElement elementStz = driver.findElement(By.xpath("//*[contains(text(), 'Страхование выезжающих за')]"));
        click(elementStz);

        WebElement element = driver.findElement(By.xpath("//a[contains(text(), 'Рассчитать')]"));
        scrollToElement(element);
        click(element);

        WebElement head = driver.findElement(By.xpath("//div[@class='page-header']/span[@class='h1']"));
        waitFieldisDisplayed(head);
        compareText(head.getText(),"Страхование выезжающих за рубеж");

        WebElement element1 = driver.findElement(By.xpath("//button/*[contains(@class, 'content-title')]"));
        scrollToElement(element1);
        click(element1);

        WebElement elementSh = driver.findElement(By.xpath("//*[@id='Countries']"));
        fillForm("Шенген", elementSh);
        elementSh.sendKeys(Keys.DOWN,Keys.ENTER);
    }

    @AfterClass
    public static void close() {
        WebElement element5 = driver.findElement(By.xpath("//label[@class='adaptive-checkbox-label'][contains(text(),' Я согласен')]"));
        scrollToElement(element5);
        checkBoxCheck(element5);

        WebElement elementR = driver.findElement(By.xpath("//button[@data-test-name='NextButton'][contains(text(),'Рассчитать')]"));
        click(elementR);

        WebElement elementK = driver.findElement(By.xpath("//div[@class='program-name'][contains(text(),'Комфорт')]"));
        waitFieldisDisplayed(elementK);

        WebElement element6 = driver.findElement(By.xpath("//h2[@class='step-title'][contains(text(),'Расчет')]"));
        scrollToElement(element6);

        compareText(driver.findElement(By.xpath("//span/span[@class='text-bold']/parent::span")).getText(),"Многократные поездки в течение года");
         //span[contains(text(),'Многократные поездки')]
        compareText(driver.findElement(By.xpath("//span/strong[@data-bind='text: Name']")).getText(),"Шенген");
        //span/strong[@data-bind='text: Name'] //strong[contains(text(),'Шенген')]
        compareText(driver.findElement(By.xpath("//strong[@data-bind=\"text: LastName() + ' ' + FirstName()\"]")).getText(),"PUTIN VOVA");
        compareText(driver.findElement(By.xpath("//strong[@data-bind=\" text: BirthDay.repr('moscowRussianDate')\"]")).getText(),"02.07.1992");
        compareText(driver.findElement(By.xpath("//div[@style=\"visibility: visible; opacity: 1; display: block; transform: translateX(0px);\"]//child::small[@data-bind=\"text: ko.unwrap('undefined' === typeof info ? '' : info)\"]")).getText(),"(включая активный отдых)");

        driver.quit();
    }

        public static void fillForm(String text, WebElement element) throws InterruptedException {
        while (!(element.getAttribute("value").equals(text))) {
            scrollToElement(element);
            element.click();
            element.clear();
            Thread.sleep(3);
            element.sendKeys(text);
        }
    }

    public static void compareText(String actual, String expected) {
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

    public static void checkBoxCheck(WebElement checkbox){
        if(!(checkbox.isSelected())){
            checkbox.click();
        }
    }
    public static void checkBoxUnCheck(WebElement checkbox){
        if(checkbox.isSelected()){
            checkbox.click();
        }
    }

    public static void activeRelax(boolean cl, WebElement webElement){
        if(cl == true){
            checkBoxCheck(webElement);
        }
        if (cl == false) {
            checkBoxUnCheck(webElement);
        }
    }

    public static void waitFieldisDisplayed(WebElement element) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until((WebDriver d) -> element.isDisplayed());
            return;
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
        Assert.fail("Поле не отображено");
    }

    public static boolean isElementPresented(WebElement element) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until((WebDriver d) -> element);
            return true;
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void click(WebElement element) {
        if (!isElementPresented(element)) {
        }
        waitFieldisDisplayed(element);
        element.click();
    }

    public static void scrollToElement(WebElement find){
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", find);
        Wait<WebDriver> wait = new WebDriverWait(driver, 20, 1000);
        wait.until(ExpectedConditions.visibilityOf(find));
    }
}
