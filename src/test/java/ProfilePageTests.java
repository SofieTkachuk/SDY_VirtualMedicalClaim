import Consts.Consts;
import Pages.LoginPage;
import Pages.MainPage;
import Pages.ProfilePage;
import Utils.PropertiesUtil;
import Utils.UseCaseBase;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static Pages.BasePage.logger;
import static org.junit.jupiter.api.Assertions.*;

public class ProfilePageTests extends UseCaseBase {
    private static ProfilePage profilePage;
    private static LoginPage loginPage;
    private static MainPage mainPage;

    @BeforeAll
    public static void classSetup() {
        loginPage = new LoginPage();
        profilePage = new ProfilePage();
        mainPage = new MainPage();

        logger.info("Setting up tests and logging in...");
        loginPage.navigateToLoginPage();
        loginPage.enterUsername(PropertiesUtil.getProperty("username"));
        loginPage.enterPassword(PropertiesUtil.getProperty("pass"));
        loginPage.clickLoginButton();
        // Users can go to the Profile window after the login procedure
        mainPage.openUserProfileDropdown();
        mainPage.openUserProfile();
        logger.info("Login successful, navigating to profile page.");
    }

    @BeforeEach
    public void resetPage(){
        String currentUrl = webDriver.getCurrentUrl();
        String profilePageUrl = profilePage.navigateToProfilePage();
        if (!currentUrl.equals(profilePageUrl)) {
            webDriver.navigate().to(profilePageUrl);
        }
    }

    @AfterEach
    public void resetFieldsToDefaultValue() {
        logger.info("Navigating to profile page.");
        profilePage.navigateToProfilePage();
        logger.info("Resetting credentials");
        profilePage.resetToDefaultValues();
    }

    @Test // Users can go to the Profile window after the login procedure
    public void profilePageLoadTest() throws IOException {
        logger.info("Getting current URL.");
        String pageLoaded = webDriver.getCurrentUrl();
        String pageExpected = profilePage.navigateToProfilePage();
        mainPage.takeScreenshot("ProfilePageLoadTest");
        assertEquals(pageExpected, pageLoaded, "The Profile Page wasn't loaded");
        logger.info("The profile page is loaded.");
    }

    @Test // The profile Page content is loaded
    public void profilePageLoadContentTest() throws IOException {
        logger.info("Checking if the profile form is visible.");
        assertTrue(profilePage.isFormVisible());
        mainPage.takeScreenshot("ProfilePageLoadTest");
        logger.info("The form is visible.");
    }

    @Test // 3 columns are exist
    public void columnsExistenceTest() {
        logger.info("Checking if all the columns are visible.");
        assertTrue(profilePage.areColumnsVisible());
        int columnCount = profilePage.countColumnsByXpath();
        assertEquals(3, columnCount, "The number of columns should be exactly 3.");
        logger.info("All 3 columns are visible.");
    }

    @Test // Verify header texts
    public void headerTextsTest() {
        List<WebElement> headers = profilePage.getHeaderTexts();
        assertEquals(3, headers.size(), "There should be exactly 3 headers.");
        logger.info("Normalizing texts.");
        List<String> actualTexts = headers.stream()
                .map(header -> profilePage.normalizeText(header.getText()))
                .collect(Collectors.toList());

        List<String> expectedTexts = Arrays.asList(
                profilePage.normalizeText("Personal Information:"),
                profilePage.normalizeText("Change password:"),
                profilePage.normalizeText("Personal Settings:")
        );

        assertEquals(expectedTexts, actualTexts, "Header texts do not match +");
        logger.info("The actual texts are as expected.");
    }

    // PERSONAL INFORMATION COLUMN TESTS
    @Test // Validate existence and values of a field
    public void validateEmailFieldTest() {
        logger.info("Checking for the email field visibility.");
        assertTrue(profilePage.isEmailFieldPresent());
        String emailFieldValue = profilePage.getEmailFieldValue();
        assertTrue(emailFieldValue != null && !emailFieldValue.isEmpty());
        String expectedEmail = PropertiesUtil.getProperty("email"); // an email is visible on the field by default
        assertEquals(expectedEmail, emailFieldValue, "The email is not visible.");
        logger.info("The email field is visible.");
    }

