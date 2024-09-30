package Pages;

import Consts.Consts;
import Utils.PropertiesUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.Normalizer;
import java.time.Duration;
import java.util.List;

public class ProfilePage extends BasePage {

    public String navigateToProfilePage() {
        webDriver.get(PropertiesUtil.getProperty("MAIN_URL") + "profile");
        return webDriver.getCurrentUrl();
    }

    public void resetToDefaultValues() {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(2));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Consts.EMAIL_FIELD_PROFILE)));
        if (!getEmailFieldValue().equals(PropertiesUtil.getProperty("email"))) {
            clearFields(Consts.EMAIL_FIELD_PROFILE);
            enterEmail(PropertiesUtil.getProperty("email"));
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Consts.FIRST_NAME_FIELD_PROFILE)));
        if (!getFirstNameFieldValue().equals(PropertiesUtil.getProperty("first_name"))) {
            clearFields(Consts.FIRST_NAME_FIELD_PROFILE);
            enterFirstName(PropertiesUtil.getProperty("first_name"));
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Consts.LAST_NAME_FIELD_PROFILE)));
        if (!getLastNameFieldValue().equals(PropertiesUtil.getProperty("last_name"))) {
            clearFields(Consts.LAST_NAME_FIELD_PROFILE);
            enterLastName(PropertiesUtil.getProperty("last_name"));
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Consts.USER_NAME_FIELD)));
        if (!getUsernameFieldValue().equals(PropertiesUtil.getProperty("username"))) {
            clearFields(Consts.USER_NAME_FIELD);
            enterUsername(PropertiesUtil.getProperty("username"));
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Consts.REPLY_TO_FIELD)));
        if (!getReplyToFieldFieldValue().isEmpty()) {
            clearFields(Consts.REPLY_TO_FIELD);
        }
        clickUpdateInformationButton();
    }

    public void resetToDefaultPassword() {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(2));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Consts.OLD_PASSWORD_FIELD)));
        enterOldPassword(PropertiesUtil.getProperty("new_password"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Consts.NEW_PASSWORD_FIELD)));
        setNewPassword(PropertiesUtil.getProperty("pass"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Consts.REPEAT_NEW_PASSWORD_FIELD)));
        setRepeatNewPass(PropertiesUtil.getProperty("pass"));
        clickUpdateInformationButton();
    }

    public void resetDropdownToDefault() {
        clickDropdown();

        webDriver.findElement((By.xpath(Consts.DROPDOWN_OPTIONS)));

        WebElement defaultOption = webDriver.findElement(By.xpath(Consts.DROPDOWN_OPTION_TO_RESET));
        defaultOption.click();
    }

    public String normalizeText(String text) {
        String normalized = text.toLowerCase();
        normalized = Normalizer.normalize(normalized, Normalizer.Form.NFD)
                .replaceAll("\\p{P}", "");
        return normalized.trim();
    }

    // ELEMENT'S PRESENCE
    public boolean isFormVisible() {
        return elementExists(Consts.PROFILE_FORM);
    }

    public boolean areColumnsVisible() {
        return elementExists(Consts.COLUMNS);
    }

    public boolean isEmailFieldPresent() {
        return elementExists(Consts.EMAIL_FIELD_PROFILE);
    }

    public boolean isFirstNameFieldPresent() {
        return elementExists(Consts.FIRST_NAME_FIELD_PROFILE);
    }

    public boolean isReplyToFieldPresent() {
        return elementExists(Consts.REPLY_TO_FIELD);
    }

    public boolean isUsernameFieldPresent() {
        return elementExists(Consts.USER_NAME_FIELD);
    }

    public boolean isLastNameFieldPresent() {
        return elementExists(Consts.LAST_NAME_FIELD_PROFILE);
    }

    public boolean isOldPasswordFieldPresent() {
        return elementExists(Consts.OLD_PASSWORD_FIELD);
    }

    public boolean isNewPasswordFieldPresent() {
        return elementExists(Consts.NEW_PASSWORD_FIELD);
    }

    public boolean isRepeatNewPasswordFieldPresent() {
        return elementExists(Consts.REPEAT_NEW_PASSWORD_FIELD);
    }

    public boolean isDropdownPresent() {
        return elementExists(Consts.DROPDOWN);
    }

    public boolean isSimpleMainMenuCheckboxPresent() {
        return elementExists(Consts.SIMPLE_MAIN_MENU);
    }

    public boolean isCustomMainMenuCheckboxPresent() {
        return elementExists(Consts.CUSTOM_MAIN_MENU);
    }

    public boolean isUpdateInformationButtonPresent() {
        return elementExists(Consts.UPDATE_INFORMATION_BUTTON);
    }

    public boolean isCancelButtonPresent() {
        return elementExists(Consts.CANCEL_BUTTON_PROFILE);
    }

    public boolean isSuccessMessagePresent() {
        return elementExists(Consts.UPDATE_INFO_SUCCESS);
    }

    public boolean isErrorMessagePopUpPresent() {
        return elementExists(Consts.UPDATE_INFO_ERROR);
    }

    // CLICK ON THE ELEMENT
    public void clickUpdateInformationButton() {
        clickElementByXpath(Consts.UPDATE_INFORMATION_BUTTON);
    }

    public void clickCancelButton() {
        clickElementByXpath(Consts.CANCEL_BUTTON_PROFILE);
    }

    public void clickDropdown() {
        clickElementByXpath(Consts.DROPDOWN);
    }

    public void clickSimpleMainMenuCheckbox() {
        clickElementByXpath(Consts.SIMPLE_MAIN_MENU);
    }

    public void clickCustomMainMenuCheckbox() {
        clickElementByXpath(Consts.CUSTOM_MAIN_MENU);
    }

    // GET ELEMENT'S VALUE
    public int countColumnsByXpath() {
        List<WebElement> columns = webDriver.findElements(By.xpath(Consts.COLUMNS));
        return columns.size();
    }

    public List<WebElement> getHeaderTexts() {
        return webDriver.findElements(By.xpath(Consts.HEADER_TEXT));
    }

    public String getEmailFieldValue() {
        WebElement emailElement = webDriver.findElement(By.xpath(Consts.EMAIL_FIELD_PROFILE));
        return emailElement.getAttribute("value");
    }

    public String getFirstNameFieldValue() {
        WebElement firstNameElement = webDriver.findElement(By.xpath(Consts.FIRST_NAME_FIELD_PROFILE));
        return firstNameElement.getAttribute("value");
    }

    public String getReplyToFieldFieldValue() {
        WebElement replyToElement = webDriver.findElement(By.xpath(Consts.REPLY_TO_FIELD));
        return replyToElement.getAttribute("value");
    }

    public String getUsernameFieldValue() {
        WebElement usernameElement = webDriver.findElement(By.xpath(Consts.USER_NAME_FIELD));
        return usernameElement.getAttribute("value");
    }

    public String getLastNameFieldValue() {
        WebElement lastNameElement = webDriver.findElement(By.xpath(Consts.LAST_NAME_FIELD_PROFILE));
        return lastNameElement.getAttribute("value");
    }

    public String getOldPasswordFieldValue() {
        WebElement oldPassElement = webDriver.findElement(By.xpath(Consts.OLD_PASSWORD_FIELD));
        return oldPassElement.getAttribute("value");
    }

    public String getNewPasswordFieldValue() {
        WebElement newPassElement = webDriver.findElement(By.xpath(Consts.NEW_PASSWORD_FIELD));
        return newPassElement.getAttribute("value");
    }

    public String getRepeatNewPasswordFieldValue() {
        WebElement repeatNewPassElement = webDriver.findElement(By.xpath(Consts.REPEAT_NEW_PASSWORD_FIELD));
        return repeatNewPassElement.getAttribute("value");
    }

    public String getOldPassValue() {
        WebElement oldPassElement = webDriver.findElement(By.xpath(Consts.OLD_PASSWORD_FIELD));
        return oldPassElement.getAttribute("value");
    }

    public String getNewPassValue() {
        WebElement newPassElement = webDriver.findElement(By.xpath(Consts.NEW_PASSWORD_FIELD));
        return newPassElement.getAttribute("value");
    }

    public String getRepeatNewPassValue() {
        WebElement repeatNewPassElement = webDriver.findElement(By.xpath(Consts.REPEAT_NEW_PASSWORD_FIELD));
        return repeatNewPassElement.getAttribute("value");
    }

    public boolean isErrorMessageDisplayed(String messageText) {
        try {
            WebElement errorElement = webDriver.findElement(By.xpath("//*[contains(text(), '" + messageText + "')]"));
            return errorElement.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    // SEND TEXT TO ELEMENT
    public void enterEmail(String email) {
        sendTextToElementByXpath(Consts.EMAIL_FIELD_PROFILE, email);
    }

    public void enterUsername(String username) {
        sendTextToElementByXpath(Consts.USER_NAME_FIELD, username);
    }

    public void enterFirstName(String firstName) {
        sendTextToElementByXpath(Consts.FIRST_NAME_FIELD_PROFILE, firstName);
    }

    public void enterLastName(String lastName) {
        sendTextToElementByXpath(Consts.LAST_NAME_FIELD_PROFILE, lastName);
    }

    public void enterOldPassword(String pass) {
        sendTextToElementByXpath(Consts.OLD_PASSWORD_FIELD, pass);
    }

    public void setNewPassword(String newPass) {
        sendTextToElementByXpath(Consts.NEW_PASSWORD_FIELD, newPass);
    }

    public void setRepeatNewPass(String repeatNewPass) {
        sendTextToElementByXpath(Consts.REPEAT_NEW_PASSWORD_FIELD, repeatNewPass);
    }

    public void enterReplyTo(String email) {
        sendTextToElementByXpath(Consts.REPLY_TO_FIELD, email);
    }

    public void clearFields(String... xpaths) {
        for (String xpath : xpaths) {
            try {
                WebElement field = webDriver.findElement(By.xpath(xpath));
                field.clear();
            } catch (NoSuchElementException e) {
                // Log the exception or handle it appropriately
                System.out.println("Element not found for clearing: " + xpath);
            }
        }
    }
}