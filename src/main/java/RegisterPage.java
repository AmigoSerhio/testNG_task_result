import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class RegisterPage {


    private WebDriver driver;
    private Actions actions;
    private WebDriverWait wait;


    String registerPageURL = "https://qatest-28flsd5.meshmd.com/register";
    String testDataWay = "..\\LEARNING_Test_task\\src\\main\\TestData.xlsx";
    String snapshotPath = "..\\LEARNING_Test_task\\src\\main\\Snapshots\\";


    public RegisterPage(WebDriver driver, Actions actions, WebDriverWait wait) throws FilloException {
        this.driver = driver;
        this.actions = actions;
        this.wait = wait;
        PageFactory.initElements(driver, this);


    }


    By userType = By.id("usertype");
    By firstNameInp = By.id("first");
    By lastNameInp =By.id("last");
    By emailInp = By.id("email");
    By emailVerifyInp = By.id("email_verify");
    By licenseNumberInp = By.id("cpso");
    By specialtySel = By.id("specialty");
    By birthDateInp = By.id("birthdate");
    By genderSel = By.id("gender");
    By languageSel = By.id("language");
    By provinceSel = By.id("prov");
    By btn_submit = By.id("btn_submit");
    By errorM1 = By.xpath("//body//*[contains(text(),'Invalid')]");
    By errorM2 = By.xpath("//body//*[contains(text(),'mandatory')]");
    By errorM3 = By.xpath("//body//*[contains(text(),'error')]");





    public String extractData(String dataWay, String iD, String columnName ) throws FilloException {

        Fillo extractFillo=new Fillo();
        Connection connection=extractFillo.getConnection(dataWay);
            //System.out.println(iD);
            String strQuery=("Select * from Sheet1 where ID=");
            strQuery+= iD;
            Recordset recordset=connection.executeQuery(strQuery);
            recordset.next();
            String result =  recordset.getField(columnName);
        connection.close();
            return result;

    }

    public String putData( String iD, String value ) throws FilloException {
        Fillo putFillo=new Fillo();
        Connection connection=putFillo.getConnection(testDataWay);
        String strQuery="Update Sheet1 Set Result"+"="+"'"+value+"'"+" "+"where ID"+"="+iD+"";
        connection.executeUpdate(strQuery);
        connection.close();
        return strQuery;

    }

    public boolean IsAnyErrors()
    {
        List<WebElement> elements = driver.findElements(By.cssSelector("[class=\"formerror\"]"));
        if (elements.isEmpty())
        {
            // element doesn't exist
            return false;
        }
        else
        {
            System.out.println("Error message text is: "+elements.get(0).getText());
            // element exists, check for visibility
            return elements.get(0).isDisplayed();
        }
    }

    public void checkRegisterFields() throws FilloException, IOException, InterruptedException {


        try {
            driver.get(registerPageURL);
            driver.manage().window().fullscreen();
            Thread.sleep(2000);

            JavascriptExecutor executor = (JavascriptExecutor)driver;
            executor.executeScript("document.body.style.zoom = '0.6'");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                    Fillo fillo=new Fillo();
                    Connection connection=fillo.getConnection(testDataWay);

        for(int i = 1; i < 5; i++) {

                    String strQuery=("Select * from Sheet1 where ID=");
                    strQuery+=Integer.toString(i);
                    Recordset recordset=connection.executeQuery(strQuery);
                    recordset.next();

            Select selectuserType = new Select(driver.findElement(userType));
            selectuserType.selectByVisibleText(recordset.getField("User_Type"));

            Select speciality = new Select(driver.findElement(specialtySel));
            speciality.selectByVisibleText(recordset.getField("Speciality"));

            driver.findElement(firstNameInp).clear();
            String firstName = recordset.getField("First_Name");
            driver.findElement(firstNameInp).sendKeys(firstName);

            driver.findElement(lastNameInp).clear();
            String lastName = recordset.getField("Last_Name");
            driver.findElement(lastNameInp).sendKeys(lastName);

            driver.findElement(emailInp).clear();
            String email = recordset.getField("Email_Address");
            driver.findElement(emailInp).sendKeys(email);

            driver.findElement(emailVerifyInp).clear();
            String email_verify = recordset.getField("Verify_Email_Address");
            driver.findElement(emailVerifyInp).sendKeys(email_verify);

            driver.findElement(licenseNumberInp).clear();
            String licenseNumber = recordset.getField("Licensy_Number");
            driver.findElement(licenseNumberInp).sendKeys(licenseNumber);



            driver.findElement(birthDateInp).clear();
            String birthDate = recordset.getField("Birthdate");
            driver.findElement(birthDateInp).sendKeys(birthDate, Keys.TAB);

            Select selectGenderSel = new Select(driver.findElement(genderSel));
            selectGenderSel.selectByVisibleText(recordset.getField("Gender"));

            Select selectLanguageSel = new Select(driver.findElement(languageSel));
            selectLanguageSel.selectByVisibleText(recordset.getField("Language_Preference"));

            Select selectProvinceSel = new Select(driver.findElement(provinceSel));
            selectProvinceSel.selectByVisibleText(recordset.getField("Province"));

            Thread.sleep(2000);

                   wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(btn_submit)));
                   actions.click(driver.findElement(btn_submit));





            if(this.IsAnyErrors()){

                snapshotPath+=Integer.toString(i)+"_checkRegisterFieldssnap.png";
                File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
                FileUtils.copyFile(scrFile, new File(snapshotPath));
                driver.navigate().refresh();
                Thread.sleep(500);
            }
            else {
                System.out.println("Test: "+Integer.toString(i)+ "Is Passed");

            }
                                                                            recordset.close();}
                                                                                 connection.close();
    }



        public void checkInputs( String locator, String id, String column, String resId){

            try {
                driver.get(registerPageURL);
                Thread.sleep(2000);

                WebElement webElement = driver.findElement(By.id(locator));
                webElement.sendKeys(this.extractData(this.testDataWay,id, column));


                Thread.sleep(500);
                webElement.sendKeys(Keys.TAB);
                Thread.sleep(500);


                    if(this.IsAnyErrors()){


                        System.out.println("User NOT able to use a " + column + "  at the " + locator +  " field)");
                        this.putData(resId, "User NOT able to use a " + column + "  at the " + locator +  " field");

                        snapshotPath+=locator+"errorSnap.png";
                        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
                        FileUtils.copyFile(scrFile, new File(snapshotPath));
                        driver.navigate().refresh();
                        Thread.sleep(500);

                    }
                    else {

                        System.out.println("User able to use a " + column + "  at the " + locator + " field)");
                        this.putData(resId, "User able to use a " + column + "  at the " + locator + " field)" );
                    }


            } catch (InterruptedException | FilloException | IOException e) {
                e.printStackTrace();
            }


}



    static File resultFile = new File("..\\LEARNING_Test_task\\src\\main\\TestData.xlsx");
    public static DataFormatter formatter = new DataFormatter();
    @DataProvider
    public static Object[][] readExcelFile() throws InvalidFormatException, IOException {
        FileInputStream fis = new FileInputStream(resultFile);
        XSSFWorkbook wb = new XSSFWorkbook(fis);
        XSSFSheet sh = wb.getSheet("Sheet2");

        /*System.out.println(sh.getPhysicalNumberOfRows());
        System.out.println(sh.getRow(0).getPhysicalNumberOfCells());*/
        int RowNum = sh.getPhysicalNumberOfRows();
        int ColNum = sh.getRow(0).getPhysicalNumberOfCells();

        String[][] xlData = new String[RowNum-1][ColNum];

        for (int i = 0; i < RowNum - 1; i++)
        {
            XSSFRow row = sh.getRow(i + 1);
            for (int j = 0; j < ColNum; j++)
            {
                if (row == null)
                    xlData[i][j] = "";
                else {
                    XSSFCell cell = row.getCell(j);
                    if (cell == null)
                        xlData[i][j] = "";
                    else {
                        String value = formatter.formatCellValue(cell);
                        xlData[i][j] = value.trim();
                    }
                }
            }
        }
        return xlData;
    }



    public void checkRegisterFieldswithDP(String id,
                                          String	User_Type,
                                          String First_Name,
                                          String	Last_Name,
                                          String	Email_Address,
                                          String	Verify_Email_Address,
                                          String	Licensy_Number,
                                          String	Speciality,
                                          String	Birthdate,
                                          String	Gender,
                                          String	Language_Preference,
                                          String	Province) throws FilloException, IOException, InterruptedException {


        try {
            driver.get(registerPageURL);
            driver.manage().window().fullscreen();
            Thread.sleep(2000);

            JavascriptExecutor executor = (JavascriptExecutor)driver;
            executor.executeScript("document.body.style.zoom = '0.6'");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }


            Select selectuserType = new Select(driver.findElement(userType));
            selectuserType.selectByVisibleText(User_Type);

            Select speciality = new Select(driver.findElement(specialtySel));
            speciality.selectByVisibleText(Speciality);

            driver.findElement(firstNameInp).clear();
            driver.findElement(firstNameInp).sendKeys(First_Name);

            driver.findElement(lastNameInp).clear();

            driver.findElement(lastNameInp).sendKeys(Last_Name);

            driver.findElement(emailInp).clear();
            driver.findElement(emailInp).sendKeys(Email_Address);

            driver.findElement(emailVerifyInp).clear();
            driver.findElement(emailVerifyInp).sendKeys(Verify_Email_Address);

            driver.findElement(licenseNumberInp).clear();
            driver.findElement(licenseNumberInp).sendKeys(Licensy_Number);

            driver.findElement(birthDateInp).clear();
            driver.findElement(birthDateInp).sendKeys(Birthdate, Keys.TAB);

            Select selectGenderSel = new Select(driver.findElement(genderSel));
            selectGenderSel.selectByVisibleText(Gender);

            Select selectLanguageSel = new Select(driver.findElement(languageSel));
            selectLanguageSel.selectByVisibleText(Language_Preference);

            Select selectProvinceSel = new Select(driver.findElement(provinceSel));
            selectProvinceSel.selectByVisibleText(Province);

            Thread.sleep(2000);

            wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(btn_submit)));
            actions.click(driver.findElement(btn_submit));



        if(this.IsAnyErrors()){


            snapshotPath+=id+"DPsnap.png";
            File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile, new File(snapshotPath));
            driver.navigate().refresh();
            Thread.sleep(500);

        }
        else {
            System.out.println("Test: "+id+ "Is Passed");

        }


            }


}

