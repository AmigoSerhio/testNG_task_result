import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static org.testng.Assert.assertEquals;

public class SignInPage {

    String signInPageURL = "https://qatest-28flsd5.meshmd.com/SignIn?r=%2F";

    private WebDriver driver;
    public SignInPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
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
            Thread.sleep(500);
            element.click();
            Thread.sleep(500);
            String originalURL  = driver.getCurrentUrl();
            if (expectedURL.equals(originalURL) ) System.out.println("expectedURL: "+expectedURL+" Is Ok");
            assertEquals(expectedURL, originalURL);



        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

        }




