package stepdefinitions;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pojo.SliderImage;

public class LoginPage {

	WebDriver driver;
	List<SliderImage> sliderImages;
	public SliderImage sliderImage;
	ExtentReports extent;
	ExtentTest log;
	WebElement nextSlideButton;

	@Before
	public void setup() {
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\lokesh_s\\eclipse-workspace\\Amazon\\Drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		sliderImages = new ArrayList<>();

		extent = new ExtentReports();
		ExtentSparkReporter spark = new ExtentSparkReporter("target/Spark.html");
		extent.attachReporter(spark);
	}

	@Given("User open the Amazon website")
	public void launchAmazonWebsite() {
		log = extent.createTest("Open Amazon Website");
		try {
			driver.get("https://www.amazon.in/");
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
			log.pass("Amazon website opened successfully");
		} catch (Exception e) {
			log.fail("Opening Amazon website: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@When("User navigate through the slider images")
	public void navigateSliderButton() {
		log = extent.createTest("Navigate Slider Images");
		try {
			sliderImage = new SliderImage();
			nextSlideButton = driver.findElement(By.xpath(sliderImage.getNextSlideButtonLocator()));
			int index = 0;
			boolean hasNext = true;

			while (hasNext && index < 7) {
				captureEachImages(index++);

				if (nextSlideButton.isDisplayed() && nextSlideButton.isEnabled()) {
					nextSlideButton.click();
					log.pass("Navigated to next slide");
				} else {
					hasNext = false;
					log.pass("Reached the end of images");
				}
			}
		} catch (Exception e) {
			log.fail("navigating through slider images: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Then("User should capture and save each image {int}")
	public void captureEachImages(int index) {
		log = extent.createTest("Capture and Save Images");
		try {
			sliderImage = new SliderImage();
			List<WebElement> imageElements = driver.findElements(By.xpath(sliderImage.getImageContainerLocator()));

			int imageIndex = 1;
			for (WebElement imgElement : imageElements) {
				String imgSrc = imgElement.getAttribute("src");
				String imagePath = captureAndSaveImage(imgSrc, index, imageIndex++);
				log.pass("Image " + imageIndex + " captured and saved: " + imagePath,
						MediaEntityBuilder.createScreenCaptureFromPath(imagePath).build());
			}
		} catch (Exception e) {
			log.fail("capturing and saving images: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private String captureAndSaveImage(String imgUrl, int slideIndex, int imageIndex) {
		try {
			URL imageUrl = new URL(imgUrl);
			BufferedImage image = ImageIO.read(imageUrl);
			String imagePath = "C:\\Users\\lokesh_s\\eclipse-workspace\\Amazon\\Slides\\image_" + imageIndex + ".jpg";
			File outputFile = new File(imagePath);
			ImageIO.write(image, "jpg", outputFile);
			return imagePath;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@After
	public void teardown() {
		if (driver != null) {
			driver.quit();
		}
		extent.flush();
	}

}
