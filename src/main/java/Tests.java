import com.codoid.products.exception.FilloException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;


public class Tests {
    private WebDriver driver;
    private Actions actions;
    private WebDriverWait wait;



            @BeforeMethod
            public void profileSetup() {

                System.setProperty("webdriver.chrome.driver", "..\\LEARNING_Test_task\\src\\main\\java\\chromedriver.exe");

                driver = new ChromeDriver();
                actions = new Actions(driver);
                wait = new WebDriverWait(driver, 1);

            }

    @Test(groups = {"SignInPage"}, description = "should validate all links on the page", enabled = true)
    public void task_1() {


        SignInPage signInPage = new SignInPage(driver);

        signInPage.checkUrl(signInPage.registerBtn, "https://qatest-28flsd5.meshmd.com/register");
        signInPage.checkUrl(signInPage.switchLang, "https://qatest-28flsd5.meshmd.com/SignIn?lang=fr");
        signInPage.checkUrl(signInPage.forgotPass, "https://qatest-28flsd5.meshmd.com/passwordForgot");
        signInPage.checkUrl(signInPage.termsOfUse, "https://qatest-28flsd5.meshmd.com/terms");
        signInPage.checkUrl(signInPage.privacyPolicy, "https://qatest-28flsd5.meshmd.com/privacy");

    }


    @Test(groups = {"RegisterPage"}, description = "should check inputs separately", enabled = true)

    public void task_2() throws FilloException {


        RegisterPage registerPage = new RegisterPage(driver, actions, wait);

        String testcombinations = "..\\LEARNING_Test_task\\src\\main\\combinations.xlsx";
        for (int i = 1; i < 18; i++) {

            String elem = registerPage.extractData(testcombinations, Integer.toString(i), "element");
            String column = registerPage.extractData(testcombinations, Integer.toString(i), "column");
            String tested_id = registerPage.extractData(testcombinations, Integer.toString(i), "tested_id");

            registerPage.checkInputs(elem, tested_id, column, Integer.toString(i));

        }


    }

    @Test(groups = {"RegisterPage"}, dataProvider = "readExcelFile",dataProviderClass = RegisterPage.class,
            description = "should check registration form with data from excel using @Dataprovider" , enabled = true)

    public void task_3(String id,
                      String User_Type,
                      String First_Name,
                      String Last_Name,
                      String Email_Address,
                      String Verify_Email_Address,
                      String Licensy_Number,
                      String Speciality,
                      String Birthdate,
                      String Gender,
                      String Language_Preference,
                      String Province) throws FilloException, IOException, InterruptedException {

        RegisterPage registerPage = new RegisterPage(driver, actions, wait);

        registerPage.checkRegisterFieldswithDP(id,User_Type,First_Name,Last_Name,Email_Address,Verify_Email_Address,Licensy_Number,Speciality,Birthdate,Gender,Language_Preference,Province);
    }


                    @AfterMethod
                    public void after() {
                        driver.close();
                    }

                }

