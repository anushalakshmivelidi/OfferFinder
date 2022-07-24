package com.offerfinder;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;


public class BigBasketOffersMain {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
System.setProperty("webdriver.chrome.driver", System.getenv("CHROME_DRIVER"));
WebDriver driver=new ChromeDriver();
driver.get("https://www.bigbasket.com/");
driver.findElement(By.xpath("//a[@class='btn hvr-fade']")).click();
driver.findElement(By.xpath("//span[@class='btn btn-default form-control ui-select-toggle']")).click();
Thread.sleep(2000);
//Select dropcity=new Select(driver.findElement(By.id("ui-select-choices-1")));

//dropcity.selectByIndex(5);
WebElement c=driver.findElement(By.id("//a[@class='ui-select-choices-row-inner'"));

System.out.println(c.getText());
	}

}
