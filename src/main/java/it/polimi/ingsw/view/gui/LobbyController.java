package it.polimi.ingsw.view.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
    @FXML
    private Button startButton;
    private List<ReducedPlayer> players;
    private List<ImageView> spinners;
    private GuiManager guiManager;
    private int maxPlayers;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        players = new ArrayList<>();
        spinners = new ArrayList<>();

        startButton.setDisable(true);
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

        // when all the players have joined, enable the start button
        if (maxPlayers == players.size())
            startButton.setDisable(false);
    }

    public void setPlayersNumber(int maxPlayers) {
        if (players.size() != 0)
            return;

        this.maxPlayers = maxPlayers;

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
