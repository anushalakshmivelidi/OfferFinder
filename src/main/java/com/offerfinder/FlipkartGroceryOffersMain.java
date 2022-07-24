package com.offerfinder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class FlipkartGroceryOffersMain {

	public static void main(String[] args) throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", System.getenv("CHROME_DRIVER"));
		WebDriver driver = new ChromeDriver();
		driver.get("https://www.flipkart.com/");
		driver.findElement(By.xpath("//button[@class='_2KpZ6l _2doB4z']")).click();
		driver.findElement(By.linkText("Grocery")).click();
		Thread.sleep(1000);
		try {
			driver.findElement(By.className("_8Phu0v")).sendKeys("500090/n");
		} catch (ElementNotInteractableException e) {
			Actions a = new Actions(driver);
			WebElement move = driver.findElement(By.className("_8Phu0v"));
			a.moveToElement(move).build().perform();
			WebElement p = driver.findElement(By.xpath("//input[@class='_166SQN']"));
			p.clear();
			p.sendKeys(System.getenv("PINCODE") + "\n");
		}
		Thread.sleep(5000);
		Actions a = new Actions(driver);
		WebElement webel = driver
				.findElement(By.xpath("//*[@id=\"container\"]/div/div[3]/div[5]/div/div[1]/a/div/img[2]"));
		a.moveToElement(webel).moveByOffset(100, 0).build().perform();
		a.click(webel).build().perform();
		Thread.sleep(5000);

		// find if next is there or not

		Set<Product> products = new HashSet<Product>();

		WebElement nextButton = null;

		do {
//nextbutton finding 
			if (nextButton != null) {
				nextButton.click();
				Thread.sleep(5000);
			}
			// main parent element
			List<WebElement> itemRows = driver.findElements(By.xpath("//div[@class='_35mN4f']"));

			System.out.println(itemRows.size());

			for (int i = 0; i < itemRows.size(); i++) {
				// products extract
				WebElement itemDetails = itemRows.get(i).findElement(By.xpath(".//div[@class='_2gX9pM']"));
				WebElement anchorTag = itemDetails.findElement(By.tagName("a"));
				String name = anchorTag.getAttribute("title");
				Float price = null;
				String quantity = "";
				try {
					WebElement priceEl = itemDetails.findElement(By.xpath(".//div[@class='_30jeq3 _3aGlZL']"));
					price = Float.valueOf(priceEl.getText().replace("₹", "").replace(",", ""));
				} catch (NoSuchElementException e) {
					System.out.println(name + ":" + "price is not avaliable" + ":" + quantity);
				}
				// String quantity="";
				try {
					WebElement weight = itemRows.get(i).findElement(By.xpath(".//div[@class='_1MbXnE _1kHdUD']"));
					quantity = weight.getText();
				} catch (NoSuchElementException e) {
					System.out.println(name + ":" + price + ":" + "Got no quantity");
				}

				System.out.println(name + ":" + price + ":" + quantity);

				products.add(new Product(name, price, quantity));
			}
			try {
				nextButton = driver.findElement(By.xpath("(//a[@class='_1LKTO3'])[last()]"));
				if (!nextButton.getText().equalsIgnoreCase("NEXT")) {
					nextButton = null;
				}
			} catch (NoSuchElementException e) {
				nextButton = null;
				System.out.println("next button not found");
			}
		} while (nextButton != null); // For extracting products in all the pages

		System.out.println("Number of products: " + products.size());
		Thread.sleep(10000);
		driver.quit();
		// push the product details to promethus
		Telemetry.pushProducts(products, "flipkart","Groceries");
	}

}