    @Test // Validate existence and values of a field
    public void validateFirstNameFieldTest() {
        logger.info("Checking for the first name field visibility.");
        assertTrue(profilePage.isFirstNameFieldPresent());
        String firstNameFieldValue = profilePage.getFirstNameFieldValue();
        assertTrue(firstNameFieldValue != null && !firstNameFieldValue.isEmpty());
        String expectedFirstName = PropertiesUtil.getProperty("first_name"); // first name is visible on the field by default
        assertEquals(expectedFirstName, firstNameFieldValue, "The first name is not visible.");
        logger.info("The first name field is visible.");
    }

    @Test // Validate that the reply-to field is present
    public void validateReplyToFieldTest() {
        logger.info("Checking for the reply to field visibility.");
        assertTrue(profilePage.isReplyToFieldPresent());
        String replyToFieldValue = profilePage.getReplyToFieldFieldValue();
        assertNotNull(replyToFieldValue, "The Reply-To field should not be null.");
        logger.info("The reply to field is visible.");
    }

    @Test // Validate existence and values of a field
    public void validateUsernameFieldTest() {
        logger.info("Checking for the username field visibility.");
        assertTrue(profilePage.isUsernameFieldPresent());
        String usernameFieldValue = profilePage.getUsernameFieldValue();
        assertTrue(usernameFieldValue != null && !usernameFieldValue.isEmpty());
        String expectedUsername = PropertiesUtil.getProperty("username"); // username is visible on the field by default
        assertEquals(expectedUsername, usernameFieldValue, "The username is not visible.");
        logger.info("The username field is visible.");
    }

    @Test // Validate existence and values of a field
    public void validateLastNameFieldTest() {
        logger.info("Checking for the last name field visibility.");
        assertTrue(profilePage.isLastNameFieldPresent());
        String lastNameFieldValue = profilePage.getLastNameFieldValue();
        assertTrue(lastNameFieldValue != null && !lastNameFieldValue.isEmpty());
        String expectedLastName = PropertiesUtil.getProperty("last_name"); // // last name is visible on the field by default
        assertEquals(expectedLastName, lastNameFieldValue, "The last name is not visible.");
        logger.info("The last name field is visible.");
    }

    @Test // Verify that a user can clear fields
    public void personalInformationClearFieldsTest() throws IOException {
        logger.info("Clearing all Personal Information fields.");
        profilePage.clearFields
                (Consts.EMAIL_FIELD_PROFILE,
                        Consts.FIRST_NAME_FIELD_PROFILE,
                        Consts.USER_NAME_FIELD,
                        Consts.LAST_NAME_FIELD_PROFILE);

        mainPage.takeScreenshot("ClearFieldsTest");

        assertTrue(profilePage.getEmailFieldValue().isEmpty(), "The email field is not empty.");
        assertTrue(profilePage.getFirstNameFieldValue().isEmpty(),"The first name field is not empty.");
        assertTrue(profilePage.getUsernameFieldValue().isEmpty(), "The username field is not empty.");
        assertTrue(profilePage.getLastNameFieldValue().isEmpty(), "The last name field is not empty.");
        logger.info("Fields are cleared");
    }

    @Test // Users can enter text to fields
    public void personalInformationTextInput() throws IOException {
        profilePage.clearFields
                (Consts.EMAIL_FIELD_PROFILE,
                        Consts.FIRST_NAME_FIELD_PROFILE,
                        Consts.USER_NAME_FIELD,
                        Consts.LAST_NAME_FIELD_PROFILE);

        logger.info("Checking if a user can input values to the fields.");
        profilePage.enterEmail(PropertiesUtil.getProperty("email"));
        profilePage.enterFirstName(PropertiesUtil.getProperty("first_name_updated"));
        profilePage.enterReplyTo(PropertiesUtil.getProperty("email"));
        profilePage.enterUsername(PropertiesUtil.getProperty("username_updated"));
        profilePage.enterLastName(PropertiesUtil.getProperty("last_name_updated"));

        mainPage.takeScreenshot("InputValuesToFieldsTest");

        assertEquals(PropertiesUtil.getProperty("email"), profilePage.getEmailFieldValue(), "Email field should have a value.");
        assertEquals(PropertiesUtil.getProperty("first_name_updated"), profilePage.getFirstNameFieldValue(), "First Name field should have a value.");
        assertEquals(PropertiesUtil.getProperty("email"), profilePage.getReplyToFieldFieldValue(), "Reply to field should have a value.");
        assertEquals(PropertiesUtil.getProperty("username_updated"), profilePage.getUsernameFieldValue(), "Username field should have a value.");
        assertEquals(PropertiesUtil.getProperty("last_name_updated"), profilePage.getLastNameFieldValue(), "Last Name field should have a value.");
        logger.info("Value is entered.");
    }

