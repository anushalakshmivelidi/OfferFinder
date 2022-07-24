package com.offerfinder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class AmazonGroceryOffersMain {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		System.setProperty("webdriver.chrome.driver", System.getenv("CHROME_DRIVER"));
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();

		driver.get("https://www.amazon.in/alm/storefront?almBrandId=ctnow");
		driver.findElement(By.id("glow-ingress-block")).click();
		Thread.sleep(1000);
		driver.findElement(By.id("GLUXZipUpdateInput")).sendKeys(System.getenv("PINCODE"));
		driver.findElement(By.id("GLUXZipUpdate")).click();
		JavascriptExecutor Js1 = (JavascriptExecutor) driver;
		Js1.executeScript("window.scrollBy(0,500)");
		Thread.sleep(5000);
		WebElement seem = driver
				.findElement(By.xpath("//*[@id=\"1d10c2ad-4e98-4b6b-a4e6-ec1d8f0ae35e\"]/div[1]/div/h2/a"));
		seem.click();
		Set<Product> products = new HashSet<Product>();
		WebElement next = null;
		do {
			if (next != null) {
				next.click();
				Thread.sleep(5000);
			}
			List<WebElement> items = driver.findElements(By.xpath("//div[@class='a-section a-spacing-base']"));
			System.out.println(items.size());
			for (int i = 0; i < items.size(); i++) {
				WebElement itemNameEl = items.get(i).findElement(By.xpath(
						".//a[@class='a-link-normal s-underline-text s-underline-link-text s-link-style a-text-normal']"));
				String name = itemNameEl.getText();
				Float price = null;
				String quantity = "";
				WebElement itemPriceEl = items.get(i).findElement(By.xpath(".//span[@class='a-price-whole']"));
				Float price1;
				price1 = Float.valueOf(itemPriceEl.getText().replace("₹", "").replace(",", ""));
				String quantity1 = " ";
				WebElement quantity2 = driver.findElement(By.xpath(".//span[@class='a-size-base a-color-secondary']"));
				quantity1 = quantity2.getText();
				System.out.println(name + ":" + price1 + ":" + quantity1);
				products.add(new Product(name, price1, quantity1));
			}
			try {
				next = driver.findElement(By.xpath(
						"//a[@class='s-pagination-item s-pagination-next s-pagination-button s-pagination-separator']"));
				if (!next.getText().equalsIgnoreCase("NEXT")) {
					next = null;
				}
			} catch (NoSuchElementException e) {
				next = null;
				System.out.println("next button not found");
			}
		} while (next != null); // For extracting products in all the pages

		System.out.println("Number of products: " + products.size());
		Thread.sleep(10000);
		driver.quit();
		Telemetry.pushProducts(products, "amazon","Groceries");

	}

}
