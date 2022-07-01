package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.observer.ViewObservable;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.*;

public class GameInfoController extends ViewObservable implements Initializable {
    public CheckBox expert;
    public ChoiceBox<String> playersChoiceBox;
    private GuiManager guiManager;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<String> allowedPlayers = Arrays.asList("2", "3");
        playersChoiceBox.setItems(FXCollections.observableList(allowedPlayers));
    }

    public void setGuiManager(GuiManager guiManager) {
        this.guiManager = guiManager;
    }


    public void handleStartButton(ActionEvent actionEvent) {
        int players = Integer.parseInt(playersChoiceBox.getSelectionModel().getSelectedItem());
        boolean expertMode = expert.isSelected();

        notifyObservers(o -> o.onNewGameParametersInput(players, expertMode));

        guiManager.askWizard();
    }
}
