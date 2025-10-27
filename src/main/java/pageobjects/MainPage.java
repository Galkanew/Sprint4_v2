//Выпадающий список в разделе «Вопросы о важном». Нужно проверить: когда нажимаешь на стрелочку,
// открывается соответствующий текст.
package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.HashMap;

public class MainPage {

    private final WebDriver driver;

    // Локаторы вопросов с 1 - 8 из раздела "Вопросы о важном":
    private final static By Question_1 = By.id("accordion__heading-0");
    private final static By Question_2 = By.id("accordion__heading-1");
    private final static By Question_3 = By.id("accordion__heading-2");
    private final static By Question_4 = By.id("accordion__heading-3");
    private final static By Question_5 = By.id("accordion__heading-4");
    private final static By Question_6 = By.id("accordion__heading-5");
    private final static By Question_7 = By.id("accordion__heading-6");
    private final static By Question_8 = By.id("accordion__heading-7");


    // Локаторы ответов с 1 - 8 на вопросы из раздела "Вопросы о важном":
    private final static By Answer_1 = By.id("accordion__panel-0");
    private final static By Answer_2 = By.id("accordion__panel-1");
    private final static By Answer_3 = By.id("accordion__panel-2");
    private final static By Answer_4 = By.id("accordion__panel-3");
    private final static By Answer_5 = By.id("accordion__panel-4");
    private final static By Answer_6 = By.id("accordion__panel-5");
    private final static By Answer_7 = By.id("accordion__panel-6");
    private final static By Answer_8 = By.id("accordion__panel-7");
    // Ключ: значение (вопрос: ответ):
    static HashMap<By, By> map = new HashMap<>();
    static {
        map.put(Question_1, Answer_1);
        map.put(Question_2, Answer_2);
        map.put(Question_3, Answer_3);
        map.put(Question_4, Answer_4);
        map.put(Question_5, Answer_5);
        map.put(Question_6, Answer_6);
        map.put(Question_7, Answer_7);
        map.put(Question_8, Answer_8);
    }

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    // Скроллим главную страницу до первого вопроса:
    public void scrollMainPage() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
                driver.findElement(Question_1));
    }

    // Открыть элемент списка вопросов, ждем появления текста с ответом:
    public String getAnswer(By questionLocator) {
        driver.findElement(questionLocator).click();
        new WebDriverWait(driver, Duration.ofSeconds(15)).until(driver ->
                driver.findElement(map.get(questionLocator)).getText() != null
                        && !driver.findElement(map.get(questionLocator)).getText().isEmpty());
        return driver.findElement(map.get(questionLocator)).getText();
    }

    // Новый метод для получения ответа на вопрос по тексту:
    public String getAnswerForQuestion(String question) {
        // Логика получения ответа на вопрос
        // Предполагаем, что вопрос будет использоваться для получения локатора
        // В реальной реализации можно использовать поиск в мапе или другой подход
        for (By questionLocator : map.keySet()) {
            if (driver.findElement(questionLocator).getText().contains(question)) {
                return getAnswer(questionLocator);
            }
        }
        return "Ответ не найден";
    }
}

