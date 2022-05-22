package it.polimi.ingsw.network.message;

public class LoginOutcome extends Message {
    public boolean loginSuccess;

    LoginOutcome(boolean success) {
        super(Message.SERVER_NICKNAME, MessageType.LOGIN_OUTCOME);
        loginSuccess = success;
    }

    public boolean isLoginSuccess() {
        return loginSuccess;
    }
}
