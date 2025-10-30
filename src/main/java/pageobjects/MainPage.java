//Выпадающий список в разделе «Вопросы о важном». Нужно проверить: когда нажимаешь на стрелочку,
// открывается соответствующий текст.
package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.HashMap;

public class MainPage {

    private final WebDriver driver;

    // Локаторы вопросов с содержательными именами:
    private final static By questionCost = By.id("accordion__heading-0");
    private final static By questionMultipleScooters = By.id("accordion__heading-1");
    private final static By questionRentalTime = By.id("accordion__heading-2");
    private final static By questionTodayOrder = By.id("accordion__heading-3");
    private final static By questionExtendReturn = By.id("accordion__heading-4");
    private final static By questionCharging = By.id("accordion__heading-5");
    private final static By questionCancelOrder = By.id("accordion__heading-6");
    private final static By questionOutsideMkad = By.id("accordion__heading-7");

    // Локаторы ответов с содержательными именами:
    private final static By answerCost = By.id("accordion__panel-0");
    private final static By answerMultipleScooters = By.id("accordion__panel-1");
    private final static By answerRentalTime = By.id("accordion__panel-2");
    private final static By answerTodayOrder = By.id("accordion__panel-3");
    private final static By answerExtendReturn = By.id("accordion__panel-4");
    private final static By answerCharging = By.id("accordion__panel-5");
    private final static By answerCancelOrder = By.id("accordion__panel-6");
    private final static By answerOutsideMkad = By.id("accordion__panel-7");

    // Ключ: значение (вопрос: ответ):
    static HashMap<By, By> map = new HashMap<>();

    static {
        map.put(questionCost, answerCost);
        map.put(questionMultipleScooters, answerMultipleScooters);
        map.put(questionRentalTime, answerRentalTime);
        map.put(questionTodayOrder, answerTodayOrder);
        map.put(questionExtendReturn, answerExtendReturn);
        map.put(questionCharging, answerCharging);
        map.put(questionCancelOrder, answerCancelOrder);
        map.put(questionOutsideMkad, answerOutsideMkad);
    }

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    // Скроллим главную страницу до первого вопроса:
    public void scrollMainPage() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
                driver.findElement(questionCost));
    }

    // Открыть элемент списка вопросов, ждем появления текста с ответом:
    public String getAnswer(By questionLocator) {
        driver.findElement(questionLocator).click();
        new WebDriverWait(driver, Duration.ofSeconds(15)).until(driver ->
                driver.findElement(map.get(questionLocator)).getText() != null
                        && !driver.findElement(map.get(questionLocator)).getText().isEmpty());
        return driver.findElement(map.get(questionLocator)).getText();
    }

    // исправленный метод для получения ответа на вопрос по тексту:
    public String getAnswerForQuestion(String question) {
        // Логика получения ответа на вопрос
        for (By questionLocator : map.keySet()) {
            String actualQuestionText = driver.findElement(questionLocator).getText();
            if (actualQuestionText.equals(question)) {
                return getAnswer(questionLocator);
            }
        }
        return "Ответ не найден";
    }

    public void scrollToQuestion(String question) {
        for (By questionLocator : map.keySet()) {
            WebElement questionElement = driver.findElement(questionLocator);
            if (questionElement.getText().equals(question)) {
                // Скроллим к конкретному вопросу
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", questionElement);

                // Ждем, пока элемент станет кликабельным
                new WebDriverWait(driver, Duration.ofSeconds(5))
                        .until(ExpectedConditions.elementToBeClickable(questionElement));
                break;
            }
        }
    }
}



