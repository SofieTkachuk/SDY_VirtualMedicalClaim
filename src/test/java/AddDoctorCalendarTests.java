import Consts.Consts;
import Pages.DoctorCalendarPage;
import Pages.LoginPage;
import Pages.MainPage;
import Utils.UseCaseBase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static Pages.BasePage.logger;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AddDoctorCalendarTests extends UseCaseBase {
    private static LoginPage loginPage;
    private static MainPage mainPage;
    private static DoctorCalendarPage doctorCalendarPage;

    @BeforeAll
    public static void classSetup() {
        loginPage = new LoginPage();
        mainPage = new MainPage();
        doctorCalendarPage = new DoctorCalendarPage();

        logger.info("Setting up tests and logging in...");
        loginPage.navigateToLoginPage();
        loginPage.enterUsername(Consts.username);
        loginPage.enterPassword(Consts.pass);
        loginPage.clickLoginButton();
        // Users can go to the Doctor Calendar page after the login procedure
        mainPage.openOtherMenuDropdown();
        mainPage.openDoctorCalendar();
        logger.info("Login successful, navigating to doctor calendar page.");
    }

    @AfterEach
    public void navigateToCalendarPage() {
        doctorCalendarPage.navigateToDoctorCalendarPage();
    }

    @Test // Users can go to the Doctor Calendar Page after the login procedure
    public void doctorCalendarPageLoadTest() {
        logger.info("Getting current URL.");
        String pageLoaded = webDriver.getCurrentUrl();
        String pageExpected = doctorCalendarPage.navigateToDoctorCalendarPage();
        assertEquals(pageExpected, pageLoaded, "Expected the Doctor Calendar Page URL to be " + pageExpected + " but got " + pageLoaded);
        logger.info("The Doctor Calendar page is loaded.");
    }

    @Test // The doctor calendar page content is loaded
    public void doctorCalendarPageLoadContentTest() {
        logger.info("Checking if the content is visible.");
        assertTrue(doctorCalendarPage.isMenuHeaderVisible());
        logger.info("The menu header is visible.");
    }

    // CALENDAR TESTS
    @Test // Validate that the calendar is visible on a page
    public void calendarExistenceTest() {
        logger.info("Checking if the calendar is visible.");
        assertTrue(doctorCalendarPage.isCalendarVisible());
        logger.info("The calendar is visible.");
    }

    @Test // Validate that the by default the today's calendar view is loaded
    public void defaultCalendarViewTest(){
        WebElement todayButton = webDriver.findElement(By.xpath(Consts.TODAY_BUTTON));
        assertNotNull(todayButton.getAttribute("disabled"));
        logger.info("The today's calendar view is loaded by default");
    }

    @Test // Verify the calendar date is displayed and matches the current date
    public void dateOnAHeaderTest() throws IOException {
        logger.info("Checking if the date header visible.");
        assertTrue(doctorCalendarPage.isDateHeaderVisible(), "Date header should be visible.");
        logger.info("The date header is visible.");

        String dateOnAHeader = doctorCalendarPage.getCalendarsDate().trim();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH);
        LocalDate currentDate = LocalDate.now();
        LocalDate headerDate;
        headerDate = LocalDate.parse(dateOnAHeader, formatter);
        assertEquals(currentDate, headerDate, "Date on a header is different from a current date.");
        mainPage.takeScreenshot("DateHeader");
        logger.info("The date header has been verified and screenshot taken.");
    }

    // DOCTOR CALENDAR BUTTONS TESTS
    @Test // 12 buttons are exist
    public void buttonsExistenceTest() {
        logger.info("Checking if all the 12 buttons are visible.");
        assertTrue(doctorCalendarPage.areButtonsVisible());
        int buttonCount = doctorCalendarPage.countButtonsByXpath();
        assertEquals(12, buttonCount, "The number of buttons should be exactly 12.");
        logger.info("All 12 buttons are visible.");
    }

    @Test // Validate functionality of the button
    public void previousDayButtonFunctionalityTest() throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH);
        LocalDate currentDate = LocalDate.now();

        int numberOfClicks = 10;
        for (int i = 1; i <= numberOfClicks; i++) {
            logger.info("Clicking on the previous day button.");
            doctorCalendarPage.clickPreviousDayButton();
            logger.info("Clicked the button, click number: " + i);

            String previousDateOnHeader = doctorCalendarPage.getCalendarsDate();
            LocalDate expectedPreviousDate = currentDate.minusDays(i);
            LocalDate actualPreviousDate = LocalDate.parse(previousDateOnHeader, formatter);

            assertEquals(expectedPreviousDate, actualPreviousDate,
                    "The calendar did not navigate to the correct previous day on click " + i);
            mainPage.takeScreenshot("PreviousDayButtonTest_Click" + i);
            logger.info("Successfully navigated to the correct previous day after " + i + " clicks.");
        }
    }

    @Test // Validate functionality of the button
    public void nextDayButtonFunctionalityTest() throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH);
        LocalDate currentDate = LocalDate.now();

        int numberOfClicks = 10;
        for (int i = 1; i <= numberOfClicks; i++) {
            logger.info("Clicking on the next day button.");
            doctorCalendarPage.clickNextDayButton();
            logger.info("Clicked the next day button, click number: " + i);

            String nextDateOnHeader = doctorCalendarPage.getCalendarsDate();
            LocalDate expectedNextDate = currentDate.plusDays(i); // Expected date after i clicks
            LocalDate actualNextDate = LocalDate.parse(nextDateOnHeader, formatter);

            assertEquals(expectedNextDate, actualNextDate,
                    "The calendar did not navigate to the correct next day on click " + i);
            mainPage.takeScreenshot("NextDayButtonTest_Click" + i);
            logger.info("Successfully navigated to the correct next day after " + i + " clicks.");
        }
    }

    @Test // Validate functionality of the button
    public void todayButtonFunctionalityTest() throws IOException {
        logger.info("Clicking on today button.");
        doctorCalendarPage.clickTodayButton();

        String dateOnAHeader = doctorCalendarPage.getCalendarsDate().trim();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH);
        LocalDate currentDate = LocalDate.now();
        LocalDate headerDate;
        headerDate = LocalDate.parse(dateOnAHeader, formatter);
        assertEquals(currentDate, headerDate, "Date on a header is different from a current date.");
        mainPage.takeScreenshot("ClickedOnToday");
        logger.info("Successfully navigated to the current day.");
    }

    @Test // Validate functionality of the button, pop-up window is loaded
    public void addDocCalendarButtonFunctionalityTest() throws IOException {
        logger.info("Clicking on the button.");
        doctorCalendarPage.clickAddDoctorCalendarButton();
        logger.info("Checking if the pop-up window was loaded.");
        mainPage.takeScreenshot("PopUpWindow");
        assertTrue(doctorCalendarPage.isAddDoctorCalendarVisible(), "The pop-up window was not loaded.");
        logger.info("The pop-up window was loaded.");
    }

    @Test // Validate functionality of the button: if the schedule is visible for 12am to 11:30pm
    public void dayButtonFunctionalityTest() throws IOException {
        logger.info("Clicking on the day button.");
        doctorCalendarPage.clickWeekButton();
        doctorCalendarPage.clickDayButton();
        mainPage.takeScreenshot("DayButton");

        List<String> times = doctorCalendarPage.getDayTimeTable();
        logger.info("Checking if the schedule is visible for 12am, 2pm, and 11:30pm.");
        assertTrue(times.contains("00:00:00"), "The schedule for 12am was not visible.");
        assertTrue(times.contains("14:00:00"), "The schedule for 2pm was not visible.");
        assertTrue(times.contains("23:30:00"), "The schedule for 11:30pm was not visible.");

        logger.info("Schedule for 12am to 11:30pm is correctly displayed.");
    }

    @Test // The Doctor Calendar Details pop-up page should be opened after pressing on appointment and closed after pressing the “Close” button
    public void popUpAppointmentDetailsDayTest() throws IOException {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));

        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(Consts.APPOINTMENT_CELL)));
            logger.info("Clicking on the existing appointment.");
            doctorCalendarPage.clickOnAppointment();
        } catch (TimeoutException e) {
            logger.info("Appointment not found. Creating one.");
            doctorCalendarPage.clickAddDoctorCalendarButton();
            doctorCalendarPage.fillAllFields("Medical", "Scheduled", "123 AVE, Brooklyn",
                    "GREGORY HOUSE", "", "7:10 AM", "8:20 AM", "");
            doctorCalendarPage.setDateOfReceipt(LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
            doctorCalendarPage.clickCreateButton();

            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(Consts.APPOINTMENT_CELL)));
            logger.info("Clicking on the created appointment.");
            doctorCalendarPage.clickOnAppointment();
        }

        mainPage.takeScreenshot("PopUpAppDay");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Consts.APPOINTMENT_DETAILS)));
        assertTrue(doctorCalendarPage.isAppointmentDetailsVisible(), "Pop-up not loaded.");

        doctorCalendarPage.clickCloseAppointment();
        mainPage.takeScreenshot("PopUpAppDayClosed");
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(Consts.APPOINTMENT_DETAILS)));
        assertFalse(doctorCalendarPage.isAppointmentDetailsVisible(), "Pop-up still visible.");
    }

    @Test // Add Doctor Calendar form opens with a selected date and a time after clicking on a free time spot on the calendar:
    public void addDoctorCalendarDayTest() throws IOException {
        logger.info("Clicking on the free spot.");
        doctorCalendarPage.clickOnEmptyCellDay();
        mainPage.takeScreenshot("AddDoctorCalendarDay");

        assertTrue(doctorCalendarPage.isAddDoctorCalendarVisible(), "The pop-up window was not loaded.");
        logger.info("The New Doctor Calendar Form is loaded.");

        logger.info("Validating that date and start time are not null.");

        doctorCalendarPage.clickCreateButton();
        String dateOfReceipt = doctorCalendarPage.getDateOfReceipt(),
                startOfReceipt = doctorCalendarPage.getStartOfReceipt();
        assertNotNull(dateOfReceipt, "Date of receipt should not be null.");
        assertNotNull(startOfReceipt, "Start of receipt should not be null.");

        logger.info("The Start Of Receipt is " + dateOfReceipt + ", and the Start Of Receipt is " + startOfReceipt);
    }

    @Test // Validate functionality of the button: the schedule is visible for the whole week from Sun to Sat, all day and from 12am to 11pm
    public void weekButtonFunctionalityTest() throws IOException {
        logger.info("Clicking on the week button.");
        doctorCalendarPage.clickWeekButton();
        mainPage.takeScreenshot("WeekButton");

        List<String> days = doctorCalendarPage.getWeekDaysTable();
        logger.info("Days found in the calendar: " + days);

        assertTrue(days.contains("Sun"), "The schedule for Sunday was not visible.");
        assertTrue(days.contains("Thu"), "The schedule for Thursday was not visible.");
        assertTrue(days.contains("Sat"), "The schedule for Saturday was not visible.");

        List<String> times = doctorCalendarPage.getDayTimeTable();

        assertTrue(times.contains("14:00:00"), "The schedule for 2pm was not visible.");

        logger.info("Schedule is correctly displayed.");
    }

    @Test // The Doctor Calendar Details pop-up page should be opened after pressing on an appointment and closed after pressing the “Close” button
    public void popUpAppointmentDetailsWeekTest() throws IOException {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));

        doctorCalendarPage.clickWeekButton();
        logger.info("Clicking on the appointment.");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Consts.APPOINTMENT_CELL)));
        doctorCalendarPage.clickOnAppointment();
        mainPage.takeScreenshot("PopUpAppWeek");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Consts.APPOINTMENT_DETAILS)));

        assertTrue(doctorCalendarPage.isAppointmentDetailsVisible(), "The pop-up window was not loaded.");
        logger.info("The appointment details pop-up is loaded.");

        doctorCalendarPage.clickCloseAppointment();
        mainPage.takeScreenshot("PopUpAppWeekClosed");

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(Consts.APPOINTMENT_DETAILS)));
        assertFalse(doctorCalendarPage.isAppointmentDetailsVisible(), "The pop-up window is still visible after closing.");

        logger.info("The appointment details pop-up is successfully closed.");
    }

    @Test // Add Doctor Calendar form opens with a selected date and a time after clicking on a free time spot on the calendar:
    public void addDoctorCalendarWeekTest() throws IOException {
        doctorCalendarPage.clickWeekButton();
        logger.info("Clicking on the free spot.");
        doctorCalendarPage.clickOnEmptyCellDay();
        mainPage.takeScreenshot("AddDoctorCalendarWeek");

        assertTrue(doctorCalendarPage.isAddDoctorCalendarVisible(), "The pop-up window was not loaded.");
        logger.info("The New Doctor Calendar Form is loaded.");

        logger.info("Validating that date and start time are not null.");

        doctorCalendarPage.clickCreateButton();
        String dateOfReceipt = doctorCalendarPage.getDateOfReceipt(),
                startOfReceipt = doctorCalendarPage.getStartOfReceipt();
        assertNotNull(dateOfReceipt, "Date of receipt should not be null.");
        assertNotNull(startOfReceipt, "Start of receipt should not be null.");

        logger.info("The Start Of Receipt is " + dateOfReceipt + ", and the Start Of Receipt is " + startOfReceipt);
    }

    @Test // Validate if a user can go to a day view by clicking on a day of the week
    public void navigateToDayViewFromWeekTest() throws IOException {
        doctorCalendarPage.clickWeekButton();
        logger.info("Clicking on a specific day in the week view.");
        doctorCalendarPage.clickOnDay("Tue");

        mainPage.takeScreenshot("DayView");

        assertTrue(doctorCalendarPage.isDayViewActive(), "The day view did not load.");
        logger.info("Successfully navigated to the day view for the selected day.");
    }

    @Test // Validate functionality of the button: displayed a schedule for the chosen month
    public void monthButtonFunctionalityTest() throws IOException {
        logger.info("Clicking on the week button.");
        doctorCalendarPage.clickMonthButton();
        mainPage.takeScreenshot("MonthButton");

        List<String> days = doctorCalendarPage.getDaysTable();
        logger.info("Days found in the calendar: " + days);

        assertTrue(days.contains("1"), "Day 1 is not visible in the month view.");
        assertTrue(days.contains("14"), "Day 10 is not visible in the month view.");
        assertTrue(days.contains("28"), "Day 28 is not visible in the month view.");

        logger.info("Schedule for a month is correctly displayed.");
    }

    @Test // Validate that the pop-up window is loaded after pressing on a day's cell
    public void addDoctorCalendarMonthTest() throws IOException {
        doctorCalendarPage.clickMonthButton();
        logger.info("Clicking on the day cell.");
        doctorCalendarPage.clickOnEmptyCellDay();
        mainPage.takeScreenshot("AddDoctorCalendarMonth");

        assertTrue(doctorCalendarPage.isAddDoctorCalendarVisible(), "The pop-up window was not loaded.");
        logger.info("The pop-up window is loaded.");

        logger.info("Validating that date is not null.");

        doctorCalendarPage.clickCreateButton();
        String dateOfReceipt = doctorCalendarPage.getDateOfReceipt();
        assertNotNull(dateOfReceipt, "Date of receipt should not be null.");

        logger.info("The Start Of Receipt is " + dateOfReceipt);
    }

    @Test // The Doctor Calendar Details pop-up page should be opened after pressing on appointment and closed after pressing the “Close” button
    public void popUpAppointmentDetailsMonthTest() throws IOException {
        doctorCalendarPage.clickMonthButton();
        logger.info("Month view button clicked.");
        doctorCalendarPage.impWait(2);
        logger.info("Clicking on the appointment.");
        doctorCalendarPage.clickOnAppointment();
        mainPage.takeScreenshot("PopUpAppMonth");

        assertTrue(doctorCalendarPage.isAppointmentDetailsVisible(), "The pop-up window was not loaded.");
        logger.info("The appointment details pop-up is loaded.");

        doctorCalendarPage.clickCloseAppointment();
        mainPage.takeScreenshot("PopUpAppWeekClosed");

        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(Consts.APPOINTMENT_DETAILS)));

        boolean isVisibleAfterClose = doctorCalendarPage.isAppointmentDetailsVisible();
        assertFalse(isVisibleAfterClose, "The appointment details pop-up did not close as expected.");

        logger.info("The appointment details pop-up is successfully closed.");
    }

    @Test // Validate if a user can go to a day view by clicking on a date
    public void navigateToDayViewFromMonthTest() throws IOException {
        doctorCalendarPage.clickMonthButton();
        logger.info("Clicking on a specific day in the month view.");
        doctorCalendarPage.clickOnDay("22");

        mainPage.takeScreenshot("DayViewFromMonth");

        assertTrue(doctorCalendarPage.isDayViewActive(), "The day view did not load.");
        logger.info("Successfully navigated to the day view for the selected date.");
    }

    // NEW DOCTOR CALENDAR FORM TESTS
    @Test // Validate that filed 'Notes' is visible
    public void noteFieldExistenceTest(){
        doctorCalendarPage.clickAddDoctorCalendarButton();
        logger.info("Checking if the 'Notes' field visible.");
        assertTrue(doctorCalendarPage.isNotesVisible(), "The field should be present.");
        logger.info("The 'Notes' field is visible.");
    }

    @Test // Validate that the user can enter a text into the 'Notes' field, and it’s visible
    public void notesFieldInputTest() throws IOException {
        String textNote = "Hello!";

        doctorCalendarPage.resetForElementVisibility();

        logger.info("Sending text to the 'Notes' field.");

        doctorCalendarPage.notesFieldInput(textNote);
        mainPage.takeScreenshot("NotesInput");
        doctorCalendarPage.clickCreateButton();
        assertEquals(textNote, doctorCalendarPage.getNotesText(), "The entered text is not as expected.");
        logger.info("Text entered to the field successfully.");
    }

    @Test // Validate that the fields are mandatory
    public void mandatoryFieldsTest() {
        doctorCalendarPage.clickAddDoctorCalendarButton();

        Map<String, Boolean> mandatoryFields = new LinkedHashMap<>();

        mandatoryFields.put("Type", webDriver.findElement(By.xpath(Consts.TYPE_FIELD)).getAttribute("required") != null);
        mandatoryFields.put("Status", webDriver.findElement(By.xpath(Consts.STATUS_FIELD)).getAttribute("required") != null);
        mandatoryFields.put("Place Of Service", webDriver.findElement(By.xpath(Consts.PLACE_OF_SERVICE_FIELD)).getAttribute("required") != null);
        mandatoryFields.put("Doctor", webDriver.findElement(By.xpath(Consts.DOCTOR_FIELD)).getAttribute("required") != null);
        mandatoryFields.put("Date", webDriver.findElement(By.xpath(Consts.DATE_OF_RECEIPT_FIELD)).getAttribute("required") != null);
        mandatoryFields.put("Start Time", webDriver.findElement(By.xpath(Consts.START_OF_RECEIPT_FIELD)).getAttribute("required") != null);
        mandatoryFields.put("End Time", webDriver.findElement(By.xpath(Consts.END_APPOINTMENT_TIME_FIELD)).getAttribute("required") != null);

        mandatoryFields.forEach((field, isMandatory) -> {
            String result = isMandatory ? "passed" : "failed";
            logger.info(field + " field is marked as mandatory: " + result);
        });

        assertTrue(mandatoryFields.values().stream().allMatch(Boolean::booleanValue), "One or more fields are not marked as mandatory.");
    }

    @Test // Validate that the appointment can't be created with empty mandatory fields
    public void mandatoryFieldsTestType() throws IOException {
        doctorCalendarPage.clickAddDoctorCalendarButton();

        logger.info("Filling out all other fields.");
        doctorCalendarPage.fillAllFields("","Scheduled", "123 AVE, Brooklyn NY, 11224", "GREGORY HOUSE (ACUPUNCTURE)", "09/01/2024", "5:10 AM", "9:20 AM", "Hello!");
        mainPage.takeScreenshot("MandatoryFieldType");
        logger.info("Checking for an error message.");
        boolean isErrorDisplayed = doctorCalendarPage.isErrorMessagePopUpPresent();
        boolean isTypeErrorDisplayed = doctorCalendarPage.isFieldErrorDisplayed(Consts.TYPE_MANDATORY_MESSAGE);
        assertTrue(isErrorDisplayed && isTypeErrorDisplayed, "The error message for the Type field did not appear as expected.");

        logger.info("The system does not allow creating an appointment with an empty type field.");
        }

    @Test // Validate that the appointment can't be created with empty mandatory fields
    public void mandatoryFieldsTestStatus() throws IOException {
        doctorCalendarPage.clickAddDoctorCalendarButton();

        logger.info("Filling out all other fields.");
        doctorCalendarPage.fillAllFields("Medical","", "123 AVE, Brooklyn NY, 11224", "GREGORY HOUSE (ACUPUNCTURE)", "09/01/2024", "5:10 AM", "9:20 AM", "Hello!");
        mainPage.takeScreenshot("MandatoryFieldStatus");
        logger.info("Checking for an error message.");
        boolean isErrorDisplayed = doctorCalendarPage.isErrorMessagePopUpPresent();
        boolean isStatusErrorDisplayed = doctorCalendarPage.isFieldErrorDisplayed(Consts.STATUS_MANDATORY_MESSAGE);
        assertTrue(isErrorDisplayed && isStatusErrorDisplayed, "The error message for the Status field did not appear as expected.");

        logger.info("The system does not allow creating an appointment with an empty status field.");
    }

    @Test // Validate that the appointment can't be created with empty mandatory fields
    public void mandatoryFieldsTestPlaceOfReceipt() throws IOException {
        doctorCalendarPage.clickAddDoctorCalendarButton();

        logger.info("Filling out all other fields.");
        doctorCalendarPage.fillAllFields("Medical","Scheduled", "", "GREGORY HOUSE (ACUPUNCTURE)", "09/01/2024", "5:10 AM", "9:20 AM", "Hello!");
        mainPage.takeScreenshot("MandatoryFieldPlace");
        logger.info("Checking for an error message.");
        boolean isErrorDisplayed = doctorCalendarPage.isErrorMessagePopUpPresent();
        boolean isPlaceErrorDisplayed = doctorCalendarPage.isFieldErrorDisplayed(Consts.PLACE_OF_SERVICE_MANDATORY_MESSAGE);
        assertTrue(isErrorDisplayed && isPlaceErrorDisplayed, "The error message for the Place Of Receipt field did not appear as expected.");

        logger.info("The system does not allow creating an appointment with an empty Place Of Receipt field.");
    }

    @Test // Validate that the appointment can't be created with empty mandatory fields
    public void mandatoryFieldsTestDoctor() throws IOException {
        doctorCalendarPage.clickAddDoctorCalendarButton();

        logger.info("Filling out all other fields.");
        doctorCalendarPage.fillAllFields("Medical","Scheduled", "123 AVE, Brooklyn NY, 11224", "", "09/01/2024", "5:10 AM", "9:20 AM", "Hello!");
        mainPage.takeScreenshot("MandatoryFieldDoctor");
        logger.info("Checking for an error message.");
        boolean isErrorDisplayed = doctorCalendarPage.isErrorMessagePopUpPresent();
        boolean isDoctorErrorDisplayed = doctorCalendarPage.isFieldErrorDisplayed(Consts.DOCTOR_MANDATORY_MESSAGE);
        assertTrue(isErrorDisplayed && isDoctorErrorDisplayed, "The error message for the Doctor field did not appear as expected.");

        logger.info("The system does not allow creating an appointment with an empty doctor field.");
    }

    @Test // Validate that the appointment can't be created with empty mandatory fields
    public void mandatoryFieldsTestDate() throws IOException {
        doctorCalendarPage.clickAddDoctorCalendarButton();

        logger.info("Filling out all other fields.");
        doctorCalendarPage.fillAllFields("Medical","Scheduled", "123 AVE, Brooklyn NY, 11224", "GREGORY HOUSE (ACUPUNCTURE)", "", "5:10 AM", "9:20 AM", "Hello!");
        mainPage.takeScreenshot("MandatoryFieldDate");
        logger.info("Checking for an error message.");
        boolean isErrorDisplayed = doctorCalendarPage.isErrorMessagePopUpPresent();
        boolean isDateErrorDisplayed = doctorCalendarPage.isFieldErrorDisplayed(Consts.DATE_MANDATORY_MESSAGE);
        assertTrue(isErrorDisplayed && isDateErrorDisplayed, "The error message for the Date Of Receipt field did not appear as expected.");

        logger.info("The system does not allow creating an appointment with an empty date of receipt field.");
    }

    @Test // Validate that the appointment can't be created with empty mandatory fields
    public void mandatoryFieldsTestStartTime() throws IOException {
        doctorCalendarPage.clickAddDoctorCalendarButton();

        logger.info("Filling out all other fields.");
        doctorCalendarPage.fillAllFields("Medical","Scheduled", "123 AVE, Brooklyn NY, 11224", "GREGORY HOUSE (ACUPUNCTURE)", "09/01/2024", "", "9:20 AM", "Hello!");
        mainPage.takeScreenshot("MandatoryFieldStartTime");
        logger.info("Checking for an error message.");
        boolean isErrorDisplayed = doctorCalendarPage.isErrorMessagePopUpPresent();
        boolean isStartTimeErrorDisplayed = doctorCalendarPage.isFieldErrorDisplayed(Consts.TIME_MANDATORY_MESSAGE);
        assertTrue(isErrorDisplayed && isStartTimeErrorDisplayed, "The error message for the Start Of Receipt field did not appear as expected.");

        logger.info("The system does not allow creating an appointment with an empty start of receipt field.");
    }

    @Test // Validate that the appointment can't be created with empty mandatory fields
    public void mandatoryFieldsTestEndTime() throws IOException {
        doctorCalendarPage.clickAddDoctorCalendarButton();

        logger.info("Filling out all other fields.");
        doctorCalendarPage.fillAllFields("Medical","Scheduled", "123 AVE, Brooklyn NY, 11224", "GREGORY HOUSE (ACUPUNCTURE)", "09/01/2024", "5:10 AM", "", "Hello!");
        mainPage.takeScreenshot("MandatoryFieldEndTime");
        logger.info("Checking for an error message.");
        boolean isErrorDisplayed = doctorCalendarPage.isErrorMessagePopUpPresent();
        boolean isEndTimeErrorDisplayed = doctorCalendarPage.isFieldErrorDisplayed(Consts.TIME_MANDATORY_MESSAGE);
        assertTrue(isErrorDisplayed && isEndTimeErrorDisplayed, "The error message for the End Of Receipt field did not appear as expected.");

        logger.info("The system does not allow creating an appointment with an empty end of receipt field.");
    }

    @Test // Validate that the fields are optional
    public void optionalFieldsTest(){
        doctorCalendarPage.clickAddDoctorCalendarButton();

        WebElement dateField = webDriver.findElement(By.xpath(Consts.DATE_OF_RECEIPT_FIELD));
        WebElement startTimeField = webDriver.findElement(By.xpath(Consts.START_OF_RECEIPT_FIELD));
        WebElement endTimeField = webDriver.findElement(By.xpath(Consts.END_OF_RECEIPT_FIELD));
        WebElement notesField = webDriver.findElement(By.xpath(Consts.NOTES_FIELD_DOC_CALENDAR_FORM));

        assertNull(dateField.getAttribute("required"), "Date Of Receipt field is marked as mandatory.");
        assertNull(startTimeField.getAttribute("required"), "Start Of Receipt field is marked as mandatory.");
        assertNull(endTimeField.getAttribute("required"), "End Of Receipt field is marked as mandatory.");
        assertNull(notesField.getAttribute("required"), "Notes field is marked as mandatory.");
        logger.info("The fields are optional.");
    }

    @Test // Validate that the appointment can be created with empty optional fields
    public void optionalFieldsTestNotes() throws IOException {
        doctorCalendarPage.clickAddDoctorCalendarButton();

        logger.info("Filling out all other fields.");
        doctorCalendarPage.fillAllFields("Medical","Scheduled", "123 AVE, Brooklyn NY, 11224", "GREGORY HOUSE (ACUPUNCTURE)", "09/01/2024", "5:10 AM", "9:20 AM", "");
        mainPage.takeScreenshot("OptionalFieldNotes");
        assertTrue(doctorCalendarPage.isSuccessMessagePresent(), "The success message did not appear as expected.");
        logger.info("The new  appointment has been created with an empty notes field.");
    }

    // DROPDOWNS TESTS
    @Test // 4 dropdowns are exist
    public void dropdownsExistenceTest() {
        doctorCalendarPage.clickAddDoctorCalendarButton();
        logger.info("Checking if all the 4 dropdowns are visible.");
        assertTrue(doctorCalendarPage.areDropdownsVisible());
        int dropdownCount = doctorCalendarPage.countDropdownsByXpath();
        assertEquals(4, dropdownCount, "The number of dropdowns should be exactly 4.");
        logger.info("All 4 dropdowns are visible.");
    }

    @ParameterizedTest // Validate dropdown selection
    @ValueSource(strings = {"Chiro", "EMG", "Medical", "Ortho", "Ortho Procedures", "Pain Consultation/Visit", "Pain Procedures", "Physical Therapy"})
    public void validateDropdownTypeFunctionalTest(String expectedDropdownOption) throws IOException {
        doctorCalendarPage.clickAddDoctorCalendarButton();

        logger.info("Checking that type options are visible to select.");
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
        //waitForElementVisible(Duration.ofSeconds(5), By.xpath(Consts.DROPDOWN_OPTIONS));
        doctorCalendarPage.impWait(2);
        logger.info("Checking that the option can be selected.");
        WebElement optionToSelect = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Consts.DROPDOWN_OPTION_TO_SELECT + expectedDropdownOption + "']")));
        optionToSelect.click();

        WebElement selectedItem = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Consts.TYPE_DROPDOWN_VALUE)));
        String selectedValue = selectedItem.getText().trim();
        mainPage.takeScreenshot("SelectedValueTypeDropdownTest");
        assertEquals(expectedDropdownOption, selectedValue, "Dropdown option not selected correctly.");
        logger.info("Selected option is visible.");
    }

    @Test // Validate dropdown input and search
    public void dropdownTypeSearchTest() throws IOException {
        doctorCalendarPage.clickAddDoctorCalendarButton();
        logger.info("Checking that the data can be typed.");
        doctorCalendarPage.searchDropdownType("Medical");

        logger.info("Checking that the option can be selected.");

        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(2));
        WebElement optionToSelect = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Consts.DROPDOWN_SEARCHED_OPTION + "Medical']")));
        optionToSelect.click();

        WebElement selectedItem = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Consts.TYPE_DROPDOWN_VALUE)));
        String selectedValue = selectedItem.getText().trim();
        mainPage.takeScreenshot("SearchTypeDropdownTest");
        assertEquals("Medical", selectedValue, "Searched option not visible.");
        logger.info("Searched option is visible and selected.");
    }

    @ParameterizedTest // Validate dropdown selection
    @ValueSource(strings = {"HAZEM", "Iqbal", "12345", "Julia", "12Mark*", "Pending", "Test#1", "TestSofie", "Test2", "Test3"})
    public void validateDropdownStatusFunctionalTest(String expectedDropdownOption) throws IOException {
        doctorCalendarPage.clickAddDoctorCalendarButton();
        logger.info("Clicking on dropdown 'Status'.");
        doctorCalendarPage.clickDropdownStatus();

        logger.info("Checking that status options are visible to select.");
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(2));
        waitForElementVisible(Duration.ofSeconds(2), By.xpath(Consts.DROPDOWN_OPTIONS));
        logger.info("Checking that the status option can be selected.");
        WebElement optionToSelect = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Consts.DROPDOWN_OPTION_TO_SELECT + expectedDropdownOption + "']")));
        optionToSelect.click();

        WebElement selectedItem = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Consts.STATUS_DROPDOWN_VALUE)));
        String selectedValue = selectedItem.getText().trim();
        mainPage.takeScreenshot("SelectedValueStatusDropdownTest");
        assertEquals(expectedDropdownOption, selectedValue, "Dropdown option not selected correctly.");
        logger.info("Selected status option is visible.");
    }

    @Test // Validate dropdown input and search
    public void dropdownStatusSearchTest() throws IOException {
        doctorCalendarPage.resetForElementVisibility();
        logger.info("Checking that the data can be typed.");
        doctorCalendarPage.searchDropdownStatus("Pending");

        logger.info("Checking that the option can be selected.");

        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(2));
        WebElement optionToSelect = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Consts.DROPDOWN_SEARCHED_OPTION + "Pending']")));
        optionToSelect.click();

        WebElement selectedItem = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Consts.STATUS_DROPDOWN_VALUE)));
        String selectedValue = selectedItem.getText().trim();
        mainPage.takeScreenshot("SearchStatusDropdownTest");
        assertEquals("Pending", selectedValue, "Searched option not visible.");
        logger.info("Searched option is visible and selected.");
    }

    @ParameterizedTest // Validate dropdown selection
    @ValueSource(strings = {"123 AVE, Brooklyn NY, 11224"}) // ParameterizedTest for future addresses that will be added later
    public void validateDropdownPlaceFunctionalTest(String expectedDropdownOption) throws IOException {
        doctorCalendarPage.resetForElementVisibility();
        logger.info("Clicking on dropdown 'Place of Service'.");
        doctorCalendarPage.clickDropdownPlace();

        logger.info("Checking that location options are visible to select.");
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(2));
        waitForElementVisible(Duration.ofSeconds(2), By.xpath(Consts.DROPDOWN_OPTIONS));
        logger.info("Checking that the location option can be selected.");
        WebElement optionToSelect = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Consts.DROPDOWN_OPTION_TO_SELECT + expectedDropdownOption + "']")));
        optionToSelect.click();

        WebElement selectedItem = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Consts.PLACE_DROPDOWN_VALUE)));
        String selectedValue = selectedItem.getText().trim();
        mainPage.takeScreenshot("SelectedValuePlaceDropdownTest");
        assertEquals(expectedDropdownOption, selectedValue, "Dropdown option not selected correctly.");
        logger.info("Selected place option is visible.");
    }

    @Test // Validate dropdown input and search
    public void dropdownPlaceSearchTest() throws IOException {
        doctorCalendarPage.resetForElementVisibility();
        logger.info("Checking that the data can be typed.");
        doctorCalendarPage.searchDropdownPlace("123 AVE, Brooklyn NY, 11224");

        logger.info("Checking that the option can be selected.");

        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(2));
        WebElement optionToSelect = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Consts.DROPDOWN_SEARCHED_OPTION + "123 AVE, Brooklyn NY, 11224']")));
        optionToSelect.click();

        WebElement selectedItem = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Consts.PLACE_DROPDOWN_VALUE)));
        String selectedValue = selectedItem.getText().trim();
        mainPage.takeScreenshot("SearchPlaceDropdownTest");
        assertEquals("123 AVE, Brooklyn NY, 11224", selectedValue, "Searched option not visible.");
        logger.info("Searched option is visible and selected.");
    }

    @ParameterizedTest // Validate dropdown selection
    @ValueSource(strings = {"GREGORY HOUSE (ACUPUNCTURE)"}) // ParameterizedTest for future doctors that will be added later
    public void validateDropdownDoctorFunctionalTest(String expectedDropdownOption) throws IOException {
        doctorCalendarPage.resetForElementVisibility();
        logger.info("Clicking on dropdown 'Doctor'.");
        doctorCalendarPage.clickDropdownDoctor();

        logger.info("Checking that doctor options are visible to select.");
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
        waitForElementVisible(Duration.ofSeconds(5), By.xpath(Consts.DROPDOWN_OPTIONS));
        logger.info("Checking that the doctor option can be selected.");
        WebElement optionToSelect = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Consts.DROPDOWN_OPTION_TO_SELECT + expectedDropdownOption + "']")));
        optionToSelect.click();

        WebElement selectedItem = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Consts.DOCTOR_DROPDOWN_VALUE)));
        String selectedValue = selectedItem.getText().trim();
        mainPage.takeScreenshot("SelectedValueDoctorDropdownTest");
        assertEquals(expectedDropdownOption, selectedValue, "Dropdown option not selected correctly.");
        logger.info("Selected doctor option is visible.");
    }

    @Test // Validate dropdown input and search
    public void dropdownDoctorSearchTest() throws IOException {
        doctorCalendarPage.resetForElementVisibility();
        logger.info("Checking that the data can be typed.");
        doctorCalendarPage.searchDropdownDoctor("GREGORY HOUSE (ACUPUNCTURE)");

        logger.info("Checking that the option can be selected.");
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(2));
        WebElement optionToSelect = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Consts.DROPDOWN_SEARCHED_OPTION + "GREGORY HOUSE (ACUPUNCTURE)']")));
        optionToSelect.click();

        WebElement selectedItem = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Consts.DOCTOR_DROPDOWN_VALUE)));
        String selectedValue = selectedItem.getText().trim();
        mainPage.takeScreenshot("SearchDoctorDropdownTest");
        assertEquals("GREGORY HOUSE (ACUPUNCTURE)", selectedValue, "Searched option not visible.");
        logger.info("Searched option is visible and selected.");
    }

    // DATE OF RECEIPT FIELDS TESTS
    @Test // Validate existence of a field
    public void validateDateOfReceiptTest() {
        doctorCalendarPage.clickAddDoctorCalendarButton();
        logger.info("Checking for the 'Date Of Receipt' field visibility.");
        assertTrue(doctorCalendarPage.isDateOfReceiptFieldVisible(), "The field should be present");
        logger.info("The 'Date Of Receipt' field is visible.");
    }

    @Test // Validate that users can enter a date manually in correct format "mm/dd/yyyy"
    public void dateOfReceiptManualInputTestPos() throws IOException {
        doctorCalendarPage.resetForElementVisibility();

        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate currentDate = LocalDate.now();
        String formattedDate = currentDate.format(inputFormatter);

        logger.info("Trying to input date manually in correct format 'mm/dd/yyyy'.");
        doctorCalendarPage.setDateOfReceipt(formattedDate);
        mainPage.takeScreenshot("InputDate");

        assertEquals(formattedDate, doctorCalendarPage.getDateOfReceipt(), "The field should have a value.");
        logger.info("The date is entered successfully.");
    }

    @Test // Validate the calendar picker is loaded after clicking on the field
    public void dateOfReceiptDatePickerLoadTest() throws IOException {
        doctorCalendarPage.resetForElementVisibility();
        doctorCalendarPage.clickDateOfReceiptField();
        logger.info("Checking if the calendar is opened after clicking on a field.");
        mainPage.takeScreenshot("DateCalendar");
        assertTrue(doctorCalendarPage.isDateOfReceiptCalendarVisible(), "The calendar should be visible.");
        logger.info("The calendar loaded successfully.");
    }

    @Test // Validate that users cannot enter a date manually with missing values
    public void dateOfReceiptManualInputTestNeg() throws IOException {
        doctorCalendarPage.resetForElementVisibility();

        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy//");
        LocalDate currentDate = LocalDate.now();
        String formattedDate = currentDate.format(inputFormatter);

        logger.info("Trying to input date manually in incorrect format.");
        doctorCalendarPage.setDateOfReceipt(formattedDate);
        doctorCalendarPage.clickCreateButton();
        mainPage.takeScreenshot("InputDateIncorrectFormat");

        assertNotEquals(currentDate, doctorCalendarPage.getDateOfReceipt(), "The field should not accept an incorrect date format");
        logger.info("The date was changed automatically.");
    }

    // START OF RECEIPT TESTS
    @Test // Validate existence of a field
    public void validateStartOfReceiptTest() {
        doctorCalendarPage.clickAddDoctorCalendarButton();
        logger.info("Checking for the 'Start Of Receipt' field visibility.");
        assertTrue(doctorCalendarPage.isStartOfReceiptFieldVisible(), "The field should be present");
        logger.info("The 'Start Of Receipt' field is visible.");
    }

    @Test // Validate that users can manually enter a time of start
    public void startOfReceiptManualInputTestPos() throws IOException {
        doctorCalendarPage.resetForElementVisibility();
        logger.info("Trying to input time manually in correct format.");
        doctorCalendarPage.setStartOfReceipt("5:20 PM");
        mainPage.takeScreenshot("InputStartTime");

        assertEquals("5:20 PM", doctorCalendarPage.getStartOfReceipt(), "The field should have a value.");
        logger.info("The start time is entered successfully.");
    }

    @Test // Validate the time picker is loaded after clicking on the field
    public void startOfReceiptTimePickerLoadTest() throws IOException {
        doctorCalendarPage.resetForElementVisibility();
        doctorCalendarPage.clickStartOfReceiptField();
        logger.info("Checking if the time picker is opened after clicking on a field.");
        mainPage.takeScreenshot("StartTimePicker");
        assertTrue(doctorCalendarPage.isStartOfReceiptCalendarVisible(), "The time picker should be visible.");
        logger.info("The time picker loaded successfully.");
    }

    @Test // Validate that the user can choose a time from the time picker
    public void timePickerFunctionTestStartOfReceipt() throws IOException {
        doctorCalendarPage.resetForElementVisibility();

        String selectedTime = "5:20 PM",
                hour = "5:00 PM",
                minutes = "5:20 PM",
                xpathForHour = String.format(Consts.TIME_PICKER_TABLE_START, hour),
                xpathForMinutes = String.format(Consts.TIME_PICKER_TABLE_START, minutes);

        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));

        doctorCalendarPage.clickStartOfReceiptField();
        doctorCalendarPage.impWait(2);
        logger.info("Clicking on hour selector.");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathForHour))).click();

        doctorCalendarPage.impWait(2);
        logger.info("Clicking on minutes selector.");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathForMinutes))).click();

        assertEquals(selectedTime, doctorCalendarPage.getStartOfReceipt(), "The time should be selected correctly.");

        mainPage.takeScreenshot("StartOfReceiptSelectedTime");
        logger.info("Selected time (" + selectedTime + ") successfully.");
    }

    @ParameterizedTest // Validate that users cannot enter a time of start with an invalid format
    @ValueSource(strings = {"5:20", "13:20 PM", "25:00", "00:61", "12:00AM", "12:00 pm", "12:00", "5-20 PM", "520PM"})
    public void startOfReceiptManualInputTestNeg(String timeInputIncorrect) throws IOException {
        doctorCalendarPage.clickAddDoctorCalendarButton();
        doctorCalendarPage.setStartOfReceipt(timeInputIncorrect);
        doctorCalendarPage.clickCreateButton();
        mainPage.takeScreenshot("InputStartTimeIncorrect");
        assertNotEquals(timeInputIncorrect, doctorCalendarPage.getStartOfReceipt(), "The field should not accept an incorrect time format." + timeInputIncorrect);
        logger.info("The start time was changed automatically.");
    }

    // END OF RECEIPT TESTS
    @Test // Validate existence of a field
    public void validateEndOfReceiptTest() {
        doctorCalendarPage.clickAddDoctorCalendarButton();
        logger.info("Checking for the 'End Of Receipt' field visibility.");
        assertTrue(doctorCalendarPage.isEndOfReceiptFieldVisible(), "The field should be present");
        logger.info("The 'End Of Receipt' field is visible.");
    }

    @Test // Validate that users can manually enter an end time
    public void endOfReceiptManualInputTestPos() throws IOException {
        doctorCalendarPage.resetForElementVisibility();
        logger.info("Trying to input time manually in correct format.");
        doctorCalendarPage.setEndOfReceipt("9:00 PM");
        mainPage.takeScreenshot("InputEndTime");

        assertEquals("9:00 PM", doctorCalendarPage.getEndOfReceipt(), "The field should have a value.");
        logger.info("The end time is entered successfully.");
    }

    @Test // Validate the time picker is loaded after clicking on the field
    public void endOfReceiptTimePickerLoadTest() throws IOException {
        doctorCalendarPage.resetForElementVisibility();
        doctorCalendarPage.clickEndOfReceiptField();
        logger.info("Checking if the time picker is opened after clicking on a field.");
        mainPage.takeScreenshot("EndTimePicker");
        assertTrue(doctorCalendarPage.isEndOfReceiptCalendarVisible(), "The time picker should be visible.");
        logger.info("The time picker loaded successfully.");
    }

    @Test // Validate that the user can choose a time from the time picker
    public void timePickerFunctionTestEndOfReceipt() throws IOException {
        doctorCalendarPage.resetForElementVisibility();

        String selectedTime = "9:00 PM",
                hour = "9:00 PM",
                minutes = "9:00 PM",
                xpathForHour = String.format(Consts.TIME_PICKER_TABLE_END, hour),
                xpathForMinutes = String.format(Consts.TIME_PICKER_TABLE_END, minutes);

        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));

        doctorCalendarPage.clickEndOfReceiptField();
        doctorCalendarPage.impWait(2);
        logger.info("Clicking on an hour selector.");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathForHour))).click();

        doctorCalendarPage.impWait(2);
        logger.info("Clicking on minutes selector.");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathForMinutes))).click();

        assertEquals(selectedTime, doctorCalendarPage.getEndOfReceipt(), "The time should be selected correctly.");

        mainPage.takeScreenshot("EndOfReceiptSelectedTime");
        logger.info("Selected time (" + selectedTime + ") successfully.");
    }

    @ParameterizedTest // Validate that users cannot enter a time of end with an invalid format
    @ValueSource(strings = {"9:00", "13:20 PM", "25:00", "00:61", "12:00AM", "12:00 pm", "12:00", "9-00 PM", "900PM"})
    public void endOfReceiptManualInputTestNeg(String timeInputIncorrect) throws IOException {
        doctorCalendarPage.clickAddDoctorCalendarButton();
        logger.info("Expected that the input '" + timeInputIncorrect + "' is not accepted.");
        doctorCalendarPage.setEndOfReceipt(timeInputIncorrect);
        doctorCalendarPage.clickCreateButton();
        mainPage.takeScreenshot("InputEndTimeIncorrect");
        assertNotEquals(timeInputIncorrect, doctorCalendarPage.getEndOfReceipt(), "The field should not accept an incorrect time format." + timeInputIncorrect);
        logger.info("The end time was changed automatically.");
    }

    @Test // Validate that the user cannot choose 'End Of Receipt' time earlier then 'Start Of Receipt'
    public void timePickerTestEndOfReceiptNeg() throws IOException {
        doctorCalendarPage.resetForElementVisibility();

        String hour = "9:00 PM",
                minutesStart = "9:10 PM",
                minutesEnd = "9:00 PM",
                xpathForHourStart = String.format(Consts.TIME_PICKER_TABLE_START, hour),
                xpathForMinutesStart = String.format(Consts.TIME_PICKER_TABLE_START, minutesStart),
                xpathForHourEnd = String.format(Consts.TIME_PICKER_TABLE_END, hour),
                xpathForMinutesEnd = String.format(Consts.TIME_PICKER_TABLE_END, minutesEnd);

        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));

        doctorCalendarPage.clickStartOfReceiptField();
        doctorCalendarPage.impWait(2);
        logger.info("Selecting start time: " + minutesStart);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathForHourStart))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathForMinutesStart))).click();

        doctorCalendarPage.impWait(2);
        logger.info("Attempting to select end time: " + minutesEnd);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathForHourEnd))).click();
        WebElement endMinuteElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathForMinutesEnd)));

        String classAttribute = endMinuteElement.getAttribute("class");
        assertTrue(classAttribute.contains("disabled"), "The 'End Of Receipt' minute should be marked as disabled.");

        assertNotEquals(minutesEnd, doctorCalendarPage.getEndOfReceipt(), "The time before the selected 'Start Of Receipt' should not be selected.");

        mainPage.takeScreenshot("EndOfReceiptTimeNeg");
        logger.info("Validation successful: The time before the selected 'Start Of Receipt' was not selected. Actual end time: " + doctorCalendarPage.getEndOfReceipt());
    }

    // DATE PICKER CALENDAR TESTS
    @Test // Validate that the user can choose a calendar date from the date picker
    public void datePickerFunctionTest() throws IOException {
        doctorCalendarPage.resetForElementVisibility();
        doctorCalendarPage.clickDateOfReceiptField();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.ENGLISH);
        LocalDate currentDate = LocalDate.now();
        String formattedCurrentDate = currentDate.format(formatter);
        int today = LocalDate.now().getDayOfMonth();

        String xpathForToday = String.format(Consts.CALENDAR_PICKER_DAY_FIELD, today);

        logger.info("Checking if today's date is selectable in the calendar.");

        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(2));
        WebElement todayElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathForToday)));
        todayElement.click();

        assertEquals(formattedCurrentDate, doctorCalendarPage.getDateOfReceipt(), "The field should have a value.");
        mainPage.takeScreenshot("TodayInCalendarSelected");
        logger.info("Selected today's date (" + today + ") successfully.");
    }

    @Test //Validate that the user can navigate to the previous months, using left arrow button
    public void datePickerPrevMonthNavigationTest() throws IOException {
        doctorCalendarPage.resetForElementVisibility();
        doctorCalendarPage.clickDateOfReceiptField();

        YearMonth currentMonth = YearMonth.now();
        int numberOfClicks = 6;
        doctorCalendarPage.impWait(2);
        for (int i = 1; i <= numberOfClicks; i++) {
            logger.info("Clicking on the previous month button.");
            doctorCalendarPage.clickDatePickerLeft();

            WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
            doctorCalendarPage.impWait(2);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Consts.DATE_PICKER_HEADER)));

            String previousMonthText = doctorCalendarPage.getHeaderPickerDate();
            logger.info("Month after click " + i + ": " + previousMonthText);

            YearMonth previousMonth = YearMonth.parse(previousMonthText, DateTimeFormatter.ofPattern("MMMM yyyy", Locale.ENGLISH));
            YearMonth expectedPrevMonth = currentMonth.minusMonths(i);

            assertEquals(expectedPrevMonth, previousMonth, "The picker did not navigate to the correct month on click " + i);
            logger.info("Successfully navigated to the correct month after " + i + " clicks.");

            mainPage.takeScreenshot("PrevMonthPickerTest_Click" + i);
        }
    }

    @Test //Validate that the user can navigate to the next months, using right arrow button
    public void datePickerNextMonthNavigationTest() throws IOException {
        doctorCalendarPage.resetForElementVisibility();
        doctorCalendarPage.clickDateOfReceiptField();
        doctorCalendarPage.impWait(2);

        YearMonth currentMonth = YearMonth.now();

        int numberOfClicks = 6;
        for (int i = 1; i <= numberOfClicks; i++) {
            logger.info("Clicking on the next month button.");
            doctorCalendarPage.clickDatePickerRight();

            WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
            doctorCalendarPage.impWait(2);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Consts.DATE_PICKER_HEADER)));

            String nextMonthText = doctorCalendarPage.getHeaderPickerDate();
            logger.info("Month after click " + i + ": " + nextMonthText);
            YearMonth nextMonth = YearMonth.parse(nextMonthText, DateTimeFormatter.ofPattern("MMMM yyyy", Locale.ENGLISH));
            YearMonth expectedNextMonth = currentMonth.plusMonths(i);

            assertEquals(expectedNextMonth, nextMonth, "The picker did not navigate to the correct month on click " + i);
            mainPage.takeScreenshot("NextMonthPickerTest_Click" + i);
            logger.info("Successfully navigated to the correct month after " + i + " clicks.");

            mainPage.takeScreenshot("NextMonthPickerTest_Click" + i);
        }
    }

    @Test // Validate that the user can navigate to the current year months list by clicking on a month header. All 12 months are visible in accurate order.
    public void datePickerMonthViewTest() throws IOException {
        doctorCalendarPage.resetForElementVisibility();
        doctorCalendarPage.clickDateOfReceiptField();
        doctorCalendarPage.impWait(2);
        doctorCalendarPage.clickChangeViewDatePicker();

        List<String> expectedMonths = Arrays.asList(
                "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
        );

        doctorCalendarPage.impWait(2);
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
        List<WebElement> monthElements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(Consts.DATE_PICKER_TABLE)));

        assertEquals(12, monthElements.size(), "Expected 12 months to be displayed, but found: " + monthElements.size() + ".");

        List<String> actualMonths = monthElements.stream()
                .map(monthElement -> monthElement.getText().trim())
                .collect(Collectors.toList());

        assertEquals(expectedMonths, actualMonths, "The months are not displayed in the correct order. Expected: " + expectedMonths + ", but found: " + actualMonths);

        mainPage.takeScreenshot("MonthViewTest");
        logger.info("Month view is displayed correctly with all 12 months in order.");
    }

    @ParameterizedTest // Validate that the user can choose a calendar month from the date picker
    @ValueSource(strings = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"})
    public void monthPickerFunctionTest(String monthToSelect) throws IOException {
        doctorCalendarPage.resetForElementVisibility();
        doctorCalendarPage.clickDateOfReceiptField();
        doctorCalendarPage.clickChangeViewDatePicker();

        logger.info("Checking if the month '" + monthToSelect + "' can be clicked for a day view.");

        String monthXPath = String.format(Consts.DATE_PICKER_TABLE_LINK, monthToSelect);
        logger.info("Using XPath: " + monthXPath);

        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));

        try {
            WebElement monthOption = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(monthXPath)));
            wait.until(ExpectedConditions.elementToBeClickable(monthOption));
            monthOption.click();

            wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath(Consts.DATE_PICKER_HEADER), monthToSelect));

            String monthText = doctorCalendarPage.getHeaderPickerDate();
            assertTrue(monthText.contains(monthToSelect), "The picker did not display the correct month.");

            mainPage.takeScreenshot("MonthOnHeader_" + monthToSelect);
            logger.info("Successfully displayed correct month: " + monthToSelect);
        } catch (TimeoutException e) {
            logger.error("Timeout while trying to select month: " + monthToSelect, e);
        } catch (StaleElementReferenceException e) {
            logger.error("StaleElementReferenceException encountered while selecting month: " + monthToSelect, e);
        }
    }

    @Test //Validate that the user can navigate between years, using the right and left arrow buttons
    public void datePickerPrevYearNavigationTest() throws IOException {
        doctorCalendarPage.resetForElementVisibility();
        doctorCalendarPage.clickDateOfReceiptField();
        doctorCalendarPage.clickChangeViewDatePicker();
        doctorCalendarPage.impWait(2);

        Year currentYear = Year.now();
        int numberOfClicks = 3;

        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));

        for (int i = 1; i <= numberOfClicks; i++) {
            logger.info("Clicking on the previous year button.");

            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Consts.LEFT_ARROW_DATE_PICKER)));
            doctorCalendarPage.clickDatePickerLeft();

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Consts.DATE_PICKER_HEADER)));
            doctorCalendarPage.impWait(2);
            String prevYearText = doctorCalendarPage.getHeaderPickerDate();

            int previousYearInt = Integer.parseInt(prevYearText.replaceAll("\\D+", ""));
            Year previousYear = Year.of(previousYearInt);

            Year expectedPrevYear = currentYear.minusYears(i);

            assertEquals(expectedPrevYear, previousYear, "The picker did not navigate to the correct year on click " + i);

            mainPage.takeScreenshot("PrevYearPickerTest_Click" + i);
            logger.info("Successfully navigated to the correct year after " + i + " clicks.");
        }
    }

    @Test //Validate that the user can navigate between years, using the right and left arrow buttons
    public void datePickerNextYearNavigationTest() throws IOException {
        doctorCalendarPage.resetForElementVisibility();
        doctorCalendarPage.clickDateOfReceiptField();
        doctorCalendarPage.impWait(2);
        doctorCalendarPage.clickChangeViewDatePicker();

        Year currentYear = Year.now();
        int numberOfClicks = 3;

        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));

        for (int i = 1; i <= numberOfClicks; i++) {
            logger.info("Clicking on the next year button.");

            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Consts.RIGHT_ARROW_DATE_PICKER)));
            doctorCalendarPage.impWait(2);
            doctorCalendarPage.clickDatePickerRight();
            doctorCalendarPage.impWait(2);

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Consts.DATE_PICKER_HEADER)));

            String nextYearText = doctorCalendarPage.getHeaderPickerDate();

            int nextYearInt = Integer.parseInt(nextYearText.replaceAll("\\D+", ""));
            Year nextYear = Year.of(nextYearInt);

            Year expectedNextYear = currentYear.plusYears(i);

            assertEquals(expectedNextYear, nextYear, "The picker did not navigate to the correct year on click " + i);

            mainPage.takeScreenshot("NextYearPickerTest_Click" + i);
            logger.info("Successfully navigated to the correct year after " + i + " clicks.");
        }
    }

    @Test // Validate that the user can navigate to the list of years, by clicking on a year header. 12 years are visible
    public void datePickerYearViewTest() throws IOException {
        doctorCalendarPage.resetForElementVisibility();
        doctorCalendarPage.clickDateOfReceiptField();
        doctorCalendarPage.clickForYearsView();

        List<WebElement> yearElements = webDriver.findElements(By.xpath(Consts.DATE_PICKER_TABLE));

        assertEquals(12, yearElements.size(), "There should be exactly 12 years displayed.");

        mainPage.takeScreenshot("YearViewTest");
        logger.info("Year view is displayed correctly with 12 years options to pick.");
    }

    @ParameterizedTest // Validate that the user can choose a calendar year from the date picker
    @ValueSource(strings = {"2022", "2024", "2028", "2030"})
    public void yearPickerFunctionTest(String yearToSelect) throws IOException {
        doctorCalendarPage.resetForElementVisibility();
        doctorCalendarPage.clickDateOfReceiptField();
        doctorCalendarPage.impWait(2);
        doctorCalendarPage.clickForYearsView();

        logger.info("Selecting the year: " + yearToSelect);

        String yearXPath = String.format(Consts.DATE_PICKER_TABLE_LINK, yearToSelect);
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
        WebElement yearOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(yearXPath)));
        yearOption.click();

        String yearText = doctorCalendarPage.getHeaderPickerDate();
        assertEquals(yearToSelect, yearText, "The picker did not navigate to the correct year.");

        mainPage.takeScreenshot("YearOnHeader" + yearToSelect);
        logger.info("Successfully navigated to the correct year: " + yearToSelect);
    }

    @Test // Validate that the user can navigate between decades, using eft arrow button
    public void datePicker12PrevYearsNavigationTest() throws IOException {
        doctorCalendarPage.resetForElementVisibility();
        doctorCalendarPage.clickDateOfReceiptField();
        doctorCalendarPage.clickForYearsView();

        Year currentYear = Year.now();

        logger.info("Clicking on the previous years button.");
        doctorCalendarPage.clickDatePickerLeft();

        List<WebElement> yearsElements = webDriver.findElements(By.xpath(Consts.DATE_PICKER_TABLE));
        List<String> displayedYears= new ArrayList<>();
        for (WebElement yearElement : yearsElements) {
            displayedYears.add(yearElement.getText().trim());
        }

        assertFalse(displayedYears.contains(currentYear.toString()), "The current year should not be displayed in the year picker.");

        mainPage.takeScreenshot("PrevYearsPickerTest");
        logger.info("Validated that the current year is not displayed in the year picker after navigation.");
    }

    @Test // Validate that the user can navigate between decades, using the right arrow button
    public void datePicker12NextYearsNavigationTest() throws IOException {
        doctorCalendarPage.resetForElementVisibility();
        doctorCalendarPage.clickDateOfReceiptField();
        doctorCalendarPage.clickForYearsView();

        Year currentYear = Year.now();

        logger.info("Clicking on the next years button.");
        doctorCalendarPage.clickDatePickerRight();

        List<WebElement> yearsElements = webDriver.findElements(By.xpath(Consts.DATE_PICKER_TABLE));
        List<String> displayedYears= new ArrayList<>();
        for (WebElement yearElement : yearsElements) {
            displayedYears.add(yearElement.getText().trim());
        }

        assertFalse(displayedYears.contains(currentYear.toString()), "The current year should not be displayed in the year picker.");

        mainPage.takeScreenshot("NextYearsPickerTest");
        logger.info("Validated that the current year is not displayed in the year picker after navigation.");
    }

    @Test // Validate that the user can navigate to the day view calendar, by clicking on a decades header
    public void daysViewFromYearsTest () throws IOException {
        doctorCalendarPage.resetForElementVisibility();
        doctorCalendarPage.clickDateOfReceiptField();
        doctorCalendarPage.clickForYearsView();

        logger.info("Clicking on years header for a day view.");

        doctorCalendarPage.clickChangeViewDatePicker();

        String headerMonth = doctorCalendarPage.getHeaderPickerDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.ENGLISH);
        YearMonth currentMonth = YearMonth.now();
        String formattedCurrentDate = currentMonth.format(formatter);

        assertEquals(formattedCurrentDate, headerMonth, "The picker did not navigate to the day view with the current month.");
        mainPage.takeScreenshot("DayViewAfterYears");
        logger.info("Successfully navigated to the correct month with day view.");
    }

    // BUTTONS TESTS
    @Test //Validate that 'Close' button is visible
    public void closeButtonVisibilityTest(){
        doctorCalendarPage.clickAddDoctorCalendarButton();
        logger.info("Checking the 'Close' button existence.");
        assertTrue(doctorCalendarPage.isCloseButtonVisible(), "The button is not visible");
        logger.info("The 'Close' button is visible.");
    }

    @Test // Validate if the appointment is not created after pressing the button “Close”, and the pop-up window is closed
    public void closeButtonFunctionTest() throws IOException {
        doctorCalendarPage.clickAddDoctorCalendarButton();
        logger.info("Clicking the 'Close' button.");
        doctorCalendarPage.clickCloseButton();
        assertTrue(doctorCalendarPage.isCalendarVisible(), "The calendar is expected to be visible but it isn't.");
        mainPage.takeScreenshot("CloseButtonFunctionality");
        logger.info("The 'New Doctor Calendar' form had been successfully closed.");
    }

    @Test //Validate that 'Create' button is visible
    public void createButtonVisibilityTest(){
        doctorCalendarPage.clickAddDoctorCalendarButton();
        logger.info("Checking the 'Create' button existence.");
        assertTrue(doctorCalendarPage.isCreateButtonVisible(), "The button is not visible.");
        logger.info("The 'Create' button is visible.");
    }

    @Test // Validate if the appointment is created after filling out all the info and pressing the button “Create”
    public void createButtonFunctionTest() throws IOException {
        doctorCalendarPage.clickAddDoctorCalendarButton();
        logger.info("Filling out all the info.");

        doctorCalendarPage.fillAllFields("Medical", "Scheduled", "123 AVE, Brooklyn NY, 11224", "GREGORY HOUSE (ACUPUNCTURE)", "", "5:10 AM","9:20 AM", "Hello!");
        doctorCalendarPage.setDateOfReceipt(LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));

        logger.info("Clicking the 'Create' button.");
        doctorCalendarPage.clickCreateButton();
        mainPage.takeScreenshot("CreateButtonFunctionality");
        logger.info("Checking for a successful message.");
        assertTrue(doctorCalendarPage.isSuccessMessagePresent(), "The success message did not appear as expected.");
        assertTrue(doctorCalendarPage.isAppointmentCreated());
        logger.info("The 'New Doctor Calendar' form had been successfully closed and new appointment was created.");

        logger.info("Deleting created appointment to clean up the schedule.");
        doctorCalendarPage.clickOnCreatedAppointment();
        doctorCalendarPage.clickDeleteAppointment();

        doctorCalendarPage.navigateToDoctorCalendarPage();
        assertFalse(doctorCalendarPage.isAppointmentCreated());
        logger.info("Appointment was deleted.");
    }
}
