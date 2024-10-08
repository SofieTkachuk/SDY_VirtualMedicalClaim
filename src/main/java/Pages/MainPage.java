package Pages;

import Consts.Consts;
import Utils.PropertiesUtil;

import static Consts.Consts.*;

public class MainPage extends BasePage {

    public void navigateToMainPage() {
        webDriver.get(PropertiesUtil.getProperty("MAIN_URL"));
    }
    public void openUserProfileDropdown() {
        clickElementByXpath(Consts.USER_PROFILE_DROPDOWN);
    }
    public void openUserProfile() {
        clickElementByXpath(Consts.USER_PROFILE);
    }
    public void openOtherMenuDropdown() {
        clickElementByXpath(OTHER_MENU_DROPDOWN);
    }
    public void openDoctorCalendar() {
        clickElementByXpath(DOCTOR_CALENDAR);
	}
}
