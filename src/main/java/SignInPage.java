
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

public class SignInPage {

    String signInPageURL = "https://qatest-28flsd5.meshmd.com/SignIn?r=%2F";

    private WebDriver driver;
    public SignInPage(WebDriver driver) {
        this.driver = driver;
    }




    By switchLang = By.cssSelector("[href='?lang=fr']");

    By emailInput = By.id("user-email");

    By passInput = By.id("user-password");

    By forgotPass = By.cssSelector("[href=\"/passwordForgot\"]");

    By signInBtn = By.cssSelector("[type=\"submit\"]");

    By registerBtn = By.cssSelector("[href=\"/register\"]");

    By termsOfUse = By.cssSelector("[href=\"/terms\"]");

    By privacyPolicy = By.cssSelector("[href=\"/privacy\"]");




    public void checkUrl(By by, String expectedURL) {


        try {

            driver.get(signInPageURL);
            Thread.sleep(2000);
            WebElement webElement = driver.findElement(by);
            webElement.click();
            Thread.sleep(1000);
            SoftAssert softassert = new SoftAssert();
            String originalURL  = driver.getCurrentUrl();
            if (expectedURL.equals(originalURL) ) System.out.println("expectedURL: "+expectedURL+" Is Ok");
            softassert.assertEquals(expectedURL, originalURL);



        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }




    public void BrokenLinks (){



        // TODO Auto-generated method stub


        String url = "";
        HttpURLConnection huc = null;
        int respCode = 200;


        driver.manage().window().maximize();

        driver.get(signInPageURL);

        List<WebElement> links = driver.findElements(By.tagName("a"));

        Iterator<WebElement> it = links.iterator();

        while(it.hasNext()){

            url = it.next().getAttribute("href");

            System.out.println(url);

            if(url == null || url.isEmpty()){
                System.out.println("URL is either not configured for anchor tag or it is empty");
                continue;
            }

            if(!url.startsWith(this.signInPageURL)){
                System.out.println("URL belongs to another domain, skipping it.");

                continue;
            }

            try {
                huc = (HttpURLConnection)(new URL(url).openConnection());

                huc.setRequestMethod("HEAD");

                huc.connect();

                respCode = huc.getResponseCode();

                if(respCode >= 400){
                    System.out.println(url+" is a broken link");
                }
                else{
                    System.out.println(url+" is a valid link");
                }

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        driver.quit();
    }
        }