    @Test // Validate that the fields are mandatory
    public void mandatoryFieldsTest(){
        WebElement emailField = webDriver.findElement(By.xpath(Consts.EMAIL_FIELD_PROFILE));
        WebElement firstNameField = webDriver.findElement(By.xpath(Consts.FIRST_NAME_FIELD_PROFILE));
        WebElement usernameField = webDriver.findElement(By.xpath(Consts.USER_NAME_FIELD));
        WebElement lastNameField = webDriver.findElement(By.xpath(Consts.LAST_NAME_FIELD_PROFILE));

        assertNotNull(emailField.getAttribute("required"), "Email field is not marked as mandatory.");
        assertNotNull(firstNameField.getAttribute("required"), "First Name field is not marked as mandatory.");
        assertNotNull(usernameField.getAttribute("required"), "Username field is not marked as mandatory.");
        assertNotNull(lastNameField.getAttribute("required"), "Last Name field is not marked as mandatory.");
        logger.info("The field is mandatory");
    }

    @Test // Validate that the error message is appeared when trying to update info with an empty value
    public void emailErrorMessageEmptyValue() throws IOException {
        String emailField = Consts.EMAIL_FIELD_PROFILE;
        logger.info("Clearing an email field.");
        profilePage.clearFields(emailField);
        profilePage.clickUpdateInformationButton();
        mainPage.takeScreenshot("EmptyEmailErrorMsg");
        assertTrue(profilePage.isErrorMessageDisplayed("an error occurred"), "The error message did not appear.");
        logger.info("The system does not allow to update information with an empty email.");
    }

    @Test // Validate that the first name can't be updated when there's an empty value
    public void firstNameEmptyValueUpdates() throws IOException {
        String firstNameField = Consts.FIRST_NAME_FIELD_PROFILE;
        logger.info("Clearing a first name field.");
        profilePage.clearFields(firstNameField);
        profilePage.clickUpdateInformationButton();
        mainPage.navigateToMainPage();
        profilePage.navigateToProfilePage();
        mainPage.takeScreenshot("EmptyFirstNameErrorMsg");
        assertEquals(PropertiesUtil.getProperty("first_name"), profilePage.getFirstNameFieldValue(),"The field should not be empty.");
        logger.info("The system does not allow to update information with an empty first name.");
    }

    @Test // Validate that the error message is appeared when trying to update info with an empty value
    public void usernameErrorMessageEmptyValue() throws IOException {
        String usernameField = Consts.USER_NAME_FIELD;
        logger.info("Clearing a username field.");
        profilePage.clearFields(usernameField);
        profilePage.clickUpdateInformationButton();
        mainPage.takeScreenshot("EmptyUsernameErrorMsg");
        assertTrue(profilePage.isErrorMessageDisplayed("an error occurred"), "The error message did not appear.");
        logger.info("The system does not allow to update information with an empty username.");
    }

    @Test // Validate that the first name can't be updated when there's an empty value
    public void lastNameEmptyValueUpdates() throws IOException {
        String lastNameField = Consts.LAST_NAME_FIELD_PROFILE;
        logger.info("Clearing a last name field.");
        profilePage.clearFields(lastNameField);
        profilePage.clickUpdateInformationButton();
        mainPage.navigateToMainPage();
        profilePage.navigateToProfilePage();
        mainPage.takeScreenshot("EmptyLastNameErrorMsg");
        assertEquals(PropertiesUtil.getProperty("last_name"), profilePage.getLastNameFieldValue(),"The field should not be empty.");
        logger.info("The system does not allow to update information with an empty last name.");
    }

