package coverFoxTest;

import java.io.IOException;

import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.EncryptedDocumentException;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import coverFoxBase.Base;
import coverFoxPOM.CoverFoxAddressDetailsPage;
import coverFoxPOM.CoverFoxHealthPlanPage;
import coverFoxPOM.CoverFoxHomePage;
import coverFoxPOM.CoverFoxMemberDetailsPage;
import coverFoxPOM.CoverFoxResultPage;
import coverFoxUtility.Utility;

import org.apache.log4j.Category;
import org.apache.log4j.Logger;

public class TC1234_CoverFox_ValidateBannerCount extends Base {
	CoverFoxHomePage homePage;
	CoverFoxHealthPlanPage healthPlanPage;
	CoverFoxMemberDetailsPage memberDetailsPage;
	CoverFoxAddressDetailsPage addressDetailsPage;
	CoverFoxResultPage resultPage;
	public static Logger logger;
	String excelpath = System.getProperty("user.dir") + "\\excelData\\seleniumExcel.xlsx";
	String sheetName = "Sheet1";

// open browser/open an application
	@BeforeClass
	public void openApplication() throws IOException {
		launchBorwser();
	}

// gender,next click, age selection, pincode, mobile,nect click
	@BeforeMethod
	public void enterDetails() throws EncryptedDocumentException, IOException, InterruptedException {

		homePage = new CoverFoxHomePage(driver);
		healthPlanPage = new CoverFoxHealthPlanPage(driver);
		memberDetailsPage = new CoverFoxMemberDetailsPage(driver);
		addressDetailsPage = new CoverFoxAddressDetailsPage(driver);
		resultPage = new CoverFoxResultPage(driver);
		 logger = Logger.getLogger("MyMavenProject");
		PropertyConfigurator.configure("log4j.properties");
		
		logger.info("Click on gender button..");
		homePage.clickOnGenderButton();
		Thread.sleep(1000);
		healthPlanPage.clickOnNextButton();
		Thread.sleep(1000);
		memberDetailsPage.handleAgeDropDown(Utility.readDataFromExcel(excelpath,

				sheetName, 0, 0));

		memberDetailsPage.clickOnNextButton();
		Thread.sleep(1000);

		addressDetailsPage.enterPinCode(Utility.readDataFromExcel(excelpath,

				sheetName, 0, 1));

		addressDetailsPage.enterMobNum(Utility.readDataFromExcel(excelpath,

				sheetName, 0, 2));

		addressDetailsPage.clickOnContinueButton();
		Thread.sleep(5000);
	}

@Test
public void valiadatePolicyCount() {
int textCount = resultPage.getCountFromText();
int bannerCount = resultPage.getCountFromBanner();
Assert.assertEquals(textCount, bannerCount, "text count not matching with banner count, TC failed");
//Assert.fail();
}

// logout From application
// close Browser/close an application
	@AfterClass
	public void closeApplication() {
		closeBrowser();
	}
}