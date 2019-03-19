import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class TestRGS extends BaseTest {

    @Test
    public void rgs(){
        System.out.println("Тестовое задание для сайта РГС");

        click(By.xpath("//ol/li/a[contains(text(),'Страхование')]"));

        click(By.xpath("//*[@class='hidden-xs'][contains(text(),'Путешествия')]"));

        click(By.xpath("//*[contains(text(), 'Страхование выезжающих за')]"));

        WebElement element = driver.findElement(By.xpath("//a[contains(text(), 'Рассчитать')]"));
        scrollToElement(element);
        click(By.xpath("//a[contains(text(), 'Рассчитать')]"));

        compareText(driver.findElement(By.xpath("//div/*[contains(text(), 'Страхование выезжающих')]")).getText(),"Страхование выезжающих за рубеж");

        WebElement element1 = driver.findElement(By.xpath("//button/*[contains(@class, 'content-title')]"));
        scrollToElement(element1);
        click(By.xpath("//button/*[contains(@class, 'content-title')]"));

        fillForm(By.xpath("//*[@id='Countries']"),"Шенген");
        WebElement element2 = driver.findElement(By.xpath("//*[@id='Countries']"));
        element2.sendKeys(Keys.DOWN,Keys.ENTER);

        new Select(driver.findElement(By.name("ArrivalCountryList"))).selectByVisibleText("Испания");


        driver.findElement(By.xpath("//input[@data-test-name='FirstDepartureDate']")).click();
//        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        waitFieldisDisplayed(By.xpath("//input[@data-test-name='FirstDepartureDate']"));
        fillForm(By.xpath("//input[@data-test-name='FirstDepartureDate']"),setData());

        WebElement element3 = driver.findElement(By.xpath("//label[@class='btn btn-attention']"));
        scrollToElement(element3);
        click(By.xpath("//label[@class='btn btn-attention']"));

        fillForm(By.xpath("//input[@data-test-name='FullName']"),"IVANOV  IVAN");
        fillForm(By.xpath("//input[@data-test-name=\"BirthDate\"]"),"25101994");

        WebElement element4 = driver.findElement(By.xpath("//*[contains(text(), 'активный отдых или спорт')]/ancestor::div[@class=\"calc-vzr-toggle-risk-group\"]//div[@class=\"toggle off toggle-rgs\"]"));
        scrollToElement(element4);
        checkBoxCheck(element4);

        WebElement element5 = driver.findElement(By.xpath("//label[@class='adaptive-checkbox-label'][contains(text(),' Я согласен')]"));
        scrollToElement(element5);
        checkBoxCheck(element5);

        click(By.xpath("//button[@data-test-name='NextButton'][contains(text(),'Рассчитать')]"));

        waitFieldisDisplayed(By.xpath("//div[@class='program-name'][contains(text(),'Комфорт')]"));

        WebElement element6 = driver.findElement(By.xpath("//h2[@class='step-title'][contains(text(),'Расчет')]"));
        scrollToElement(element6);

        compareText(driver.findElement(By.xpath("//span[contains(text(),'Многократные поездки')]")).getText(),"Многократные поездки в течение года");
        compareText(driver.findElement(By.xpath("//strong[contains(text(),'Шенген')]")).getText(),"Шенген");
        compareText(driver.findElement(By.xpath("//strong[contains(text(),'IVANOV IVAN')]")).getText(),"IVANOV IVAN");
        compareText(driver.findElement(By.xpath("//strong[contains(text(),'25')]")).getText(),"25.10.1994");
        compareText(driver.findElement(By.xpath(" //div[@style=\"visibility: visible; opacity: 1; display: block; transform: translateX(0px);\"]//child::small[@data-bind=\"text: ko.unwrap('undefined' === typeof info ? '' : info)\"]")).getText(),"(включая активный отдых)");

    }
}
