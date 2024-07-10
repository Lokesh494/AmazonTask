package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.DateUtil;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import pojo.SliderImage;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class HooksClass {

	public static WebDriver driver;
	public static WebDriverWait wait;

	List<SliderImage> sliderImages;

	public HooksClass(WebDriver driver) {
		HooksClass.driver = driver;
	}

	public static void launchBrowser(String browser) {
		initializeDriver(browser);

		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	@Before
	public void setup() {
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\lokesh_s\\eclipse-workspace\\Amazon\\Drivers\\chromedriver.exe");
		driver = new ChromeDriver();

	}

	@After
	public void teardown() {
		if (driver != null) {
			driver.quit();
		}
	}

	public static void initializeDriver(String browser) {
		switch (browser.toLowerCase()) {
		case "chrome":
			System.setProperty("webdriver.chrome.driver",
					"C:\\Users\\lokesh_s\\eclipse-workspace\\Amazon\\Drivers\\chromedriver.exe");
			driver = new ChromeDriver();
			break;
		case "firefox":
			System.setProperty("webdriver.gecko.driver", "path_to_geckodriver");
			driver = new FirefoxDriver();
			break;
		case "ie":
			System.setProperty("webdriver.ie.driver", "path_to_iedriver");
			driver = new InternetExplorerDriver();
			break;
		case "edge":
			System.setProperty("webdriver.edge.driver", "path_to_edgedriver");
			driver = new EdgeDriver();
			break;
		case "safari":
			driver = new SafariDriver();
			break;
		default:
			System.out.println("Invalid browser specified. Defaulting to Chrome.");
			System.setProperty("webdriver.chrome.driver",
					"C:\\Users\\lokesh_s\\eclipse-workspace\\EPAM-Flex_Framework\\Drivers\\chromedriver.exe");
			driver = new ChromeDriver();
			break;
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	}

	public static void maximizeWindow() {
		driver.manage().window().maximize();
	}

	public static void loadUrl(String url) {
		driver.get(url);
	}

	public static void type(WebElement e, String value) {
		e.sendKeys(value);
	}

	public static void btnClick(WebElement e) {
		e.click();
	}

	public static void getTittle() {
		String title = driver.getTitle();
		System.out.println(title);
	}

	public static void getUrl() {
		String currentUrl = driver.getCurrentUrl();
		System.out.println(currentUrl);
	}

	public static void deleteCookies() {
		driver.manage().deleteAllCookies();
	}

	public static void quitBrowser() {
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	public WebDriver getDriver() {
		return driver;
	}

	public static void selectByVisibleText(WebElement element, String text) {
		Select s = new Select(element);
		s.selectByVisibleText(text);
	}

	public WebElement findElementId(String value) {
		WebElement txt = driver.findElement(By.id(value));
		return txt;
	}

	// sendKeys
	public void insertValueinTxtBox(WebElement ele, String value) {
		ele.sendKeys(value);
	}

	// click
	public void clkBtn(WebElement ele1) {
		ele1.click();
	}

	// ExcelWrite - To create new Sheet
	public void newExcelWrite(String SheetName, int rowNo, int cellNo, String data) throws IOException {
		File f = new File("C:\\Users\\lokesh_s\\eclipse-workspace\\EPAM-Flex_Framework\\External Files\\TEST.xlsx");
		FileInputStream str = new FileInputStream(f);
		XSSFWorkbook workbook = new XSSFWorkbook(str);
		XSSFSheet createSheet = workbook.createSheet(SheetName);
		XSSFRow createRow = createSheet.createRow(rowNo);
		XSSFCell cell = createRow.createCell(cellNo);
		cell.setCellValue(data);
		FileOutputStream str2 = new FileOutputStream(f);
		workbook.write(str2);
	}

	// ExcelWrite - To rewrite in existing sheet
	public void existingExcelWrite(String SheetName, int rowNo, int cellNo, String string) throws IOException {
		File f = new File("C:\\Users\\Sathish N\\eclipseworkspace\\BaseClass\\TestData\\AdactinTask.xlsx");
		FileInputStream str = new FileInputStream(f);
		XSSFWorkbook workbook = new XSSFWorkbook(str);
		XSSFSheet createSheet = workbook.getSheet(SheetName);
		XSSFRow createRow = createSheet.createRow(rowNo);
		XSSFCell createCell = createRow.createCell(cellNo);
		createCell.setCellValue(string);
		FileOutputStream str2 = new FileOutputStream(f);
		workbook.write(str2);
	}

	// ExcelRead
	public String excelRead(String SheetName, int rowNo, int cellNo) throws IOException {
		File f = new File("C:\\Users\\Sathish N\\eclipseworkspace\\BaseClass\\TestData\\AdactinTask.xlsx");
		FileInputStream str = new FileInputStream(f);
		XSSFWorkbook workbook = new XSSFWorkbook(str);
		XSSFSheet sheet = workbook.getSheet("Adactin");
		XSSFRow r = sheet.getRow(rowNo);
		XSSFCell c = r.getCell(cellNo);
		String value = "";
		int type = c.getCellType();
		if (type == 1) {
			value = c.getStringCellValue();
		} else if (DateUtil.isCellDateFormatted(c)) {
			Date d = c.getDateCellValue();
			SimpleDateFormat sim = new SimpleDateFormat("dd-MM-YY");
			value = sim.format(d);
		} else {
			double d = c.getNumericCellValue();
			long l = (long) d;
			value = String.valueOf(l);
		}
		return value;
	}

	// xpath
	public WebElement xpath(String value) {
		WebElement text = driver.findElement(By.xpath(value));
		return text;
	}

	// dropDown
	public void dropDown(WebElement ele, String value) {
		Select select = new Select(ele);
		select.selectByValue(value);
	}

	// getAttribute
	public String getAttr(WebElement ele1) {
		String val1 = ele1.getAttribute("value");
		System.out.println(val1);
		return val1;
	}

	// getText
	public String getTex(WebElement ele1) {
		String text = ele1.getText();
		System.out.println(text);
		return text;
	}

	// MouseOverActions
	public void mouseOver(WebElement ele1) {
		Actions action = new Actions(driver);
		action.moveToElement(ele1).perform();
	}

	// dragAndDrop
	public void dragAndDropMethod(WebElement ele1, WebElement ele2) {
		Actions action = new Actions(driver);
		action.dragAndDrop(ele1, ele2).perform();
	}

	// rightClick
	public void rightClick(WebElement ele1) {
		Actions action = new Actions(driver);
		action.contextClick(ele1).perform();
	}

	// doubleClick
	public void doubleclk(WebElement ele1) {
		Actions action = new Actions(driver);
		action.doubleClick(ele1).perform();
	}

	// Alert
	public void alertKey() {
		Alert alert = driver.switchTo().alert();
		alert.accept();
	}

	// AlertDismiss
	public void alertDismis() {
		Alert alert = driver.switchTo().alert();
		alert.dismiss();
	}

	// AlertPrompt
	public void alertPrompt(String string) {
		Alert alert = driver.switchTo().alert();
		alert.sendKeys(string);
	}

	// ScreenShot
	public void screenShot(String path, File Sfile) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File screenshotAs = ts.getScreenshotAs(OutputType.FILE);
		File Dfile = new File(path);
		FileUtils.copyFile(Sfile, Dfile);
	}

	// JavaScriptSetAttribute
	public void javaScript(String value1, WebElement ele1) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('value','" + value1 + "')", ele1);
	}

	// JavaScriptGetAttribute
	public String javaScriptGetAttri(WebElement ele1) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		Object object = js.executeScript("return arguments[0].getAttribute('value')", ele1);
		String string = object.toString();
		System.out.println(string);
		return string;
	}

	// scrollUp
	public void scrollup(WebElement ele1) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true)", ele1);
	}

	// scrollDown
	public void scrollDown(WebElement ele1) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(false)", ele1);
	}

	// Frames
	public int frameSizeCount(String value) {
		List<WebElement> frameCount = driver.findElements(By.tagName(value));
		int size = frameCount.size();
		return size;
	}

	// Switch to frame by using Index
	public void SwitchFrameIndex(int name) {
		driver.switchTo().frame(name);
	}

	// Switch to frame by using String
	public void SwitchFrameString(String name) {
		driver.switchTo().frame(name);
	}

	// Switch to frame by using WebElement
	public void SwitchFrameElement(WebElement ele1) {
		driver.switchTo().frame(ele1);
	}

	// Switch from frame to window
	public void frameToWin() {
		driver.switchTo().defaultContent();
	}

	// DropDown - SelectByIndex
	public void selectByIn(WebElement ele1, int value) {
		Select select = new Select(ele1);
		select.selectByIndex(value);
	}

	// DropDown - SelectByValue
	public void selectByVal(WebElement ele1, String value) {
		Select select = new Select(ele1);
		select.selectByValue(value);
	}

	// DropDown - SelectByVisibleText
	public void selectByVis(WebElement ele1, String text) {
		Select select = new Select(ele1);
		select.selectByVisibleText(text);
	}

	// DropDown - IsMultiple
	public boolean isMulti(WebElement ele1) {
		Select select = new Select(ele1);
		boolean multiple = select.isMultiple();
		return multiple;
	}

	// DropDown - getOptions
	public List<String> getOpt(WebElement ele1) {
		Select select = new Select(ele1);
		List<WebElement> options = select.getOptions();
		List<String> op = new ArrayList<String>();
		for (WebElement x : options) {
			op.add(x.getText());
		}
		return op;
	}

	// DropDown - getAllSelected
	public List<String> getAll(WebElement ele1) {
		Select select = new Select(ele1);
		List<WebElement> options = select.getAllSelectedOptions();
		List<String> op = new ArrayList<String>();
		for (WebElement x : options) {
			op.add(x.getText());
		}
		return op;
	}

	// DropDown - getFirstSelected
	public String getFirst(WebElement ele1) {
		Select select = new Select(ele1);
		WebElement firstSelectedOption = select.getFirstSelectedOption();
		String attribute = firstSelectedOption.getAttribute("value");
		return attribute;
	}

	// DropDown - deSelectByIndex
	public void deSelectByInd(WebElement ele1, int value) {
		Select select = new Select(ele1);
		select.deselectByIndex(value);
	}

	// DropDown - deSelectByValue
	public void deSelectByVal(WebElement ele1, String value) {
		Select select = new Select(ele1);
		select.deselectByValue(value);
	}

	// DropDown - deSelectByVisibleText
	public void deSelectByVisibleTex(WebElement ele1, String value) {
		Select select = new Select(ele1);
		select.deselectByVisibleText(value);
	}

	// DropDown - deSelectAll
	public void deSelectByAl(WebElement ele1) {
		Select select = new Select(ele1);
		select.deselectAll();
	}

	// Windows Handling
	public void windowHand() {
		String parentWin = driver.getWindowHandle();
		Set<String> windowHandles = driver.getWindowHandles();
		for (String string : windowHandles) {
			if (!(parentWin.equals(string))) {
				driver.switchTo().window(string);
			}
		}
	}

	// Implicity
	public void implicity(int sec) {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	}

}
