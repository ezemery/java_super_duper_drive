package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;
	private LoginPage loginPage;
	private SignupPage signupPage;
	private HomePage homePage;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
		loginPage = new LoginPage(driver);
		signupPage = new SignupPage(driver);
		homePage = new HomePage(driver);
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	@Order(1)
	void TestSuccessfulSignUp(){
		driver.get("http://localhost:" + port + "/signup");
		signupPage.registerUser("Ezechukwu","Emmanuel","eze09","ezemery");
	}

	@Test
	@Order(2)
	void TestSuccessfulLogin(){
		WebDriverWait wait = new WebDriverWait(driver, 10);
		driver.get("http://localhost:" + port + "/login");
		loginPage.loginUser("eze09","ezemery");
		WebElement noteTab = wait.until(ExpectedConditions.elementToBeClickable(homePage.NavNoteTab));
		WebElement fileTab = wait.until(ExpectedConditions.elementToBeClickable(homePage.NavFileTab));
		WebElement credentialTab = wait.until(ExpectedConditions.elementToBeClickable(homePage.NavCredentialsTab));
		assertEquals("Notes", noteTab.getText());
		assertEquals("Files", fileTab.getText());
		assertEquals("Credentials", credentialTab.getText());
	}

	@Test
	@Order(3)
	void TestUnauthorizedAccessRestrictions() {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		driver.get("http://localhost:" + port + "/home");
		Boolean loginUrl = wait.until(ExpectedConditions.urlContains("/login"));
		assertEquals(loginUrl, true);
		TestSuccessfulLogin();
		homePage.LogoutButton.submit();
		assertEquals(loginUrl, true);
	}

	@Test
	@Order(4)
	void TestCreateNote(){
		WebDriverWait wait = new WebDriverWait(driver, 10);
		TestSuccessfulLogin();
		WebElement noteTab = wait.until(ExpectedConditions.elementToBeClickable(homePage.NavNoteTab));
		noteTab.click();
		WebElement newNoteButton = wait.until(ExpectedConditions.elementToBeClickable(homePage.NewNoteButton));
		newNoteButton.click();
		WebElement noteTabModal = wait.until(ExpectedConditions.visibilityOf(homePage.NewNoteModal));
		homePage.NoteTitle.sendKeys("Hello");
		homePage.NoteDescription.sendKeys("Stop Crying");
		homePage.NoteSubmit.submit();
		Boolean createNoteSuccess = wait.until(ExpectedConditions.urlContains("success"));
		noteTab.click();
		List<WebElement> noteList = homePage.DisplayNoteTitle;
		assertEquals(1, noteList.size());
		assertEquals(createNoteSuccess, true);
	}

	@Test
	@Order(5)
	void TestEditNote(){
		WebDriverWait wait = new WebDriverWait(driver, 10);
		TestSuccessfulLogin();
		WebElement noteTab = wait.until(ExpectedConditions.elementToBeClickable(homePage.NavNoteTab));
		noteTab.click();
		WebElement noteEditButton = wait.until(ExpectedConditions.elementToBeClickable(homePage.EditNoteButton));
		noteEditButton.click();
		WebElement noteEditTabModal = wait.until(ExpectedConditions.visibilityOf(homePage.EditNoteModal));
		homePage.EditNoteTitle.clear();
		homePage.EditNoteTitle.sendKeys("How are you doing");
		homePage.EditNoteDescription.clear();
		homePage.EditNoteDescription.sendKeys("Very fine, Thanks");
		homePage.EditNoteSubmit.submit();
		Boolean editNoteSuccess = wait.until(ExpectedConditions.urlContains("success"));
		assertEquals(editNoteSuccess, true);
	}

	@Test
	@Order(6)
	void TestDeleteNote(){
		WebDriverWait wait = new WebDriverWait(driver, 10);
		TestSuccessfulLogin();
		WebElement noteTab = wait.until(ExpectedConditions.elementToBeClickable(homePage.NavNoteTab));
		noteTab.click();
		WebElement noteDeleteButton = wait.until(ExpectedConditions.elementToBeClickable(homePage.DeleteNoteButton));
		noteDeleteButton.click();
		Boolean deleteNoteSuccess = wait.until(ExpectedConditions.urlContains("success"));
		noteTab.click();
		List<WebElement> noteList = homePage.DisplayNoteTitle;
		assertEquals(deleteNoteSuccess, true);
		assertEquals(0, noteList.size());
	}

	@Test
	@Order(7)
	void TestCreateCredential(){
		WebDriverWait wait = new WebDriverWait(driver, 10);
		TestSuccessfulLogin();
		WebElement credentialTab = wait.until(ExpectedConditions.elementToBeClickable(homePage.NavCredentialsTab));
		credentialTab.click();
		WebElement newCredentialButton = wait.until(ExpectedConditions.elementToBeClickable(homePage.NewCredentialButton));
		newCredentialButton.click();
		WebElement credentialTabModal = wait.until(ExpectedConditions.visibilityOf(homePage.CredentialModal));
		homePage.CredentialUrl.sendKeys("https://google.com");
		homePage.CredentialUsername.sendKeys("ezemery");
		homePage.CredentialPassword.sendKeys("123456789");
		homePage.CredentialSubmit.submit();
		Boolean createCredentialSuccess = wait.until(ExpectedConditions.urlContains("success"));
		credentialTab.click();
		List<WebElement> credentialList = homePage.DisplayCredentialUrl;
		assertEquals(1, credentialList.size());
		assertEquals(createCredentialSuccess, true);

	}

	@Test
	@Order(8)
	void TestEditCredential(){
		WebDriverWait wait = new WebDriverWait(driver, 10);
		TestSuccessfulLogin();
		WebElement credentialTab = wait.until(ExpectedConditions.elementToBeClickable(homePage.NavCredentialsTab));
		credentialTab.click();
		WebElement credentialEditButton = wait.until(ExpectedConditions.elementToBeClickable(homePage.EditCredentialButton));
		credentialEditButton.click();
		WebElement credentialEditTabModal = wait.until(ExpectedConditions.visibilityOf(homePage.EditCredentialModal));
		homePage.CredentialEditUrl.clear();
		homePage.CredentialEditUrl.sendKeys("https://facebook.com");
		homePage.CredentialEditUsername.clear();
		homePage.CredentialEditUsername.sendKeys("eze09");
		homePage.CredentialEditPassword.clear();
		homePage.CredentialEditPassword.sendKeys("987654321");
		homePage.CredentialEditSubmit.submit();
		Boolean editCredentialSuccess = wait.until(ExpectedConditions.urlContains("success"));
		assertEquals(editCredentialSuccess, true);
	}

	@Test
	@Order(9)
	void TestDeleteCredential(){
		WebDriverWait wait = new WebDriverWait(driver, 10);
		TestSuccessfulLogin();
		WebElement credentialTab = wait.until(ExpectedConditions.elementToBeClickable(homePage.NavCredentialsTab));
		credentialTab.click();
		WebElement credentialDeleteButton = wait.until(ExpectedConditions.elementToBeClickable(homePage.DeleteCredentialButton));
		credentialDeleteButton.click();
		Boolean deleteCredentialSuccess = wait.until(ExpectedConditions.urlContains("success"));
		credentialTab.click();
		List<WebElement> credentialList = homePage.DisplayCredentialUrl;
		assertEquals(deleteCredentialSuccess, true);
		assertEquals(0, credentialList.size());

	}

}
