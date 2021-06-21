import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.asserts.SoftAssert;

public class SignInPage {

    String signInPageURL = "https://qatest-28flsd5.meshmd.com/SignIn?r=%2F";

    private WebDriver driver;
    public SignInPage(WebDriver driver) {
        this.driver = driver;
    }




    @FindBy(css = "[href='?lang=fr']") public WebElement switchLang;
    @FindBy(id = "user-email") public WebElement emailInput;
    @FindBy(id = "user-password") public WebElement passInput;
    @FindBy(css = "[href=\"/passwordForgot\"]") public WebElement forgotPass;
    @FindBy(css = "[type=\"submit\"]") public WebElement signInBtn;
    @FindBy(css = "[href=\"/register\"]") public WebElement registerBtn ;
    @FindBy(css = "[href=\"/terms\"]") public WebElement termsOfUse ;
    @FindBy(css = "[href=\"/privacy\"]") public WebElement privacyPolicy ;






    public void checkUrl(WebElement element, String expectedURL) {


        try {

            driver.get(signInPageURL);
            Thread.sleep(2000);
            //WebElement webElement = driver.findElement(By);
            element.click();
            Thread.sleep(1000);
            SoftAssert softassert = new SoftAssert();
            String originalURL  = driver.getCurrentUrl();
            if (expectedURL.equals(originalURL) ) System.out.println("expectedURL: "+expectedURL+" Is Ok");
            softassert.assertEquals(expectedURL, originalURL);



        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }





        }




