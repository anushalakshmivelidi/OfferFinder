package com.offerfinder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class JioMartOffersMain {

	public static void main(String[] args) throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", System.getenv("CHROME_DRIVER"));
		WebDriver driver=new ChromeDriver();
		driver.get("https://www.jiomart.com/");
		driver.findElement(By.className("delivery_content")).click();
		driver.findElement(By.id("rel_pincode")).sendKeys(System.getenv("PINCODE"));
		driver.findElement(By.className("apply_btn")).click();
		Thread.sleep(2000);
		JavascriptExecutor Js1 = (JavascriptExecutor) driver;
		Js1.executeScript("window.scrollBy(0,1000)");
		driver.findElement(By.xpath("//section[@id='section_351']/a")).click();
		Set<Product> products=new HashSet<Product>();
		List<WebElement> itemsrow=driver.findElements(By.xpath("//ol[@id='top_deals_products']/li"));
		System.out.println(itemsrow.size());
		for(int i=0;i<itemsrow.size();i++)
		{
			WebElement items=itemsrow.get(i).findElement(By.xpath(".//span[@class='clsgetname']"));
			String name=items.getText();
			WebElement price1=itemsrow.get(i).findElement(By.xpath(".//div[@class='price-box']/span"));
			Float price=Float.valueOf(price1.getText().replace("₹", ""));
			System.out.println(name+"--"+price);
			String quantity="";
			products.add(new Product(name,price,quantity));
		}
		driver.quit();
		Telemetry.pushProducts(products,"jiomart", "Groceries");
	}

	}


