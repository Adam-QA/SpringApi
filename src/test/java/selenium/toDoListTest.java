package selenium;









import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class toDoListTest {
	static WebDriver driver;
	//setup
	@BeforeAll
	public static void setup() {
		System.setProperty("webdriver.gecko.driver", "C:\\Users\\Adam Stevenson\\Documents\\workspace-spring-tool-suite-4-4.8.0.RELEASE\\SpringApi\\src\\main\\resources\\drivers\\geckodriver.exe");
		 driver = new FirefoxDriver();
		
		
	}
	//test
	@Test
	public void testlist() throws InterruptedException {
		driver.get("http://127.0.0.1:5500/ToDoList/html/index.html");
		Thread.sleep(500);
		
		WebElement addlist = driver.findElement(By.xpath("//*[@id=\"abc\"]"));
		addlist.sendKeys("test");
		
		WebElement submit = driver.findElement(By.xpath("/html/body/div/form/button"));
		submit.submit();
		Thread.sleep(5000);
		WebElement success = driver.findElement(By.xpath("/html/body/table/thead/tr[2]/td[2]"));
		assertEquals("test", success.getText());
		
	}
	
	@Test
	public void view() throws InterruptedException {
		driver.get("http://127.0.0.1:5500/ToDoList/html/index.html");
		Thread.sleep(500);
		
		WebElement viewlist = driver.findElement(By.xpath("/html/body/table/thead/tr[2]/td[3]/a"));
		viewlist.click();
		Thread.sleep(2500);
		
		WebElement name = driver.findElement(By.xpath("/html/body/div[1]/h1"));
		assertEquals("Tasks", name.getText());
	}
	
	@Test
	public void testupdate() throws InterruptedException {
		driver.get("http://127.0.0.1:5500/ToDoList/html/index.html");
		Thread.sleep(5000);
		
		WebElement viewlist = driver.findElement(By.xpath("/html/body/table/thead/tr[2]/td[3]/a"));
		viewlist.click();
		Thread.sleep(2500);
		
		WebElement update = driver.findElement(By.xpath("//*[@id=\"inputName\"]"));
		update.sendKeys("Tuesday");
		Thread.sleep(500);
		
		WebElement submit = driver.findElement(By.xpath("//*[@id=\"button1\"]"));
		submit.click();
		Thread.sleep(500);
		
		driver.get("http://127.0.0.1:5500/ToDoList/html/index.html");
		Thread.sleep(500);
		
		WebElement name = driver.findElement(By.xpath("/html/body/div[1]/h1"));
		assertEquals("Tuesday", name.getText());
		
		
		
	}
	
	@Test
	public void testdelete() throws InterruptedException {
		driver.get("http://127.0.0.1:5500/ToDoList/html/index.html");
		Thread.sleep(5000);
		
		WebElement delete = driver.findElement(By.xpath("/html/body/table/thead/tr[2]/td[4]/button"));
		delete.submit();
//		WebElement deleted = driver.findElement(By.xpath("/html/body/table/thead/tr[2]/td[4]/button"));
//		assertEquals("", deleted.getText());
	}
	
	

	
	//close test
	@AfterAll
	public static void close() {
		driver.quit();
		
		
	}

}
