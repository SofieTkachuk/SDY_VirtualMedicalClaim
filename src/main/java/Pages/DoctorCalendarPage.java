package Pages;

import Consts.Consts;
import Utils.PropertiesUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

public class DoctorCalendarPage extends BasePage{
    public String navigateToDoctorCalendarPage() {
        webDriver.get(PropertiesUtil.getProperty("MAIN_URL") + "appointment");
        return webDriver.getCurrentUrl();
    }

    public void impWait(int sec) {
        try {
            Thread.sleep(1000 * sec);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // ELEMENT'S PRESENCE
    public boolean isMenuHeaderVisible() {
        return elementExists(Consts.MENU_HEADER);
    }

    public boolean areButtonsVisible() {
        return elementExists(Consts.DOCTOR_CALENDAR_ALL_BUTTONS);
    }

    public boolean isDateHeaderVisible() {
        return elementExists(Consts.CALENDARS_DATE_HEADER);
    }

    public boolean isCalendarVisible() {
        return elementExists(Consts.FULL_CALENDAR);
    }

    public boolean isAddDoctorCalendarVisible() {
        return elementExists(Consts.ADD_DOCTOR_CALENDAR_FORM);
    }

    public boolean isDayViewActive() {
        return elementExists(Consts.DAY_ACTIVE_BUTTON);
    }

    public boolean isAppointmentDetailsVisible() {
        return elementExists(Consts.APPOINTMENT_DETAILS);
    }

    public boolean isAppointmentCreated() {
        return elementExists(Consts.CREATED_APPOINTMENT);
    }

    public boolean areDropdownsVisible() {
        return elementExists(Consts.ADD_DOCTOR_CALENDAR_ALL_DROPDOWNS);
    }

    public boolean isDateOfReceiptFieldVisible() {
        return elementExists(Consts.DATE_OF_RECEIPT_FIELD);
    }

    public boolean isStartOfReceiptFieldVisible() {
        return elementExists(Consts.START_OF_RECEIPT_FIELD);
    }

    public boolean isEndOfReceiptFieldVisible() {
        return elementExists(Consts.END_OF_RECEIPT_FIELD);
    }

    public boolean isDateOfReceiptCalendarVisible() {
        return elementExists(Consts.DATE_OF_RECEIPT_CALENDAR);
    }

    public boolean isStartOfReceiptCalendarVisible() {
        return elementExists(Consts.START_OF_RECEIPT_CALENDAR);
    }

    public boolean isEndOfReceiptCalendarVisible() {
        return elementExists(Consts.END_OF_RECEIPT_CALENDAR);
    }

    public boolean isNotesVisible() {
        return elementExists(Consts.NOTES_FIELD_DOC_CALENDAR_FORM);
    }

    public boolean isCloseButtonVisible() {
        return elementExists(Consts.CLOSE_BUTTON_NEW_DOC_CALENDAR);
    }

    public boolean isCreateButtonVisible() {
        return elementExists(Consts.CREATE_BUTTON_NEW_DOC_CALENDAR);
    }

    public boolean isErrorMessagePopUpPresent() {
        return elementExists(Consts.UPDATE_INFO_ERROR);
    }

    public boolean isSuccessMessagePresent() {
        return elementExists(Consts.UPDATE_INFO_SUCCESS);
    }

    public boolean isFieldErrorDisplayed(String xpath) {
        WebElement error = webDriver.findElement(By.xpath(xpath));
        return error.isDisplayed();
    }

    // GET ELEMENT'S VALUE
    public int countButtonsByXpath() {
        List<WebElement> buttons = webDriver.findElements(By.xpath(Consts.DOCTOR_CALENDAR_ALL_BUTTONS));
        return buttons.size();
    }

    public int countDropdownsByXpath() {
        List<WebElement> dropdowns = webDriver.findElements(By.xpath(Consts.ADD_DOCTOR_CALENDAR_ALL_DROPDOWNS));
        return dropdowns.size();
    }

    public String getCalendarsDate() {
        WebElement dateHeader = webDriver.findElement(By.xpath(Consts.CALENDARS_DATE_HEADER));
        return dateHeader.getText().trim();
    }

    public String getHeaderPickerDate() {
        WebElement dateHeader = webDriver.findElement(By.xpath(Consts.DATE_PICKER_HEADER));
        return dateHeader.getText().trim();
    }

    public String getDateOfReceipt() {
        WebElement date = webDriver.findElement(By.xpath(Consts.DATE_OF_RECEIPT_FIELD));
        return date.getAttribute("value");
    }

    public String getStartOfReceipt() {
        WebElement startTime = webDriver.findElement(By.xpath(Consts.START_OF_RECEIPT_FIELD));
        return startTime.getAttribute("value");
    }

    public String getEndOfReceipt() {
        WebElement endTime = webDriver.findElement(By.xpath(Consts.END_OF_RECEIPT_FIELD));
        return endTime.getAttribute("value");
    }

    public String getNotesText() {
        WebElement notesText = webDriver.findElement(By.xpath(Consts.NOTES_FIELD_DOC_CALENDAR_FORM));
        return notesText.getAttribute("value");
    }

    public List<String> getDayTimeTable() {
        List<WebElement> timeElements = webDriver.findElements(By.xpath(Consts.CALENDAR_CONTENT_BY_TIME));
        return timeElements.stream()
                .map(e -> e.getAttribute("data-time"))
                .collect(Collectors.toList());
    }

    public List<String> getWeekDaysTable() {
        List<WebElement> dayElements = webDriver.findElements(By.xpath(Consts.CALENDAR_CONTENT_BY_DAYS));

        return dayElements.stream()
                .map(e -> e.getText().split(" ")[0])
                .collect(Collectors.toList());
    }

    public List<String> getDaysTable() {
        List<WebElement> dayElements = webDriver.findElements(By.xpath(Consts.CALENDAR_CONTENT_BY_DAYS));

        return dayElements.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    // CLICK ON THE ELEMENT
    public void clickPreviousDayButton() {
        clickElementByXpath(Consts.PREV_BUTTON);
    }

    public void clickNextDayButton() {
        clickElementByXpath(Consts.NEXT_BUTTON);
    }

    public void clickTodayButton() {
        clickElementByXpath(Consts.TODAY_BUTTON);
    }

    public void clickAddDoctorCalendarButton() {
        clickElementByXpath(Consts.ADD_DOCTOR_CALENDAR_BUTTON);
    }

    public void clickDayButton() {
        clickElementByXpath(Consts.DAY_BUTTON);
    }

    public void clickWeekButton() {
        clickElementByXpath(Consts.WEEK_BUTTON);
    }

    public void clickMonthButton() {
        clickElementByXpath(Consts.MONTH_BUTTON);
    }

    public void clickOnEmptyCellDay() {
        clickElementByXpath(Consts.EMPTY_CELL_DAY);
    }

    public void clickOnDay(String dayName) {
        String xpath = format(Consts.CALENDAR_CONTENT_BY_DAYS, dayName);
        WebElement dayElement = webDriver.findElement(By.xpath(xpath));
        dayElement.click();
    }

    public void clickOnAppointment() {
        impWait(2);
        clickElementByXpath(Consts.APPOINTMENT_CELL);
    }

    public void clickCloseAppointment() {
        clickElementByXpath(Consts.CLOSE_DETAILS_BUTTON);
    }

    public void clickOnCreatedAppointment() {
        clickElementByXpath(Consts.CREATED_APPOINTMENT);
    }

    public void clickDeleteAppointment() {
        clickElementByXpath(Consts.APPOINTMENT_DELETE_BUTTON);
        clickElementByXpath(Consts.APPOINTMENT_DELETE_CONFIRMATION_BUTTON);
    }

    public void clickDropdownStatus() {
        clickElementByXpath(Consts.STATUS_DROPDOWN);
    }

    public void clickDropdownPlace() {
        clickElementByXpath(Consts.PLACE_DROPDOWN);
    }

    public void clickDropdownDoctor() {
        clickElementByXpath(Consts.DOCTOR_DROPDOWN);
    }

    public void clickDateOfReceiptField() {
        clickElementByXpath(Consts.DATE_OF_RECEIPT_FIELD);
    }

    public void clickStartOfReceiptField() {
        clickElementByXpath(Consts.START_OF_RECEIPT_FIELD);
    }

    public void clickEndOfReceiptField() {
        clickElementByXpath(Consts.END_OF_RECEIPT_FIELD);
    }

    public void clickDatePickerLeft() {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(2));
        WebElement clickLeft = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Consts.LEFT_ARROW_DATE_PICKER)));
        clickLeft.click();
    }

    public void clickDatePickerRight() {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(2));
        WebElement clickRight = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Consts.RIGHT_ARROW_DATE_PICKER)));
        clickRight.click();
    }

    public void clickChangeViewDatePicker() {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(2));
        WebElement clickHeader = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Consts.DATE_PICKER_HEADER)));
        clickHeader.click();
    }

    public void clickCloseButton() {
        clickElementByXpath(Consts.CLOSE_BUTTON_NEW_DOC_CALENDAR);
    }

    public void clickCreateButton() {
        clickElementByXpath(Consts.CREATE_BUTTON_NEW_DOC_CALENDAR);
    }

    public void resetForElementVisibility() {
        clickAddDoctorCalendarButton();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        clickCreateButton();
    }

    public void clickForYearsView() {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(2));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Consts.DATE_PICKER_HEADER)));
        clickChangeViewDatePicker();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Consts.DATE_PICKER_HEADER)));
        clickChangeViewDatePicker();
    }

    // SEND TEXT TO ELEMENT
    public void setDateOfReceipt(String date) {
        sendTextToElementByXpath(Consts.DATE_OF_RECEIPT_FIELD, date);
    }

    public void setStartOfReceipt(String time) {
        sendTextToElementByXpath(Consts.START_OF_RECEIPT_FIELD, time);
    }

    public void setEndOfReceipt(String time) {
        sendTextToElementByXpath(Consts.END_OF_RECEIPT_FIELD, time);
    }

    public void searchDropdownType(String type) {
        sendTextToElementByXpath(Consts.SEARCH_TYPE, type);
    }

    public void searchDropdownStatus(String status) {
        sendTextToElementByXpath(Consts.SEARCH_STATUS, status);
    }

    public void searchDropdownPlace(String place) {
        sendTextToElementByXpath(Consts.SEARCH_PLACE, place);
    }

    public void searchDropdownDoctor(String doctor) {
        sendTextToElementByXpath(Consts.SEARCH_DOCTOR, doctor);
    }

    public void notesFieldInput(String text) {
        sendTextToElementByXpath(Consts.NOTES_FIELD_DOC_CALENDAR_FORM, text);
    }

    public void fillAllFields(String type, String status, String place, String doctor, String date, String startTime, String endTime, String note) {
        searchDropdownType(type);
        searchDropdownStatus(status);
        searchDropdownPlace(place);
        searchDropdownDoctor(doctor);

        setDateOfReceipt(date);
        setStartOfReceipt(startTime);
        setEndOfReceipt(endTime);

        notesFieldInput(note);

        clickCreateButton();
    }
}
