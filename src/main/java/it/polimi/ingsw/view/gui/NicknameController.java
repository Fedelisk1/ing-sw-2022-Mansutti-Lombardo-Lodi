package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.observer.ViewObservable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class NicknameController extends ViewObservable {
    @FXML
    public TextField username;
    public Text errorLabel;

    public void handleOkButton(ActionEvent actionEvent) {
        notifyObservers(o -> o.onNicknameInput(username.getText()));
    }

    public void onError() {
        errorLabel.setVisible(true);
    }
}
