package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.observer.ViewObservable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.HashMap;
import java.util.Map;

public class ConnectToServerController extends ViewObservable {
    public TextField address;
    public TextField port;
    public Text connectionError;

    /**
     * notifies observer with server's address and port
     * @param actionEvent
     */
    public void handleConnectButton(ActionEvent actionEvent) {
        Map<String, String> serverInfo = new HashMap<>();
        serverInfo.put("address", address.getText());
        serverInfo.put("port", port.getText());

        notifyObservers(o -> o.onServerInfoInput(serverInfo));
    }

    /**
     * shows connection to server error
     */
    public void onError() {
        connectionError.setVisible(true);
    }
}
