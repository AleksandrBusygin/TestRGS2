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
                { "Австрия", "IVANOV IVAN" , "25.10.1994", false},
                { "Италия", "PETROV PETR", "13.01.1987", false},
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
    public void rgs() throws InterruptedException {

        new Select(driver.findElement(By.name("ArrivalCountryList"))).selectByVisibleText(country);

        WebElement elementDeparture = driver.findElement(By.xpath("//input[@data-test-name='FirstDepartureDate']"));
        elementDeparture.click();
        waitFieldisDisplayed(elementDeparture);
        isElementPresented(elementDeparture);
        fillForm(setData(),elementDeparture);

        WebElement element3 = driver.findElement(By.xpath("//label[@class='btn btn-attention']"));
        scrollToElement(element3);
        click(element3);

        WebElement elementFullName = driver.findElement(By.xpath("//input[@data-test-name='FullName']"));
        waitFieldisDisplayed(elementFullName);
        fillForm(name,elementFullName);

        WebElement elementBirthDate = driver.findElement(By.xpath("//input[@data-test-name=\"BirthDate\"]"));
        waitFieldisDisplayed(elementBirthDate);
        isElementPresented(elementBirthDate);
        fillForm(date, elementBirthDate);

        WebElement element4 = driver.findElement(By.xpath("//div[contains(@data-bind,'activeRestOrSportsToggle')]/div[contains(@class, 'toggle-rgs')]"));
        scrollToElement(element4);
        activeRelax(sport,element4);

        scrollToElement(driver.findElement(By.name("ArrivalCountryList")));
    }
}
