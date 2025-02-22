package testng.apis;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import pages.LoginPage;

public class Hooks {
	
	protected WebDriver driver;
	
	public String bugPayload = """
 {
      "fields": {
        "project": {
            "key": "TS"
        },
        "issuetype": {
            "name": "Bug"
        },
        "summary": "Bug001 - For TS"
    }
  }""";
	
	@BeforeMethod
	public void beforeMethod() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://www.saucedemo.com/");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
	    new LoginPage(driver)
	        .login("standard_user", "secret_sauce");
	}
	
	@AfterMethod
	public void afterMethod(ITestResult result) throws IOException {
		if(!result.isSuccess()) {
			// Take Screenshot for Failed test case
			TakesScreenshot screenshot = (TakesScreenshot)driver;
			File src = screenshot.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(src, new File("./images/"+result.getName()+".png"));
			
			// Create New Bug Ticket
			String id = RestAssured.given()
			   .baseUri("https://karthikeselene.atlassian.net")
	           .basePath("/rest/api/3")
	           .auth()	
	           .preemptive()
	           .basic("karthike.selene@gmail.com", "")
	           .contentType(ContentType.JSON)
	           .log().all()
	           .body(bugPayload)
	           .post("/issue")
	           .then()
	           .statusCode(201)
	           .extract()
	           .jsonPath()
	           .getString("id");
			
			// Attach the error screenshot
			RestAssured.given()
			   .baseUri("https://karthikeselene.atlassian.net")
	           .basePath("/rest/api/3")
	           .auth()	
	           .preemptive()
	           .basic("karthike.selene@gmail.com", "")
	           .header("X-Atlassian-Token", "no-check")
	           .contentType(ContentType.MULTIPART)
	           .multiPart(new File("./images/"+result.getName()+".png"))
	           .post("/issue/"+id+"/attachments")
	           .then()
	           .statusCode(200);
		}
		driver.quit();
	}

}