    @ParameterizedTest // Validate that the information cannot be updated with an invalid email format
    @ValueSource(strings = {"test.io.com", "test@io@epam.com", "test(ioepamexamplecom", ".test... iotoday@epam.com", "email_test"})
    public void emailErrorMessageTestInvalidValue(String invalidEmail) throws IOException {
        profilePage.clearFields(Consts.EMAIL_FIELD_PROFILE);
        profilePage.enterEmail(invalidEmail);
        profilePage.clickUpdateInformationButton();
        mainPage.takeScreenshot("InvalidEmailErrorMsg");
        assertTrue(profilePage.isErrorMessageDisplayed("an error occurred"), "The error message did not appear.");
        logger.info("The system does not allow to update information with an invalid email format.");
    }

    @ParameterizedTest // Validate that the information cannot be updated with an invalid username format
    @ValueSource(strings = {"Users 1 and 2", "@#$%^&amp", "*()_+{}|:&lt", "&gt;?"})
    public void usernameErrorMessageTestInvalidValue(String invalidUsername) throws IOException {
        profilePage.clearFields(Consts.USER_NAME_FIELD);
        profilePage.enterUsername(invalidUsername);
        profilePage.clickUpdateInformationButton();
        mainPage.takeScreenshot("InvalidUsernameErrorMsg");
        assertTrue(profilePage.isErrorMessageDisplayed("an error occurred"), "The error message did not appear.");
        logger.info("The system does not allow to update information with an invalid username format.");
    }

    // CHANGE PASSWORD COLUMN TESTS
    @Test // Validate that the old password field is present and empty
    public void validateOldPasswordFieldTest() {
        logger.info("Checking presence of an old password field.");
        assertTrue(profilePage.isOldPasswordFieldPresent());
        String oldPassValue = profilePage.getOldPasswordFieldValue();
        assertNotNull(oldPassValue, "The field should not be null.");
        logger.info("The field is present.");
    }

    @Test // Validate that the new password field is present and empty
    public void validateNewPasswordFieldTest() {
        logger.info("Checking presence of an new password field.");
        assertTrue(profilePage.isNewPasswordFieldPresent());
        String newPassValue = profilePage.getNewPasswordFieldValue();
        assertNotNull(newPassValue, "The field should not be null.");
        logger.info("The new password field is present.");
    }

    @Test // Validate that the field is present and empty
    public void validateRepeatNewPasswordFieldTest() {
        logger.info("Checking presence of an repeat new password field.");
        assertTrue(profilePage.isRepeatNewPasswordFieldPresent());
        String repeatNewPassValue = profilePage.getRepeatNewPasswordFieldValue();
        assertNotNull(repeatNewPassValue, "The field should not be null.");
        logger.info("The repeat new password field is present.");
    }

    @Test // Validate that users can input a value to fields
    public void changePasswordInputValue() throws IOException {
        logger.info("Entering a value to the field.");
        profilePage.enterOldPassword(PropertiesUtil.getProperty("pass"));
        profilePage.setNewPassword(PropertiesUtil.getProperty("new_password"));
        profilePage.setRepeatNewPass(PropertiesUtil.getProperty("new_password"));

        mainPage.takeScreenshot("ChangePassInputTest");
        assertEquals(PropertiesUtil.getProperty("pass"), profilePage.getOldPassValue(), "Email field don't have a value.");
        assertEquals(PropertiesUtil.getProperty("new_password"), profilePage.getNewPassValue(), "First Name field don't have a value.");
        assertEquals(PropertiesUtil.getProperty("new_password"), profilePage.getRepeatNewPassValue(), "Username field don't have a value.");
        logger.info("The pass value is entered.");
    }

