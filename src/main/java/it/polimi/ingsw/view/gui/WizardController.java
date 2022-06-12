package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.Wizard;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.ViewObservable;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import static it.polimi.ingsw.model.Wizard.*;

public class WizardController extends ViewObservable implements Initializable {
    public HBox wizardsHBox;
    private List<Wizard> availableWizards;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        availableWizards = Arrays.stream(values()).toList();
    }

    public void setAvailableWizards(List<Wizard> availableWizardsUpdate) {
        List<Wizard> removed = new ArrayList<>(availableWizards);
        removed.removeAll(availableWizardsUpdate);

        availableWizards = availableWizardsUpdate;

        System.out.println(wizardsHBox.getChildren());

        removed.forEach(r -> {
            Button toRemove = (Button) Gui.getStage().getScene().lookup("#" + r.toString().toLowerCase() + "Button");
            wizardsHBox.getChildren().remove(toRemove);
            System.out.println(wizardsHBox.getChildren());
        });
    }

    public void onError() {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setHeaderText("Wizard already taken");
        a.setContentText("This wizard has already been taken by another player.");
        a.show();
    }

    public void handleKingButton(ActionEvent actionEvent) {
        String chosenId = ((Button) actionEvent.getSource()).getId();
        String chosenWizard = chosenId.substring(0, chosenId.indexOf("Button")).toUpperCase();

        notifyObservers(o -> o.onWizardChosen(Wizard.valueOf(chosenWizard)));
    }
}
