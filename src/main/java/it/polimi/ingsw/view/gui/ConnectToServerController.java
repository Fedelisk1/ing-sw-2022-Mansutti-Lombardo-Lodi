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

    public void onAction(ActionEvent actionEvent) {
        System.out.println(address.getText());
    }

    public void handleConnectButton(ActionEvent actionEvent) {
        Map<String, String> serverInfo = new HashMap<>();
        serverInfo.put("address", address.getText());
        serverInfo.put("port", port.getText());

        notifyObservers(o -> o.onServerInfoInput(serverInfo));
    }

    public void onError() {
        System.out.println("error");
        connectionError.setVisible(true);
    }
}
