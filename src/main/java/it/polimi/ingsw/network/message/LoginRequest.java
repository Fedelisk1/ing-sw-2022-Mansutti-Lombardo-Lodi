package it.polimi.ingsw.network.message;

import java.io.Serial;

/**
 * Message sent from client to server ofter the connection is successfully established.
 */
public class LoginRequest extends Message {
    @Serial
    private static final long serialVersionUID = -290267858031312316L;
    private final String username;

    public LoginRequest(String username) {
        super(username, MessageType.LOGIN_REQUEST);
        this.username = username;
    }


    public String getUsername() {
        return username;
    }
}
