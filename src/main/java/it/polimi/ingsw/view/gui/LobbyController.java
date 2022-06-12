package it.polimi.ingsw.view.gui;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import it.polimi.ingsw.model.reduced.ReducedPlayer;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


import java.awt.*;
import java.net.URL;
import java.util.*;
import java.util.List;

public class LobbyController implements Initializable {
    private List<ReducedPlayer> players;
    private List<ImageView> spinners;
    private GuiManager guiManager;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        players = new ArrayList<>();
        spinners = new ArrayList<>();
    }

    public void setGuiManager(GuiManager guiManager) {
        this.guiManager = guiManager;
    }

    public void setPlayers(List<ReducedPlayer> playersUpdate) {
        playersUpdate.stream().filter(p -> !players.contains(p))
                .forEach(this::addPlayer);
    }

    private void addPlayer(ReducedPlayer player) {
        players.add(player);
        int i = players.size();

        // firstly remove the spinner
        System.out.println("remove spinner from vbox" + i);
        VBox vBox = (VBox) Gui.getStage().getScene().lookup("#vBoxPlayer" + i);
        vBox.getChildren().remove(spinners.get(i - 1));

        // set nickname
        Text nicknameText = (Text) Gui.getStage().getScene().lookup("#nicknameText" + players.size());
        nicknameText.setText(player.getNickname());

        // set wizard image
        Image wizardImage = new Image("/images/wizards/" + player.getWizard().toString().toLowerCase() + ".png");
        ImageView wizardImageView = new ImageView(wizardImage);
        wizardImageView.setFitWidth(135.0);
        wizardImageView.setFitHeight(213.0);
        vBox.getChildren().add(wizardImageView);
    }

    public void setPlayersNumber(int maxPlayers) {
        if (players.size() != 0)
            return;

        for (int i = 1; i <= maxPlayers; i++) {
            System.out.println(i);
            VBox vBox = (VBox) Gui.getStage().getScene().lookup("#vBoxPlayer" + i);

            Image spinner = new Image("/images/spinner.gif");
            ImageView spinnerIV = new ImageView(spinner);
            spinnerIV.setFitHeight(70.0);
            spinnerIV.setFitWidth(70.0);
            spinners.add(spinnerIV);

            if (vBox != null)
                vBox.getChildren().add(spinnerIV);
        }
    }


    public void handleStartButton(ActionEvent actionEvent) {
        guiManager.showTable();
    }
}
