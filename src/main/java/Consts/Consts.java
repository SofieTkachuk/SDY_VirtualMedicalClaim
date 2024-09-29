package Consts;

public class Consts {
    // This is the URL of the website that we are testing
    public static final String MAIN_URL = "https://legion.virtualmedclaims.com/",

    // These are the XPaths of the elements that we are interacting with in LoginPage.java
            PASSWORD_FIELD = "//input[@id='password']",
            LOGIN_BUTTON = "//input[@type='submit']",
            REMEMBER_ME_CHECKBOX = "//input[@type='checkbox']",
            FORGOT_PASSWORD_LINK = "//*[text()='Forgot Password?']",
            USERNAME_FIELD = "//input[@id='username']",
            FORGOT_USERNAME_PASSWORD_LINK = "//*[text()='Forgot username/password?']",

    // These are the XPaths of the elements that we are interacting with in MainPage.java
            USER_PROFILE_DROPDOWN = "//*[@class='large user circle icon']",
            USER_PROFILE = "//a[@href='/profile/']",
            OTHER_MENU_DROPDOWN = "//i[@class='large angle double down icon']",
            DOCTOR_CALENDAR = "//a[@href='/appointment/']",
            PATIENTS_ICON = "//*[@id='createPatient']",
            CREATE_BUTTON = "//*[@id='objectMenu']/a",

    // current valid credentials info
            email = "sdytestuser@gmail.com",
            first_name = "USER 1",
            username = "USER1",
            last_name = "Roma",
            pass = "Welcome123!",

    // fields input data for profile page tests
            first_name_updated = "Mary",
            username_updated = "Mary1",
            last_name_updated = "Bing",
            new_password = "ThisIsNewPass71!",

    // These are the XPaths of the elements that we are interacting with in ProfilePage.java
            PROFILE_FORM = "//form[@name='profile_user']",
            COLUMNS = "//div[@class='ui card fluid']",
            HEADER_TEXT = "//div[@class='ui label blueMate fluid']",
            EMAIL_FIELD_PROFILE = "//input[@id='profile_user_email']",
            FIRST_NAME_FIELD_PROFILE = "//input[@id='profile_user_first_name']",
            REPLY_TO_FIELD = "//input[@id='profile_user_emailReplyTo']",
            USER_NAME_FIELD = "//input[@id='profile_user_username']",
            LAST_NAME_FIELD_PROFILE = "//input[@id='profile_user_last_name']",
            OLD_PASSWORD_FIELD = "//input[@id='old-password-input']",
            NEW_PASSWORD_FIELD = "//input[@id='profile_user_plainPassword_first']",
            REPEAT_NEW_PASSWORD_FIELD = "//input[@id='profile_user_plainPassword_second']",
            DROPDOWN = "//select[@id='profile_user_mainMenuCount']/following-sibling::i[@class='dropdown icon']",
            DROPDOWN_VALUE = "//select[@id='profile_user_mainMenuCount']/following-sibling::div[contains(@class, 'text')]",
            DROPDOWN_OPTION_TO_RESET = ".//div[@class='item' and @data-value='']",
            SIMPLE_MAIN_MENU = "//input[@type='checkbox' and @id='profile_user_simpleMainMenu']",
            CUSTOM_MAIN_MENU = "//input[@type='checkbox' and @id='profile_user_simpleMainMenu']",
            UPDATE_INFORMATION_BUTTON = "//div[@id='change-password-form-submit']",
            CANCEL_BUTTON_PROFILE = "//a[@class='ui black deny button']",
            UPDATE_INFO_SUCCESS = "//div[@class='toast toast-success']",
            UPDATE_INFO_ERROR = "//div[@class='toast toast-error']",
            EYE_ICON = "//div[contains(@class,'ui icon mini button')]",

    // These are the XPaths of the elements that we are interacting with in ForgotUsernamePasswordTest
            OLD_PASSWORD_PROFILE = "//input[@id='old-password-input']",
            NEW_PASSWORD_PROFILE = "//input[@id='profile_user_plainPassword_first']",
            RESET_PASS_HEADER = "//div[@id='forgot-password-modal']//div[@class='header']",

