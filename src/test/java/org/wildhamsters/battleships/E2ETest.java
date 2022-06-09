package org.wildhamsters.battleships;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertTrue;

@Test
public class E2ETest {

    private final FirefoxDriver firefoxDriver = new FirefoxDriver();
    private final ChromeDriver chromeDriver = new ChromeDriver();

    public void twoPlayersGameTest() {
        String url = "http://localhost:5000/login";
        BrowserPlayer chromePlayer = new BrowserPlayer(chromeDriver);
        BrowserPlayer firefoxPlayer = new BrowserPlayer(firefoxDriver);
        firefoxPlayer.wakeUpServer(url);

        firefoxPlayer.login(url, "admin", "admin");
        firefoxPlayer.enterGameRoom();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.getMessage();
        }
        chromePlayer.login(url, "user", "user");
        chromePlayer.enterGameRoom();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.getMessage();
        }
        testLoop(firefoxPlayer, chromePlayer);

        boolean isGameFinished = firefoxDriver.findElements(By.tagName("img")).size() > 0 &&
                chromeDriver.findElements(By.tagName("img")).size() > 0;
        assertTrue(isGameFinished);
    }

    private void testLoop(BrowserPlayer firefoxPlayer, BrowserPlayer chromePlayer) {
        WebElement page = firefoxDriver.findElementById("page");

        boolean loop;
        do {
            if (firefoxPlayer.isInTurn()) {
                firefoxPlayer.makeShot();
            } else if (chromePlayer.isInTurn()) {
                chromePlayer.makeShot();
            }
            firefoxDriver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            loop = !page.getAttribute("class").contains("centered");
        } while (loop);
    }
}

class BrowserPlayer {

    private final RemoteWebDriver driver;

    BrowserPlayer(RemoteWebDriver driver) {
        this.driver = driver;
    }

    void wakeUpServer(String url) {
        while (!isServerAwake(url)) {
            driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        }
    }

    private boolean isServerAwake(String url) {
        driver.get(url);
        String title = driver.getTitle();
        return title.equals("Please sign in");
    }

    void login(String url, String username, String password) {
        driver.get(url);
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        WebElement idTextField = driver.findElement(By.id("username"));
        idTextField.sendKeys(username);
        WebElement passwordTextField = driver.findElement(By.id("password"));
        passwordTextField.sendKeys(password);
        passwordTextField.submit();
    }

    void enterGameRoom() {
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        WebElement startButton = driver.findElement(By.id("start"));
        startButton.click();
    }

    boolean isInTurn() {
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        WebElement element = driver.findElementById("rightSide");
        return element.getAttribute("class").contains("playerTurn");
    }

    void makeShot() {
        if (isInTurn()) {
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
            List<WebElement> elements = driver.findElements(By.xpath("//td[contains(@id, 'ocell') and contains(@class, 'water')]"));
            WebElement element = elements.get(new Random().nextInt(elements.size()));
            element.click();
        }
    }
}
