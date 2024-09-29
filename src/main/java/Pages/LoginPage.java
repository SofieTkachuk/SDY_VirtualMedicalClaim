package Pages;

import Consts.Consts;

import static Consts.Consts.*;

public class LoginPage extends BasePage {

    public void navigateToLoginPage() {
        webDriver.get(Consts.MAIN_URL + "login");
    }

    public void enterUsername(String username) {
        sendTextToElementByXpath(Consts.USERNAME_FIELD, username);
    }

    public void enterPassword(String password) {
        sendTextToElementByXpath(Consts.PASSWORD_FIELD, password);
    }

    public void clickLoginButton() {
        clickElementByXpath(Consts.LOGIN_BUTTON);
    }
}