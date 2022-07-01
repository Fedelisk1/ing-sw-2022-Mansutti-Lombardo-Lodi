package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.Wizard;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.ViewObservable;
import javafx.application.Platform;
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

/**
 * Management of wizards in the gui
 */
public class WizardController extends ViewObservable implements Initializable {
    public HBox wizardsHBox;
    public Button kingButton;
    public Button sorcererButton;
    public Button pixieButton;
    public Button wizardButton;
    private List<Wizard> availableWizards;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        availableWizards = Arrays.stream(values()).toList();
    }

    /**
     * After each choice it updates the view of the available wizards removing those already chosen
     * @param availableWizardsUpdate list of wizard
     */
    public void setAvailableWizards(List<Wizard> availableWizardsUpdate) {
        List<Wizard> removed = new ArrayList<>(availableWizards);
        removed.removeAll(availableWizardsUpdate);

        availableWizards = availableWizardsUpdate;

        removed.forEach(r -> {
            Button toRemove = null;

            switch (r) {
                case KING -> toRemove = kingButton;
                case PIXIE -> toRemove = pixieButton;
                case SORCERER -> toRemove = sorcererButton;
                case WIZARD -> toRemove = wizardButton;
            }

            Button finalToRemove = toRemove;
            Platform.runLater(() -> {
                wizardsHBox.getChildren().remove(finalToRemove);
            });
        });
    }

    public void handleKingButton(ActionEvent actionEvent) {
        String chosenId = ((Button) actionEvent.getSource()).getId();
        String chosenWizard = chosenId.substring(0, chosenId.indexOf("Button")).toUpperCase();

        notifyObservers(o -> o.onWizardChosen(Wizard.valueOf(chosenWizard)));
    }
}
