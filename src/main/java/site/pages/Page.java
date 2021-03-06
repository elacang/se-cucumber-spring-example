package site.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import site.applogic.InvisibilityOfElement;
import site.applogic.VisibilityOfElement;
import site.applogic.VisibilityOfElementLocated;
import site.util.PropertyLoader;
import webdriver.SharedDriver;

import javax.annotation.PostConstruct;

/*
 * Abstract Page. Page object pattern
 */
public abstract class Page {

    @Autowired
    private SharedDriver driver;

    private Wait<WebDriver> wait;
    private final String baseUrl = PropertyLoader.loadProperty("base.url");

    @PostConstruct
    public void init() {
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, 10, 500);
    }

	public WebDriver getWebDriver() {
		return driver;
	}

    /**
     * Open inner path of site
     * @param s inner path
     */
    protected void openAt(String s) {
        driver.get(baseUrl + s);
    }

    /**
     * Wait until element is present
     * @param element element
     * @return boolean
     */
    protected boolean waitForElementPresent(WebElement element) {
        Boolean result = true;
        try {
            wait.until(new VisibilityOfElement(element));
        } catch (TimeoutException e) {
            result = false;
        } catch (Throwable t) {
            throw new Error(t);
        }
        return result;
    }

    /**
     * Wait until element not present
     * @param element element
     * @return boolean
     */
    protected boolean waitForElementNotPresent(WebElement element) {
        Boolean result = true;
        try {
            wait.until(new InvisibilityOfElement(element));
        } catch (TimeoutException e) {
            result = false;
        } catch (Throwable t) {
            throw new Error(t);
        }
        return result;
    }

    /**
     * Wait until element present by locator
     * @param locator locator
     * @return boolean
     */
    protected boolean waitForElementPresent(By locator) {
        Boolean result = true;
        try {
            wait.until(new VisibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            result = false;
        } catch (Throwable t) {
            throw new Error(t);
        }
        return result;
    }

    /**
     * Type string into element
     * @param element element
     * @param s string
     */
    protected void typeInto(WebElement element, String s) {
        element.clear();
        element.sendKeys(s);
    }

    /**
     * Get attribute "value" of the element
     * @param element element
     * @return value
     */
    protected String getValue(WebElement element) {
        return element.getAttribute("value");
    }
}
