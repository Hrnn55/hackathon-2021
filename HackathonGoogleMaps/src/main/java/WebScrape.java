import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.sql.*;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class WebScrape
{
	public Connection conn;

	public static void main(String[] args) throws InterruptedException, IOException, SQLException
	{
		WebScrape app = new WebScrape();
	}

	public WebScrape() throws InterruptedException, IOException, SQLException
	{
//		googleMapsWebCrawl("3 Mariners Cv, Edgewater, NJ 07020");
//		googleMapsWebCrawl("20 W 34th St, New York, NY 10001");
//		googleMapsWebCrawl("Dwight-Englewood School");

		connectToDb();

		ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM requestdb.hrnnhq_requests");
		while(rs.next())
		{
			System.out.print(rs.getString("requestId") + " " + rs.getString("requestType") + " " + rs.getString("parameter"));
			System.out.println();
		}

		new Timer().scheduleAtFixedRate(new scheduledDbCheckTask(), 0, 500);
	}

	public void checkDb() throws SQLException, InterruptedException
	{
		ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM requestdb.hrnnhq_requests");

		int count = 0;
		while(rs.next())
		{
			String requestId = rs.getString("requestId");
			String requestType = rs.getString("requestType");
			String parameter = rs.getString("parameter");

			String[] iframes = googleMapsWebCrawl(parameter);

			String result = "";
			for(String iframe : iframes)
			{
				result += iframe.split("src=\"")[1].split("\" width=")[0] + "_";
			}
			System.out.println(result);

			PreparedStatement stmt = conn.prepareStatement("INSERT INTO requestdb.hrnnhq_responses VALUE (?, ?)");
			stmt.setString(1, requestId);
			stmt.setString(2, result);
			stmt.executeUpdate();

			conn.createStatement().executeUpdate("DELETE FROM requestdb.hrnnhq_requests WHERE requestId = " + requestId);

			count++;
		}
	}

	public class scheduledDbCheckTask extends TimerTask
	{
		@Override
		public void run()
		{
			try
			{
				checkDb();
			}
			catch(SQLException | InterruptedException throwables)
			{
				throwables.printStackTrace();
			}
		}
	}

	public String[] googleMapsWebCrawl(String address) throws InterruptedException
	{
		System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-blink-features=AutomationControlled");

		WebDriver driver = new ChromeDriver(options);

//        String address = "3 Mariners Cove, Edgewater, NJ 07020";
		String linkAddress = address.replaceAll(" ", "+");
		String searchName = "Hospitals near " + address;
		String linkSearchName = searchName.replaceAll(" ", "+");

		String[] iframes = new String[3];
		for(int i = 0; i < 3; i++)
		{
			int num = 3 + i * 2;

			//        driver.get("https://www.google.com/maps/dir/3+Mariners+Cove,+Edgewater,+NJ+07020,+USA/Columbia+University+Medical+Center,+400+Kelby+St+%23+18,+Fort+Lee,+NJ+07024/");
			driver.get("https://www.google.com/maps/search/" + linkSearchName + "/");
			driver.manage().window().maximize();

			// wait until website loads
			WebDriverWait wait = new WebDriverWait(driver, 20);
			wait.until(d -> ((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete"));
			randomIntervalBreak();

			//div[@aria-label='Results for Hospitals near 3 Mariners Cove, Edgewater, NJ 07020']/div[3]
			//div[@aria-label='" + searchContent + "']/div[3]/div/div[2]/div[2]/div[2]/div[2]/div/button
			//div[@aria-label='" + searchContent + "']/div[3]/div/div[2]

			String locationName = "";
			locationName = driver.findElement(By.xpath("//div[@aria-label='Results for " + searchName + "']/div[" + num + "]/div/div[2]")).getAttribute("aria-label");
			String linkLocationName = locationName.replaceAll(" ", "+");
			System.out.println(locationName);

			driver.get("https://www.google.com/maps/dir/" + linkAddress + "/" + linkLocationName + "/");

			// wait until website loads
			wait.until(d -> ((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete"));
			randomIntervalBreak();

			driver.findElement(By.xpath("//div[@data-trip-index='0']")).click();
			Thread.sleep(1337);
			driver.findElement(By.xpath("//button[@aria-label=' Share directions ']")).click();
			Thread.sleep(1337);
			driver.findElement(By.xpath("//button[@aria-label='Embed a map']")).click();

			iframes[i] = driver.findElement(By.xpath("//div[@class='modal-dialog']/div/div/div[3]/div/input")).getAttribute("value");
		}
		driver.close();

		return iframes;
	}

	public static void randomIntervalBreak() throws InterruptedException
	{
		Random rand = new Random();
		Thread.sleep(rand.nextInt(2001) + 1000);
	}

	public Connection connectToDb()
	{
		try
		{
			// String driver = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://requestdb.cm9huvgic56n.us-east-1.rds.amazonaws.com:3306?useSSL=false&useUnicode=yes&characterEncoding=UTF-8";
			String username = "admin";
			String password = "yy123456";

			// Class.forName(driver);

			conn = DriverManager.getConnection(url, username, password);
			System.out.println("Connected to database");

			return conn;
		}
		catch(Exception e)
		{
			System.out.println(e);
		}

		return null;
	}
}
