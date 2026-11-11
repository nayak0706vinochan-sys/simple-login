package com.example.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class LoginTest {

    public static void main(String[] args) throws Exception {
        ChromeOptions options = new ChromeOptions();
        // Remove the "--headless=new" argument if you want to watch the browser locally
        options.addArguments("--headless=new", "--no-sandbox",
                "--disable-dev-shm-usage", "--window-size=1280,800");

        WebDriver driver = new ChromeDriver(options);
        int failed = 0;

        try {
            String pageUrl = LoginTest.class.getClassLoader()
                    .getResource("web/login.html").toURI().toString();

            // name, password, expected message
            String[][] cases = {
                { "admin", "admin123",  "Login successful" },
                { "admin", "wrongpass", "Invalid name or password" },
                { "user",  "admin123",  "Invalid name or password" },
                { "",      "",          "Name and password are required" }
            };

            for (String[] c : cases) {
                driver.get(pageUrl);
                driver.findElement(By.id("username")).sendKeys(c[0]);
                driver.findElement(By.id("password")).sendKeys(c[1]);
                driver.findElement(By.id("loginBtn")).click();

                String actual = driver.findElement(By.id("message")).getText();

                if (actual.equals(c[2])) {
                    System.out.println("PASS: name='" + c[0] + "' -> " + actual);
                } else {
                    System.out.println("FAIL: name='" + c[0] + "' expected '" + c[2] + "' but got '" + actual + "'");
                    failed++;
                }
            }
        } finally {
            driver.quit();
        }

        if (failed > 0) {
            throw new RuntimeException(failed + " login check(s) failed");
        }
        System.out.println("All login checks passed");
    }
}