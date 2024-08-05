package core;

import org.openqa.selenium.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class Waiter {

    private final WebDriver driver;
    private final JavascriptExecutor jsExec;
    private final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(30);

    public Waiter(WebDriver driver) {
        this.driver = driver;
        this.jsExec = (JavascriptExecutor) driver;
    }

    // Waiting method for whole browser page
    public void waitForLoad() {
        waitUntilJSReady();
        waitUntilJQueryReady();
        waitForChildNodes();
    }

    private void waitForJQueryLoad() {
        //Wait for jQuery to load
        ExpectedCondition<Boolean> jQueryLoad = driver -> ((Long) jsExec
                .executeScript("return jQuery.active") == 0);

        //Get JQuery is Ready
        boolean jqueryReady = (Boolean) jsExec.executeScript("return jQuery.active==0");

        //Wait JQuery until it is Ready!
        if (!jqueryReady) {
            try {
                // Wait for jQuery to load
                newWait(DEFAULT_TIMEOUT).until(jQueryLoad);
            } catch (TimeoutException e) {
                throw new AssertionError("jQuery loading isn't completed after 120 seconds!");
            }
        }
    }

    //Wait for Child Nodes to be present Load
    private void waitForChildNodes() {
        String childNodesReadyScript = "return window.document.hasChildNodes()";

        //Wait for ANGULAR to load
        ExpectedCondition<Boolean> nodesLoad = driver -> Boolean.valueOf(((JavascriptExecutor) driver)
                .executeScript(childNodesReadyScript).toString());

        //Get Angular is Ready
        boolean angularReady = (Boolean) (jsExec.executeScript(childNodesReadyScript));

        //Wait ANGULAR until it is Ready!
        if (!angularReady) {
            //Wait for Angular to load
            newWait(DEFAULT_TIMEOUT).until(nodesLoad);
        }
    }

    //Wait Until JS Ready
    private void waitUntilJSReady() {
        //Wait for Javascript to load
        ExpectedCondition<Boolean> jsLoad = driver -> jsExec.executeScript("return document.readyState").toString().equals("complete");

        //Get JS is Ready
        boolean jsReady = (Boolean) jsExec.executeScript("return document.readyState == 'complete'");

        //Wait Javascript until it is Ready!
        if (!jsReady) {
            //Wait for Javascript to load
            newWait(DEFAULT_TIMEOUT).until(jsLoad);
        }
    }

    //Wait Until JQuery and JS Ready
    private void waitUntilJQueryReady() {
        //First check that JQuery is defined on the page. If it is, then wait AJAX
        Boolean jQueryDefined = (Boolean) jsExec.executeScript("return typeof jQuery != 'undefined'");
        if (jQueryDefined == true) {
            //Wait JQuery Load
            waitForJQueryLoad();

            //Wait JS Load
            waitUntilJSReady();
        }
    }

    public List<WebElement> forAllElementsVisible(By selector) {
        return newWait(DEFAULT_TIMEOUT).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(selector));
    }

    // Checking element presence on page
    public WebElement forElementPresentBy(By selector) {
        return newWait(DEFAULT_TIMEOUT).until(ExpectedConditions.presenceOfElementLocated(selector));
    }

    public WebElement forElementVisibleBy(By selector) {
        return newWait(DEFAULT_TIMEOUT).until(ExpectedConditions.visibilityOfElementLocated(selector));
    }

    public WebElement forElementVisible(WebElement element) {
        return newWait(DEFAULT_TIMEOUT).ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.visibilityOf(element));
    }

    public WebElement forChildElementVisibleBy(WebElement root, By selector) {
        return forElementVisible(root.findElement(selector));
    }

    public WebElement forChildElementVisibleBy(Duration timeout, WebElement root, By selector) {
        return forElementVisible(timeout, root.findElement(selector));
    }

    public List<WebElement> forAllChildElementsPresent(WebElement root, By selector) {
        newWait(DEFAULT_TIMEOUT).until(ExpectedConditions.presenceOfAllElementsLocatedBy(selector));
        return root.findElements(selector);
    }

    public WebElement forElementVisible(Duration timeout, WebElement element) {
        return newWait(timeout).ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.visibilityOf(element));
    }

    public WebElement forElementSafelyClicked(By selector) {
        final var element = forElementVisibleBy(selector);
        return forElementSafelyClicked(element);
    }

    public WebElement forElementSafelyClicked(WebElement element) {
        final var elm = newWait(DEFAULT_TIMEOUT).until(ExpectedConditions.elementToBeClickable(element));
        elm.click();
        waitForLoad();
        return elm;
    }

    public WebElement forChildElementSafelyClicked(WebElement root, By selector) {
        return forElementSafelyClicked(root.findElement(selector));
    }



    public WebDriverWait newWait(Duration timeout) {
        return new WebDriverWait(driver, timeout);
    }
}
