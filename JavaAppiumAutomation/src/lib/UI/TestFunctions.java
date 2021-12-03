package lib.UI;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import lib.CoreTestCase;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;


public class TestFunctions extends CoreTestCase {

    protected AppiumDriver driver;

    public  TestFunctions(AppiumDriver driver)
    {
        this.driver = driver;
    }


    //Метод проверки присутствия елемента
    public   WebElement waitForElementPresent(By by, String error_message, long timeoutIntSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutIntSeconds);
        wait.withMessage(error_message + "/n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }


    //Метод для тапа по елементу
    public   WebElement waitForElementAndClick(By by, String error_message, long timeoutIntSeconds)
    {
        WebElement element = waitForElementPresent(by, error_message , 15);
        element.click();
        System.out.println(element);
        return element;
    }

    //Метод для вставки текста и скрытия клавиатуры
    public   WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutIntSeconds) {
        WebElement element = waitForElementPresent(by, error_message, 5);
        element.sendKeys(value);
        return element;
    }

    //Метод для проверки отсутствия елемента
    public  boolean waitForElementNotPresent(By by, String error_message, long timeoutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    //Метод для отчистки поля
     public  WebElement waitForElementAndClear(By by, String error_message, long timeoutIntSeconds) {
         WebElement element = waitForElementPresent(by, error_message, 5);
         element.clear();
         return element;
     }

     //Метод для проверки соответствия текста
    public WebElement assertElementHasText(By by, String inputText, String error_message, long timeoutIntSeconds){

        WebElement title_element = waitForElementPresent(by, error_message, timeoutIntSeconds);

        String article_title = title_element.getAttribute("text");

        Assert.assertEquals(
                "unexpected title",
                article_title,
                inputText
        );
         return title_element;
    }

    //Метод для проверки отсутствия двух елементов
    public  boolean waitForElementsNotPresent(By by, By bi, String error_message, long timeoutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return (
                wait.until
                (ExpectedConditions.invisibilityOfElementLocated(by)) &&
                wait.until
                (ExpectedConditions.invisibilityOfElementLocated(bi))
        );
    }

  /*  public  void checkTextPresent(By by, String input_text, String error_message, long timeoutInSeconds)
    {
        List<WebElement> elements = driver.findElements(By.id("org.wikipedia:id/page_list_item_container"));

        WebElement total_element = waitForElementPresent(by, error_message);
        String  expected_text = total_element.getAttribute("text");
        System.out.println(elements);
        System.out.println(expected_text);
        for (WebElement i : elements)
        {
            assertElementHasText(elements(expected_text) , containsString(input_text));
        }
    }

    private List<WebElement> elements(String expected_text) {
        return elements(expected_text);
    }

    private void assertElementHasText(List<WebElement> elements, Matcher<String> containsString) {
    }*/

    public void swipeUp(int timeOfSwipe)
    {
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();
        int x = (int) (size.width/2);
        int start_y = (int) (size.height * 0.8);
        int end_y = (int) (size.height * 0.2);
        action.
                press(x, start_y).
                waitAction(timeOfSwipe).
                moveTo(x, end_y).
                release().
                perform();
    }

    public  void swipeUpQuick()
    {
        swipeUp(200);
    }

    public  void swipingUpToFindElement(By by, String error_message, int max_swipes)
    {
        int already_swiped = 0;
        while (driver.findElements(by).size() == 0){
            if (already_swiped > max_swipes){
                waitForElementPresent(by , "can not find element by swiping", 5);
                return;
            }
            swipeUpQuick();
            ++already_swiped;
        }
    }

    public void swipeElementToLeft(By by, String error_message)
    {
        WebElement element =waitForElementPresent(
                by,
                error_message,
                10);
        int left_x = element.getLocation().getX();
        int right_x = left_x + element.getSize().getWidth();
        int upper_y = element.getLocation().getX();
        int lower_y = upper_y + element.getSize().getHeight();
        int middle_y = (upper_y + lower_y) / 2;

        TouchAction action = new TouchAction(driver);
        action.
                press(right_x, middle_y).
                waitAction(150).
                moveTo(left_x, middle_y).
                perform();
    }

    public int getAmountOfElements(By by)
    {
        List elements = driver.findElements(by);
        return elements.size();
    }

    public void assertElementNotPresent(By by, String error_message)
    {
        int amount_of_elements = getAmountOfElements(by);
        if (amount_of_elements > 0) {
            String default_message = "this'" + by.toString() + "'should not be here" ;
            throw new AssertionError(default_message+ "" + error_message);
        }
    }

    public String waitForElementAndGetAttribute(By by, String attribute, String error_message, long timeout)
    {
        WebElement element = waitForElementPresent(by, error_message, timeout);
         return element.getAttribute(attribute);
    }

    public List<WebElement> waitForMenuToRender(By by, String error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "/n");
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
    }

    public void assertElementPresent(By by, String error_message)
    {
        int amount_of_elements = getAmountOfElements(by);
        if (amount_of_elements < 1) {
            String default_message = "this'" + by.toString() + "'should not be here" ;
            throw new AssertionError(default_message+ "" + error_message);
        }
    }

    public void swipeDown(int timeOfSwipe)
    {
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();
        int x =  (size.width/2);
        int start_y = (int) (size.height * 0.5);
        int end_y = (int) (size.height * 0.7);
        action.
                press(x, start_y).
                waitAction(timeOfSwipe).
                moveTo(x, end_y).
                release().
                perform();
    }



}
