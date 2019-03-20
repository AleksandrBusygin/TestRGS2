import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class TestRGS extends BaseTest {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { "Австрия", "IVANOV IVAN" , "25.10.1994", true},
                { "Италия", "PETROV PETR", "13.01.1987", false },
                { "Германия", "PUTIN VOVA", "02.07.1992", true }});
    }

    @Parameterized.Parameter
    public String country;
    @Parameterized.Parameter(1)
    public String name;
    @Parameterized.Parameter(2)
    public String date;
    @Parameterized.Parameter(3)
    public boolean sport;

    @Test
    public void rgs(){

        new Select(driver.findElement(By.name("ArrivalCountryList"))).selectByVisibleText(country);

        driver.findElement(By.xpath("//input[@data-test-name='FirstDepartureDate']")).click();
        waitFieldisDisplayed(By.xpath("//input[@data-test-name='FirstDepartureDate']"));
        isElementPresented(By.xpath("//input[@data-test-name='FirstDepartureDate']"));
        fillForm(By.xpath("//input[@data-test-name='FirstDepartureDate']"),setData());

        WebElement element3 = driver.findElement(By.xpath("//label[@class='btn btn-attention']"));
        scrollToElement(element3);
        click(By.xpath("//label[@class='btn btn-attention']"));

        waitFieldisDisplayed(By.xpath("//input[@data-test-name='FullName']"));
        fillForm(By.xpath("//input[@data-test-name='FullName']"),name);
        waitFieldisDisplayed(By.xpath("//input[@data-test-name=\"BirthDate\"]"));
        isElementPresented(By.xpath("//input[@data-test-name=\"BirthDate\"]"));
        fillForm(By.xpath("//input[@data-test-name=\"BirthDate\"]"),date);

        WebElement element4 = driver.findElement(By.xpath("//*[contains(text(), 'активный отдых или спорт')]/ancestor::div[@class=\"calc-vzr-toggle-risk-group\"]//div[@class=\"toggle off toggle-rgs\"]"));
        scrollToElement(element4);
        activeRelax(sport,element4);
    }
}
