/*Задание: Заказ самоката. Нужно проверить весь флоу позитивного сценария с двумя наборами данных.
Проверить точки входа в сценарий, их две: кнопка «Заказать» вверху страницы и внизу.
Из чего состоит позитивный сценарий:
- Нажать кнопку «Заказать». На странице две кнопки заказа.
- Заполнить форму заказа.
- Проверить, что появилось всплывающее окно с сообщением об успешном создании заказа.*/

package pageobjects;
import org.openqa.selenium.*;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class PageOrder {
    private final WebDriver driver;

    // Локаторы для баннера куки
    private static final By CookieBannerButton = By.className("App_CookieButton__3cvqF");

    // Локаторы для первой страницы
    private static final By OrderButtonHeader = By.xpath("//*[@id='root']/div/div[1]/div[1]/div[2]/button[1]");
    private static final By OrderButtonDown = By.xpath("//*[@id='root']/div/div/div[4]/div[2]/div[5]/button");
    private static final By NameButton = By.xpath("//*[@id='root']/div/div[2]/div[2]/div[1]/input");
    private static final By SurnameButton = By.xpath("//*[@id='root']/div/div[2]/div[2]/div[2]/input");
    private static final By AddressButton = By.xpath("//*[@id='root']/div/div[2]/div[2]/div[3]/input");
    private static final By MetroButton = By.xpath("//*[@id='root']/div/div[2]/div[2]/div[4]/div/div/input");
    private static final By TelButton = By.xpath("//*[@id='root']/div/div[2]/div[2]/div[5]/input");
    private static final By NextButton = By.xpath("//*[@id='root']/div/div[2]/div[3]/button");

    // Локаторы для второй страницы
    private static final By DateButton = By.xpath("//*[@id='root']/div/div[2]/div[2]/div[1]/div/div/input");
    private static final By PeriodButton = By.xpath("//*[@id='root']/div/div[2]/div[2]/div[2]/div/div");
    private static final By BlackButton = By.xpath("//*[@id='root']/div/div[2]/div[2]/div[3]/label[1]/input");
    private static final By GreyButton = By.xpath("//*[@id='grey']");
    private static final By CommentButton = By.xpath("//*[@id='root']/div/div[2]/div[2]/div[4]/input");
    private static final By SubmitButton = By.xpath("//*[@id='root']/div/div[2]/div[3]/button[2]");
    private static final By PopapButton = By.xpath("//*[@id='root']/div/div[2]/div[5]/div[1]");
    private static final By YesButton = By.xpath("//*[@id='root']/div/div[2]/div[5]/div[2]/button[2]");

    public PageOrder(WebDriver driver) {
        this.driver = driver;
    }

    // Метод для закрытия баннера куки
    public void closeCookieBanner() {
        driver.findElement(CookieBannerButton).click();
    }

    //Скролл до элемента кнопки "Заказать"
    public void scrollPageOrder() {
        WebElement orderButton = driver.findElement(OrderButtonDown);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", orderButton);

        // Добавляем небольшую паузу после скролла
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Нажатие на нижнюю кнопку "Заказать"
    public void clickOrderButtonDown() {
        WebElement orderButton = driver.findElement(OrderButtonDown);

        // Скроллим к элементу и кликаем через JavaScript
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", orderButton);

        // Ждем пока элемент станет кликабельным
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(orderButton));

        // Кликаем через JavaScript (обходит проблему с перекрытием)
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", orderButton);
    }

    // Нажатие на верхнюю кнопку "Заказать"
    public void clickOrderButtonHeader() {
        driver.findElement(OrderButtonHeader).click();
    }

    // Заполнение первой страницы заказа
    public void enterDataFirstPageOrder(String name, String surname, String address, int metro, String phoneNumber) {
        driver.findElement(NameButton).sendKeys(name);  // Заполнение поля "Имя"
        driver.findElement(SurnameButton).sendKeys(surname);  // Заполнение поля "Фамилия"
        driver.findElement(AddressButton).sendKeys(address);  // Заполнение поля "Адрес"

        // ВЫБОР СТАНЦИИ МЕТРО
        WebElement metroInput = driver.findElement(By.xpath(".//input[@placeholder='* Станция метро']"));
        metroInput.click();

        // Маппинг индексов на названия станций
        String[] stationNames = {"", "Бульвар Рокоссовского", "Черкизовская", "Преображенская площадь",
                "Сокольники", "Красносельская", "Комсомольская", "Красные Ворота",
                "Чистые пруды", "Лубянка", "Охотный Ряд", "Библиотека им. Ленина",
                "Кропоткинская", "Парк культуры", "Фрунзенская", "Спортивная",
                "Ленинский проспект"};

        String stationName = stationNames[metro];
        metroInput.sendKeys(stationName);

        // Явное ожидание вместо Thread.sleep
        WebElement dropdown = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//div[contains(text(), '" + stationName + "')]")));
        dropdown.click();

        driver.findElement(TelButton).sendKeys(phoneNumber);  // Заполнение поля "Телефон"
        driver.findElement(NextButton).click();  // Нажатие на кнопку "Далее"
    }

    // Заполнение второй страницы заказа
    public void enterDataSecondPageOrder(String color, String date, int rentalDays, String comment) {
        // Выбор цвета самоката
        if ("черный".equals(color)) {
            driver.findElement(BlackButton).click();
        } else if ("серый".equals(color)) {
            driver.findElement(GreyButton).click();
        }

        // Выбор даты
        driver.findElement(DateButton).sendKeys(date);
        driver.findElement(DateButton).sendKeys(Keys.RETURN);

        // Выбор срока аренды
        driver.findElement(PeriodButton).click();
        By allDayForOrder = By.className("Dropdown-option");
        List<WebElement> elements = driver.findElements(allDayForOrder);
        elements.get(rentalDays - 1).click();  // Выбор срока аренды

        driver.findElement(CommentButton).sendKeys(comment);  // Заполнение поля "Комментарий"
        driver.findElement(SubmitButton).click();  // Нажатие на кнопку "Заказать"
        driver.findElement(YesButton).click();  // Подтверждение заказа в попапе
    }

    // Попап подтверждения оформления заказа
    public boolean successfullyText() {
        String successfullOrder = driver.findElement(PopapButton).getText();
        return successfullOrder.contains("Заказ оформлен");
    }
}