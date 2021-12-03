package lib.UI;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends TestFunctions{
    public ArticlePageObject(AppiumDriver driver)
    {
        super(driver);
    }

    private static final String
        TITLE = "org.wikipedia:id/view_page_title_text",
        FOOTER_ELEMENT = "//*[@text = 'View page in browser']";
    public WebElement waitTitleElement()
    {
        return this.waitForElementPresent(By.id(TITLE),"can not find title",5);
    }

    public String getArticleTitle()
    {
        WebElement title_element = waitTitleElement();
        return title_element.getAttribute("text");
    }

    public void swipeToFooter()
    {
        this.swipingUpToFindElement(By.xpath(FOOTER_ELEMENT),"can not swipe", 20);


    }
}