    @Test
    // Validate that the entered data is concealed with dots by default and is visible after pressing on a button (an eye icon) and each button works independently and accordingly to its field
    public void concealedData() throws IOException {
        List<WebElement> eyeIcons = webDriver.findElements(By.xpath(Consts.EYE_ICON));
        List<WebElement> passwordFields = Arrays.asList(
                webDriver.findElement(By.xpath(Consts.OLD_PASSWORD_FIELD)),
                webDriver.findElement(By.xpath(Consts.NEW_PASSWORD_FIELD)),
                webDriver.findElement(By.xpath(Consts.REPEAT_NEW_PASSWORD_FIELD))
        );

        mainPage.takeScreenshot("MaskedDataTest");
        assertEquals(3, eyeIcons.size(), "There should be exactly 3 eye icons.");
        assertEquals(3, passwordFields.size(), "There should be exactly 3 password fields.");

        // Verify that all password fields are masked by default
        for (int i = 0; i < passwordFields.size(); i++) {
            WebElement passwordField = passwordFields.get(i);
            assertEquals("password", passwordField.getAttribute("type"), "Password field " + i + " should be masked by default.");
        }

        // Check the visibility toggle functionality of each eye icon
        for (int i = 0; i < eyeIcons.size(); i++) {
            WebElement eyeIcon = eyeIcons.get(i);
            WebElement passwordField = passwordFields.get(i);

            // Click the eye icon to reveal the password
            eyeIcons.get(i).click();
            assertEquals("text", passwordFields.get(i).getAttribute("type"), "Password field " + i + " should be visible after clicking the eye icon.");

            // Click the eye icon again to conceal the password
            eyeIcon.click();
            assertEquals("password", passwordField.getAttribute("type"), "Password field " + i + " should be masked again after clicking the eye icon a second time.");
        }
        logger.info("The password field concealed with dots.");
    }

    @Test
    // Validate that the user can change the password with all valid inputs: a valid old password and a matching password rules new password
    public void changePasswordTest() throws IOException {
        profilePage.enterOldPassword(PropertiesUtil.getProperty("pass"));
        profilePage.setNewPassword(PropertiesUtil.getProperty("new_password"));
        profilePage.setRepeatNewPass(PropertiesUtil.getProperty("new_password"));

        profilePage.clickUpdateInformationButton();
        mainPage.takeScreenshot("ChangePassTest");
        assertTrue(profilePage.isSuccessMessagePresent());
        logger.info("Password changed.");

        profilePage.resetToDefaultPassword();
    }

    @ParameterizedTest // Validate password change with different invalid scenarios
    @CsvSource({
            "'', 'ThisIsIncorrectPass', 'ThisIsIncorrectPass", // Old password is empty
            "'Welcome123!', '', 'ThisIsIncorrectPass'", // New password is empty
            "'Welcome123!', 'ThisIsIncorrectPass', ''", // Repeat new password is empty
            "'WrongOldPassword!', 'ThisIsIncorrectPass', 'ThisIsIncorrectPass'", // Old password is incorrect
            "'Welcome123!', 'ThisIsIncorrectPass', 'TestIncorrect!1'" // New and repeat passwords do not match
    })
    public void validatePasswordChangeWithInputsNotMatch(String oldPassword, String newPassword, String repeatNewPassword) throws IOException {
        profilePage.enterOldPassword(oldPassword);
        profilePage.setNewPassword(newPassword);
        profilePage.setRepeatNewPass(repeatNewPassword);

        profilePage.clickUpdateInformationButton();
        assertTrue(profilePage.isErrorMessagePopUpPresent(), "The expected error message did not appear.");
        mainPage.takeScreenshot("NotFullInfoPassErrorMsg");
        logger.info("Password wasn't changed with empty pass field.");
    }

    @ParameterizedTest // Validate that the password cannot be changed if not meets requirements
    @ValueSource(strings = {"NoPas1!", "12345тутQ!", "12345hello!", "12345HELLO!", "ThisIsIncorrectPass!", "ThisIsIncorrectPass1"})
    public void validatePasswordChangeWithInvalidPassInputs(String passInputIncorrect) throws IOException {
        profilePage.enterOldPassword(PropertiesUtil.getProperty("pass"));
        profilePage.setNewPassword(passInputIncorrect);
        profilePage.setRepeatNewPass(passInputIncorrect);
        mainPage.takeScreenshot("InvalidInfoPassErrorMsg");
        profilePage.clickUpdateInformationButton();
        assertTrue(profilePage.isErrorMessagePopUpPresent(), "The expected error message did not appear.");
        logger.info(passInputIncorrect + "Password wasn't changed, not meeting requirements.");
    }

    // PERSONAL SETTINGS COLUMN TESTS
    @Test // Validate that the dropdown is present and empty
    public void validateDropdownExistenceTest() throws IOException {
        logger.info("Checking that dropdown is visible on the page.");
        assertTrue(profilePage.isDropdownPresent(), "Dropdown should be present on the page.");

        String value = webDriver.findElement(By.xpath(Consts.DROPDOWN_VALUE)).getText();
        mainPage.takeScreenshot("DropdownExistenceTest");
        assertEquals("", value, "Dropdown field is not empty.");
        logger.info("Dropdown is present.");
    }

