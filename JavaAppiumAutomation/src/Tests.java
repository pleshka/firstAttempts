
import lib.CoreTestCase;
import lib.UI.ArticlePageObject;
import lib.UI.SearchPageObject;
import lib.UI.TestFunctions;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;

public class Tests extends CoreTestCase {

    private TestFunctions TestFunctions;

    protected void setUp() throws Exception
    {
        super.setUp();

        TestFunctions = new TestFunctions(driver);
    }

    @Test
    public void testSearch() {


        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("java");
        SearchPageObject.waitForSearchResult("Object-oriented programming language");
    }

    @Test
    public void testCancelSearch() {

        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.waitForCancelButtonToAppear();
        SearchPageObject.clickCancelButton();
        SearchPageObject.waitForCancelButtonToDisappear();
    }

    @Test
    public void testCompareArticleTitle() {

        SearchPageObject SearchPageObject = new SearchPageObject(driver);


        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("java");
        SearchPageObject.clickArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        ArticlePageObject.waitTitleElement();
        String article_title = ArticlePageObject.getArticleTitle();

        Assert.assertEquals(
                "unexpected title",
                "Java (programming language)",
                article_title
        );
    }

    @Test
    public void testCompareSearchText() {
        TestFunctions.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find search input",
                5
        );

        TestFunctions.assertElementHasText(
                By.id("org.wikipedia:id/search_src_text"),
                "Search…",
                "Element does not correspond expected",
                5
        );
    }


    @Test
    public void testFindCancelSearch() {
        TestFunctions.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find search input",
                5
        );

        TestFunctions.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "657676575765",
                "Cannot find search input",
                5
        );

        TestFunctions.waitForElementNotPresent(
                By.id("org.wikipedia:id/search_empty_view"),
                "No search results has been found",
                10
        );

        TestFunctions.waitForElementNotPresent(
                By.id("org.wikipedia:id/search_empty_image"),
                "No search results has been found",
                10
        );

        TestFunctions.waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find X to cancel search ",
                5
        );

        TestFunctions.waitForElementNotPresent(
                By.id("org.wikipedia:id/search_results_list"),
                "Search result did not disappeared",
                10
        );

    }

    @Test
    public void testFindCancelSearchByAbsence() // проверяет что разультат поиска есть опираясь на отсутствие елементов:
    //так как результат может отсутствовать по причине пустого поля поиска
    // и отсутствию результатов поиска
    {
        TestFunctions.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find search input",
                5
        );

        TestFunctions.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "1488",
                "Cannot find search input",
                5
        );

        TestFunctions.waitForElementsNotPresent(
                By.id("org.wikipedia:id/search_empty_view"),
                By.id("org.wikipedia:id/search_empty_image"),
                "No search results has been found",
                10
        );

        TestFunctions.waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find X to cancel search ",
                5
        );

        TestFunctions.waitForElementNotPresent(
                By.id("org.wikipedia:id/search_results_list"),
                "Search result did not disappeared",
                10
        );

    }

    @Test
    public void testFindCancelSearchByListItem() //проверяет что разультат поиска есть опираясь на его присутствие
    {
        TestFunctions.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find search input",
                5
        );

        TestFunctions.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "1488",
                "Cannot find search input",
                5
        );

        TestFunctions.waitForElementPresent(
                By.xpath("//*[@resource-id ='org.wikipedia:id/page_list_item_container']"),
                "No search results",
                10
        );

        TestFunctions.waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find X to cancel search ",
                5
        );

        TestFunctions.waitForElementNotPresent(
                By.id("org.wikipedia:id/search_results_list"),
                "Search result did not disappeared",
                10
        );

    }

    /*@Test
    public void testCompareArticleText()
    {
        TestFunctions.waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find wiki input",
                5
        );

        TestFunctions.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "1488",
                "Cannot find search input",
                5
        );

        List<WebElement> elements = driver.findElements(By.id("org.wikipedia:id/page_list_item_container"));
        assertThat(elements, containsString("1488"));

        TestFunctions.checkTextPresent(
                By.id("org.wikipedia:id/page_list_item_title"),
                "1480",
                "kal",
                5
        );
    }*/

    @Test
    public void testSwipeArticle() {

        SearchPageObject SearchPageObject = new SearchPageObject(driver);


        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Appium");
        SearchPageObject.clickArticleWithSubstring("Appium");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        ArticlePageObject.waitTitleElement();
        ArticlePageObject.swipeToFooter();
    }

    @Test
    public void testSaveFirstArticleToMyList() {

        TestFunctions.waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find wiki input",
                5
        );

        TestFunctions.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "Cannot find search input",
                5
        );

        TestFunctions.waitForElementAndClick(
                By.xpath("//*[@resource-id ='org.wikipedia:id/page_list_item_container']//*[@text ='Object-oriented programming language']"),
                "Cannot find wiki input",
                5
        );

        TestFunctions.waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "can't find title",
                15
        );

        TestFunctions.waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "can not open options",
                5
        );

        TestFunctions.waitForMenuToRender(
                By.id("org.wikipedia:id/title"),
                "",
                15
        );

        TestFunctions.waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "can not find Add to reading list",
                10
        );

        TestFunctions.waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "can not find got it button",
                5
        );

        TestFunctions.waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "can not find text field",
                2
        );

        TestFunctions.waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                "popka",
                "can not find text field",
                2
        );

        TestFunctions.waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "can not find OK button",
                5
        );

        TestFunctions.waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "can not find X button",
                5
        );

        TestFunctions.swipeDown(
                1
        );

        TestFunctions.waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "can not find 'MY LISTS' button",
                15
        );

        TestFunctions.waitForElementAndClick(
                By.xpath("//*[@text='popka']"),
                "can not find popka",
                5
        );

        TestFunctions.swipeElementToLeft(
                By.xpath("//*[@text='popka']"),
                "cannot find popka"
        );

        TestFunctions.waitForElementNotPresent(
                By.xpath("//*[@text='popka']"),
                "there is popka",
                5
        );


    }

    @Test
    public void testOfNonEmptySearchResult()
    {
        TestFunctions.waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find wiki input",
                5
        );

        String search_input = "java";
        TestFunctions.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                search_input,
                "Cannot find search input",
                5
        );
        String search_list = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
        TestFunctions.waitForElementPresent(
                By.xpath(search_list),
                "popa",
                15
        );
        int amount_of_search_results = TestFunctions.getAmountOfElements(By.xpath(search_list));
        System.out.println(amount_of_search_results);
        Assert.assertTrue(
                "kakasgkaaaaaaaaa",
                amount_of_search_results > 0
        );
    }

    @Test
    public void testAmountOfEmptySearch()
    {
        TestFunctions.waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find wiki input",
                5
        );

        String search_input = "dfjknfhbbhv";
        TestFunctions.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                search_input,
                "Cannot find search input",
                5
        );
        String search_list = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
        String empty_result_label ="//*[@text='No results found']";
        TestFunctions.waitForElementPresent(
                    By.xpath(empty_result_label),
                    "kal",
                5
            );

        TestFunctions.assertElementNotPresent(
                    By.xpath(search_list),
                    "govno"
            );

    }

    @Test
    public void testChangeScreenOrientation()
    {

            TestFunctions.waitForElementAndClick(
                    By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                    "Cannot find wiki input",
                    5
            );

            String search_input = "java";
            TestFunctions.waitForElementAndSendKeys(
                    By.xpath("//*[contains(@text,'Search…')]"),
                    search_input,
                    "Cannot find search input",
                    5
            );
            TestFunctions.waitForElementAndClick(
                    By.xpath("//*[@resource-id ='org.wikipedia:id/page_list_item_container']//*[@text ='Object-oriented programming language']"),
                    "Cannot find wiki input",
                    15
            );
            String title_before_rotation = TestFunctions.waitForElementAndGetAttribute(
                    By.id("org.wikipedia:id/view_page_title_text"),
                    "text",
                    "can not find article title",
                    15
            );

            driver.rotate(ScreenOrientation.LANDSCAPE);


            String title_after_rotation = TestFunctions.waitForElementAndGetAttribute(
                    By.id("org.wikipedia:id/view_page_title_tex"),
                    "text",
                    "can not find article title",
                    15
            );

            Assert.assertEquals(
                    "title changed after rotation",
                    title_before_rotation,
                    title_after_rotation
            );

            driver.rotate(ScreenOrientation.PORTRAIT);

            String title_after_second_rotation = TestFunctions.waitForElementAndGetAttribute(
                    By.id("org.wikipedia:id/view_page_title_text"),
                    "text",
                    "can not find article title",
                    15
            );

            Assert.assertEquals(
                    "title changed after rotation",
                    title_before_rotation,
                    title_after_second_rotation
            );

    }

    @Test
    public void testArticleSearchInBackground()
    {

        driver.rotate(ScreenOrientation.PORTRAIT);

        TestFunctions.waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find wiki input",
                5
        );

        TestFunctions.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "Cannot find search input",
                5
        );

        TestFunctions.waitForElementPresent(
                By.xpath("//*[@resource-id ='org.wikipedia:id/page_list_item_container']//*[@text ='Object-oriented programming language']"),
                "Cannot find wiki input",
                5
        );

        driver.runAppInBackground(2);

        TestFunctions.waitForElementPresent(
                By.xpath("//*[@resource-id ='org.wikipedia:id/page_list_item_container']//*[@text ='Object-oriented programming language']"),
                "Cannot find wiki input after backgrounding",
                5
        );
    }

    @Test
    public void testSaveTwoArticlesToMyList() {
        TestFunctions.waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find wiki input",
                5
        );

        TestFunctions.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "Cannot find search input",
                5
        );

        TestFunctions.waitForElementAndClick(
                By.xpath("//*[@resource-id ='org.wikipedia:id/page_list_item_container']//*[@text ='Object-oriented programming language']"),
                "Cannot find wiki input",
                5
        );

        TestFunctions.waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "can't find title",
                15
        );

        TestFunctions.waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "can not open options",
                5
        );

        TestFunctions.waitForMenuToRender(
                By.id("org.wikipedia:id/title"),
                "",
                15
        );

        TestFunctions.waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "can not find Add to reading list",
                10
        );

        TestFunctions.waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "can not find got it button",
                5
        );

        TestFunctions.waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "can not find text field",
                2
        );

        TestFunctions.waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                "popka",
                "can not find text field",
                2
        );

        TestFunctions.waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "can not find OK button",
                5
        );

        TestFunctions.waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "can not find X button",
                5
        );

        // add second article start

        TestFunctions.waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find wiki input",
                5
        );

        TestFunctions.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "69",
                "Cannot find search input",
                5
        );

        TestFunctions.waitForElementAndClick(
                By.xpath("//*[@resource-id ='org.wikipedia:id/page_list_item_container']//*[@text ='69 Love Songs']"),
                "Cannot find second wiki input",
                5
        );

        TestFunctions.waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "can't find title",
                15
        );

        TestFunctions.waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "can not open options",
                5
        );

        TestFunctions.waitForMenuToRender(
                By.id("org.wikipedia:id/title"),
                "",
                15
        );

        TestFunctions.waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "can not find Add to reading list",
                10
        );

        TestFunctions.waitForElementAndClick(
                By.xpath("//*[@text='Create new']"),
                "can not find create new list button",
                5
        );

        TestFunctions.waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "can not find text field",
                2
        );

        TestFunctions.waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                "popka 2",
                "can not find text field",
                2
        );

        TestFunctions.waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "can not find OK button",
                5
        );

        TestFunctions.waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "can not find X button",
                5
        );

        TestFunctions.waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "can not find 'MY LISTS' button",
                15
        );

        //шаги на экране избранного

        TestFunctions.waitForElementAndClick(
                By.xpath("//*[@text='popka']"),
                "can not find popka",
                5
        );

        TestFunctions.swipeElementToLeft(
                By.xpath("//*[@text='Object-oriented programming language']"),
                "cannot find popka"
        );

        TestFunctions.waitForElementNotPresent(
                By.xpath("//*[@text='popka']"),
                "there is popka",
                5
        );

        TestFunctions.waitForElementAndClick(
                By.xpath("//*[@text='popka 2']"),
                "can not find popka",
                5
        );

        TestFunctions.waitForElementPresent(
                By.xpath("//*[@text='69 Love Songs']"),
                "cannot find 69 Love Songs",
                10
        );

    }

    @Test
    public void testAssertTitlePresent()
    {
        TestFunctions.waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find wiki input",
                5
        );

        TestFunctions.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "Cannot find search input",
                5
        );

        TestFunctions.waitForElementAndClick(
                By.xpath("//*[@resource-id ='org.wikipedia:id/page_list_item_container']//*[@text ='Object-oriented programming language']"),
                "Cannot find wiki input",
                5
        );

        TestFunctions.assertElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "can t find title"
        );
    }

}