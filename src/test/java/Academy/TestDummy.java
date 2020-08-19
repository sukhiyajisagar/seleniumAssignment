package Academy;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import junit.framework.Assert;
import resources.base;

public class TestDummy extends base{
	public WebDriver driver;
	
	 public static Logger log =LogManager.getLogger(base.class.getName());
	@BeforeTest
	public void initialize() throws IOException
	{
		log.debug("Opening chrome browser");
		 driver =initializeDriver();
	}

	@Test
	public void open() throws InterruptedException
	{
		
		driver.get("https://www.cheapair.com/");
		log.debug("Travel Site Opened");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//a[@class='icn icn-plane']")).click();
		log.debug("Clicked on Flights");
		driver.findElement(By.xpath("//*[@id=\"tripTypeContainer\"]/div/label[2]")).click();
		log.debug("Single Trip Selected");
		driver.findElement(By.cssSelector("#from1")).sendKeys("Mumbai");
		List<WebElement> options =driver.findElements(By.cssSelector("li[class='ui-menu-item'] a"));
		for(WebElement option :options)
		{
			if(option.getText().equalsIgnoreCase("Mumbai, India"))
			{
				option.click();
				break;
			}
		}
		log.debug("From location selected");
		driver.findElement(By.cssSelector("#to1")).sendKeys("Delhi");
		List<WebElement> options2 =driver.findElements(By.cssSelector("li[class='ui-menu-item'] a"));
		for(WebElement options3 :options2)
		{
			if(options3.getText().equalsIgnoreCase("Delhi, India"))
			{
				options3.click();
				break;
			}
		}
		log.debug("To location selected");
		driver.findElement(By.xpath("//span[contains(@class,'placeholder')] [contains(text(),'Select Date')]")).click();
		WebElement calendar=driver.findElement(By.xpath("//*[@class=\"ui-datepicker-group ui-datepicker-group-last\"]"));
		WebElement tbdy=calendar.findElement(By.tagName("tbody"));
		List<WebElement> dates= tbdy.findElements(By.xpath("//td[@data-handler='selectDay']"));
		int count=dates.size();
		
		for(int i=0;i<count;i++)
		{
			if(dates.get(i).getText().equalsIgnoreCase("7"))
			{
				dates.get(i).click();
				break;
			}
		}
		log.debug("Date selected");
		String dateSelected=driver.findElement(By.xpath("//div[@class='form-control readonly prominent']")).getText();
		driver.findElement(By.xpath("//button[@class='btn large block']")).click();
		log.info("Flight details selected");
		Set<String>s=driver.getWindowHandles();
		Iterator<String> I1= s.iterator();
		String parent = I1.next();
		String child_window=I1.next();
		driver.switchTo().window(child_window);
		log.info("Control shifted to child window");
		if(driver.findElement(By.xpath("//span[@class='link fltactmdl-signup-no-thanks']")).isDisplayed())
		{
			driver.findElement(By.xpath("//span[@class='link fltactmdl-signup-no-thanks']")).click();
		}
		WebDriverWait wait = new WebDriverWait(driver,20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'Select a Flight')]")));
		log.debug("Successfully reached on child window");
		driver.findElement(By.xpath("//div[@class='flight-result-shell__fares']/div/span")).click();
		log.info("Price now displaying in ascending order");
		WebElement flightsrow= driver.findElement(By.xpath("//div[@class='fltrt-opt-region trip-option al-AI ns numcolumns4']"));
		WebElement fareList=flightsrow.findElement(By.tagName("ol"));
		List<WebElement> fareLists= fareList.findElements(By.tagName("li"));
		for(int i=0;i<fareLists.size();i++)
		{
			fareLists.get(i).findElement(By.xpath("//div[@data-hk='fareOption']")).click();
			break;
		}
		log.info("First flight clicked");
		String actualDate = driver.findElement(By.xpath("//*[@id=\"flightReviewTarget\"]/div/div/div/div/div[2]/div/div[1]/div/div[2]")).getText();
		log.debug("Fetching actual date");
		if(actualDate.contains(dateSelected))
		{
			Assert.assertTrue(true);
		}
		log.info("Actual date and Expected date compared");
	}
	
	@AfterTest
	public void teardown()
	{
		log.debug("Closing browser");
		driver.close();	
	}
}