    @ParameterizedTest // Validate dropdown selection
    @ValueSource(strings = {"4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "No Limit"})
    public void validateDropdownFunctionalTest(String expectedDropdownOption) throws IOException {
        logger.info("Clicking on dropdown.");
        profilePage.clickDropdown();
        logger.info("Checking that dropdown options are visible to select.");
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(2));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Consts.DROPDOWN_OPTIONS)));
        logger.info("Checking that dropdown option can be selected.");
        WebElement optionToSelect = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Consts.DROPDOWN_OPTION_TO_SELECT + expectedDropdownOption + "']")));
        optionToSelect.click();

        WebElement selectedItem = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Consts.DROPDOWN_VALUE)));
        String selectedValue = selectedItem.getText().trim();
        mainPage.takeScreenshot("SelectedValueDropdownTest");
        assertEquals(expectedDropdownOption, selectedValue, "Dropdown option not selected correctly.");
        logger.info("Selected option is visible.");

        if ("No Limit".equals(expectedDropdownOption)) {
            profilePage.resetDropdownToDefault();
        }
    }

    @Test // Validate that the checkboxes are present and clickable
    public void validateSimpleMainMenuCheckboxTest() throws IOException {
        logger.info("Checking that checkbox is visible on the page.");
        assertTrue(profilePage.isSimpleMainMenuCheckboxPresent(), "Simple Main Menu checkbox was not found on the Profile Page.");

        profilePage.clickSimpleMainMenuCheckbox();
        boolean isChecked = webDriver.findElement(By.xpath(Consts.SIMPLE_MAIN_MENU)).isSelected();
        mainPage.takeScreenshot("Checkbox1Selected");
        assertTrue(isChecked, "The checkbox should be checked.");
        logger.info("Checkbox clicked.");

        profilePage.clickSimpleMainMenuCheckbox();
        boolean isUnChecked = webDriver.findElement(By.xpath(Consts.SIMPLE_MAIN_MENU)).isSelected();
        mainPage.takeScreenshot("Checkbox1UnChecked");
        assertFalse(isUnChecked, "The checkbox should not be checked.");
        logger.info("Checkbox is unclicked.");
    }

    @Test // Validate that the checkboxes are present and clickable
    public void validateCustomMainMenuCheckboxTest() throws IOException {
        logger.info("Checking that checkbox is present on the page.");
        assertTrue(profilePage.isCustomMainMenuCheckboxPresent(), "Custom Main Menu checkbox was not found on the Profile Page.");

        profilePage.clickCustomMainMenuCheckbox();
        boolean isChecked = webDriver.findElement(By.xpath(Consts.CUSTOM_MAIN_MENU)).isSelected();
        mainPage.takeScreenshot("Checkbox2Selected");
        assertTrue(isChecked, "The checkbox should be checked.");
        logger.info("Checkbox is clicked.");

        profilePage.clickCustomMainMenuCheckbox();
        boolean unChecked = webDriver.findElement(By.xpath(Consts.CUSTOM_MAIN_MENU)).isSelected();
        mainPage.takeScreenshot("Checkbox2UnChecked");
        assertFalse(unChecked, "The checkbox should not be checked.");
        logger.info("Checkbox is unchecked.");
    }

    // BUTTON TESTS
    @Test // The button is present and functioning correctly
    public void updateInformationButtonTest() throws IOException {
        logger.info("Checking the button's visibility.");
        assertTrue(profilePage.isUpdateInformationButtonPresent());
        profilePage.clickUpdateInformationButton();
        mainPage.takeScreenshot("UpdateButtonTest");
        assertTrue(profilePage.isSuccessMessagePresent(), "The message was not found.");
        logger.info("The button is visible and clickable.");
    }

    @Test // The button is present and functioning correctly
    public void cancelButtonTest() throws IOException {
        logger.info("Checking the button's presence.");
        assertTrue(profilePage.isCancelButtonPresent());
        profilePage.clickCancelButton();
        String url = webDriver.getCurrentUrl();
        mainPage.takeScreenshot("CancelButton");
        assertEquals(url, PropertiesUtil.getProperty("MAIN_URL"));
        logger.info("The button is visible and is clickable.");
    }
}