    // Multiple Doctor Calendar page
            APPOINTMENT_DELETE_BUTTON = "//*[@id = 'deleteDoctorCalendarButton']",
            APPOINTMENT_DELETE_CONFIRMATION_BUTTON = "//div[@class='ui green positive ok inverted button']",
            LIST_DAY_BUTTON = "//button[@class = 'fc-listDay-button fc-button fc-button-primary' and text()='list day']",
            LIST_WEEK_BUTTON = "//button[text()='list week']",
            LIST_MONTH_BUTTON = "//button[text()='list month']",
            TYPE_FIELD = "//select[@id='appointment_type']",
            PLACE_OF_SERVICE_FIELD = "//select[@id='appointment_location']",
            DOCTOR_FIELD = "//select[@id='appointment_doctor']",
            STATUS_FIELD = "//select[@id='appointment_status']",
            CALENDAR_PICKER_DAY_FIELD = "//td[text()='%d']",
            CLOSE_DETAILS_BUTTON = "//*[@id='show-details-appointment-modal']/div[3]/div[1]",
            RESOURCE_DAY_BUTTON = "//button[text()='Resource Day']",
            END_APPOINTMENT_TIME_FIELD = "//*[@id='appointmentEndTime']",

    // These are the XPaths of the elements that we are interacting with in DoctorCalendarPage.java
            FULL_CALENDAR = "//div[@id='full-calendar']",
            MENU_HEADER = "//*[contains(text(), 'Doctor Calendar')]",
            DOCTOR_CALENDAR_ALL_BUTTONS = "//button[@type='button']",
            CALENDARS_DATE_HEADER = "//div[contains(@class, 'fc-center')]//h2",
            PREV_BUTTON = "//button[@aria-label='prev']",
            NEXT_BUTTON = "//button[@aria-label='next']",
            TODAY_BUTTON = "//button[@class='fc-today-button fc-button fc-button-primary']",
            ADD_DOCTOR_CALENDAR_BUTTON = "//button[@class='fc-addAppointmentButton-button fc-button fc-button-primary']",
            DAY_BUTTON = "//button[@class='fc-timeGridDay-button fc-button fc-button-primary']",
            DAY_ACTIVE_BUTTON = "//button[@class='fc-timeGridDay-button fc-button fc-button-primary fc-button-active']",
            WEEK_BUTTON = "//button[@class='fc-timeGridWeek-button fc-button fc-button-primary']",
            MONTH_BUTTON = "//button[@class='fc-dayGridMonth-button fc-button fc-button-primary']",
            ADD_DOCTOR_CALENDAR_FORM = "//div[@class = 'ui modal transition visible active' and @id='newAppointmentModal']",
            ADD_DOCTOR_CALENDAR_ALL_DROPDOWNS = "//*[@id='appointment-form']//i[@class='dropdown icon']",
            TYPE_DROPDOWN_VALUE = "//select[@id='appointment_type']/following-sibling::div[contains(@class, 'text')]",
            STATUS_DROPDOWN = "//select[@id='appointment_status']/following-sibling::i[@class='dropdown icon']",
            STATUS_DROPDOWN_VALUE = "//select[@id='appointment_status']/following-sibling::div[contains(@class, 'text')]",
            PLACE_DROPDOWN = "//select[@id='appointment_location']/following-sibling::i[@class='dropdown icon']",
            PLACE_DROPDOWN_VALUE = "//select[@id='appointment_location']/following-sibling::div[contains(@class, 'text')]",
            DOCTOR_DROPDOWN = "//select[@id='appointment_doctor']/following-sibling::i[@class='dropdown icon']",
            DOCTOR_DROPDOWN_VALUE = "//select[@id='appointment_doctor']/following-sibling::div[contains(@class, 'text')]",
            DATE_OF_RECEIPT_FIELD = "//input[@name = 'appointmentDate']",
            START_OF_RECEIPT_FIELD = "//input[@name = 'appointmentStartTime']",
            END_OF_RECEIPT_FIELD = "//input[@name = 'appointmentEndTime']",
            SEARCH_TYPE = "//select[@id='appointment_type']/following-sibling::input[@class = 'search']",
            SEARCH_STATUS = "//select[@id='appointment_status']/following-sibling::input[@class = 'search']",
            SEARCH_PLACE = "//select[@id='appointment_location']/following-sibling::input[@class = 'search']",
            SEARCH_DOCTOR = "//select[@id='appointment_doctor']/following-sibling::input[@class = 'search']",
            DROPDOWN_SEARCHED_OPTION = "//div[contains(@class, 'menu transition visible')]//div[@class='item selected' and text()='",
            DATE_OF_RECEIPT_CALENDAR = "//input[@class = 'ui fluid calendar-input date visible']",
            DATE_PICKER_HEADER = "//*[@id='appointmentDateCalendar']//table/thead/tr[1]/th/span[1]",
            DATE_PICKER_TABLE = "//*[@id='appointmentDateCalendar']/div/div/table/tbody//td",
            DATE_PICKER_TABLE_LINK = "//*[@id='appointmentDateCalendar']/div/div/table/tbody//td[text()='%s']",
            LEFT_ARROW_DATE_PICKER = "//*[@id='appointmentDateCalendar']//table/thead/tr[1]/th/span[2]",
            RIGHT_ARROW_DATE_PICKER = "//*[@id='appointmentDateCalendar']//table/thead/tr[1]/th/span[3]",
            START_OF_RECEIPT_CALENDAR = "//input[contains(@class, 'ui fluid visible') and @name='appointmentStartTime']",
            TIME_PICKER_TABLE_START = "//*[@id='appointmentStartTimeCalendar']/div/div/table/tbody//td[text()='%s']",
            END_OF_RECEIPT_CALENDAR = "//input[contains(@class, 'ui fluid visible') and @name='appointmentEndTime']",
            TIME_PICKER_TABLE_END = "//*[@id='appointmentEndTimeCalendar']/div/div/table/tbody//td[text()='%s']",
            NOTES_FIELD_DOC_CALENDAR_FORM = "//*[@id='appointment_notes']",
            CLOSE_BUTTON_NEW_DOC_CALENDAR = "//*[@id='newAppointmentModal']/div[3]/div[1]",
            CREATE_BUTTON_NEW_DOC_CALENDAR = "//div[@id = 'appointment-form-submit-btn']",
            TYPE_MANDATORY_MESSAGE = "//*[contains(text(), 'Type is required')]",
            STATUS_MANDATORY_MESSAGE = "//*[contains(text(), 'Status is required')]",
            PLACE_OF_SERVICE_MANDATORY_MESSAGE = "//*[contains(text(), 'Location is required')]",
            DOCTOR_MANDATORY_MESSAGE = "//*[contains(text(), 'Doctor is required')]",
            DATE_MANDATORY_MESSAGE = "//*[contains(text(), 'Date is required')]",
            TIME_MANDATORY_MESSAGE = "//*[contains(text(), 'Time is required')]",
            CALENDAR_CONTENT_BY_TIME = "//tr[@data-time]",
            CALENDAR_CONTENT_BY_DAYS = "//a[contains(@data-goto, 'day')]",
            EMPTY_CELL_DAY = "//*[@class= 'fc-content-skeleton']",
            APPOINTMENT_CELL = "//*[@class = 'fc-title']",
            APPOINTMENT_DETAILS = "//div[contains(@class, 'ui modal large transition visible active') and @id='show-details-appointment-modal']",
            CREATED_APPOINTMENT = "//div[contains(@class, 'fc-time') and @data-full='5:10 AM - 9:20 AM']",

    // MATCHING FOR BOTH SOFIE'S PAGES
            DROPDOWN_OPTIONS = "//div[contains(@class, 'menu transition visible')]",
            DROPDOWN_OPTION_TO_SELECT = "//div[contains(@class, 'menu transition visible')]//div[@class='item' and text()='";

}
