package lib.UI;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class SearchPageObject  extends TestFunctions{

    private static final String
                SEARCH_INIT_ELEMENT = "//*[contains(@text,'Search Wikipedia')]",
                SEARCH_INPUT = "//*[contains(@text,'Search…')]",
                SEARCH_RESULT_BY_SUBSTRING_TPL = "//*[@resource-id ='org.wikipedia:id/page_list_item_container']//*[@text ='{SUBSTRING}']",
                SEARCH_CANCEL_BUTTON = "org.wikipedia:id/search_close_btn";



    public SearchPageObject(AppiumDriver driver)
    {
        super(driver);
    }

/*TEMPLATES METHODS*/
    private static String getResultSearchElement(String substring)
    {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }
    /*TEMPLATES METHODS*/

    public void initSearchInput()
    {
        this.waitForElementPresent(By.xpath(SEARCH_INIT_ELEMENT), "can not find search input", 5);
        this.waitForElementAndClick(By.xpath(SEARCH_INIT_ELEMENT), "can not click search input",5);
    }

    public void typeSearchLine(String search_line)
    {
        this.waitForElementAndSendKeys(By.xpath(SEARCH_INPUT), search_line, "cannot type", 5);
    }

    public void waitForSearchResult(String substring)
    {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementPresent(By.xpath(search_result_xpath), "can not find search result", 5);
    }

    public void waitForCancelButtonToAppear()
    {
        this.waitForElementPresent(By.id(SEARCH_CANCEL_BUTTON), "can not find cancel button", 5);
    }

    public void clickCancelButton()
    {
        this.waitForElementAndClick(By.id(SEARCH_CANCEL_BUTTON), "can not click cancel button", 5);
    }

    public void waitForCancelButtonToDisappear()
    {
        this.waitForElementNotPresent(By.id(SEARCH_CANCEL_BUTTON), "cancel button is present", 5);
    }

    public void clickArticleWithSubstring(String substring) {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementAndClick(By.xpath(search_result_xpath), "can not find and click search result", 10);

    }


}
