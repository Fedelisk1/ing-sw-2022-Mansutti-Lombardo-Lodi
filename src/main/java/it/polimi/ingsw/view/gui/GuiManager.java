package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Wizard;
import it.polimi.ingsw.model.reduced.ReducedGame;
import it.polimi.ingsw.model.reduced.ReducedPlayer;
import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.observer.ViewObserver;
import it.polimi.ingsw.view.View;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

public class GuiManager extends ViewObservable implements View{
    private final Parent nicknameRoot;
    private final Parent gameInfoRoot;
    private final Parent wizardRoot;
    private final Parent lobbyRoot;
    private final Parent tableRoot;

    private final ConnectToServerController connectToServerController;
    private final NicknameController nicknameController;
    private final GameInfoController gameInfoController;
    private final WizardController wizardController;
    private final LobbyController lobbyController;
    private final TableController tableController;

    private static Semaphore semaphore;
    private String nickname;

    public GuiManager() {
        semaphore = new Semaphore(0);

        // wait for graphics to be initialized
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        FXMLLoader nicknameLoader = new FXMLLoader(getClass().getResource("/fxml/nickname.fxml"));
        FXMLLoader gameInfoLoader = new FXMLLoader(getClass().getResource("/fxml/gameInfo.fxml"));
        FXMLLoader wizardLoader = new FXMLLoader(getClass().getResource("/fxml/wizard.fxml"));
        FXMLLoader lobbyLoader = new FXMLLoader(getClass().getResource("/fxml/lobby.fxml"));
        FXMLLoader tableLoader = new FXMLLoader(getClass().getResource("/fxml/table.fxml"));

        try {
            nicknameRoot = nicknameLoader.load();
            gameInfoRoot = gameInfoLoader.load();
            wizardRoot = wizardLoader.load();
            lobbyRoot = lobbyLoader.load();
            tableRoot = tableLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        connectToServerController = Gui.getConnectToServerController();
        nicknameController = nicknameLoader.getController();
        gameInfoController = gameInfoLoader.getController();
        wizardController = wizardLoader.getController();
        lobbyController = lobbyLoader.getController();
        tableController = tableLoader.getController();

        gameInfoController.setGuiManager(this);
        lobbyController.setGuiManager(this);
        tableController.setGuiManager(this);

        Platform.setImplicitExit(false);
    }

    @Override
    public void addObserver(ViewObserver viewObserver) {
        super.addObserver(viewObserver);

        connectToServerController.addObserver(viewObserver);
        nicknameController.addObserver(viewObserver);
        wizardController.addObserver(viewObserver);
        gameInfoController.addObserver(viewObserver);
        tableController.addObserver(viewObserver);
    }

    public static Semaphore getSemaphore() {
        return semaphore;
    }

    @Override
    public void showConnectionOutcome(boolean connectionOk) {
        if (connectionOk) {
            Platform.runLater(() -> Gui.getStage().setScene(new Scene(nicknameRoot)));
        } else {
            connectToServerController.onError();
        }
    }

    @Override
    public void showLoginOutcome(boolean userNameAvailable, boolean newGame, String nickname, List<Wizard> availableWizards) {
        if (userNameAvailable) {
            if (newGame) {
                // ask parameters for the new game
                Scene s = new Scene(gameInfoRoot);
                Platform.runLater(() -> Gui.getStage().setScene(s));
            } else {
                // ask wizard
                wizardController.setAvailableWizards(availableWizards);
                askWizard();
            }
            this.nickname = nickname;

            Platform.runLater(() -> Gui.getStage().setTitle("Eriantys - " + nickname));
        } else {
            nicknameController.onError();
        }
    }

    public void askWizard() {
        Platform.runLater(() -> Gui.getStage().setScene(new Scene(wizardRoot)));
    }

    @Override
    public void updateAvailableWizards(List<Wizard> availableWizards) {
        wizardController.setAvailableWizards(availableWizards);
    }

    @Override
    public void showWizardError(List<Wizard> availableWizards) {
        wizardController.setAvailableWizards(availableWizards);
    }

    @Override
    public void showLobby(List<ReducedPlayer> players, int playersNumber) {
        Platform.runLater(() -> {
            if(! Gui.getStage().getScene().getRoot().equals(lobbyRoot))
                Gui.getStage().setScene(new Scene(lobbyRoot));

            lobbyController.setPlayersNumber(playersNumber);
            lobbyController.setPlayers(players);
        });
    }

    @Override
    public void askAssistantCard(Map<Integer, Integer> hand, List<Integer> notPlayable) {
        tableController.askAssistantCard(hand, notPlayable);
    }

    @Override
    public void showPlayedAssistantCard(String player, int card) {
        tableController.showPlayedAssistant(player, card);
    }

    @Override
    public void update(ReducedGame game) {
        tableController.update(game);
    }

    @Override
    public void askActionPhase1(int count, int maxIsland, boolean expert) {
        Platform.runLater(() -> tableController.askActionPhase1(count));
    }

    @Override
    public void askActionPhase2(int maxMNStpes, boolean expert) {
        Platform.runLater(() -> tableController.askActionPhase2(maxMNStpes));
    }

    @Override
    public void askActionPhase3(List<Integer> alloweValues, boolean expert) {
        Platform.runLater(() -> tableController.askActionPhase3(alloweValues));
    }

    @Override
    public void askCCAllRemoveColorInput() {
        tableController.askCCAllRemoveColorInput();
    }

    @Override
    public void askCCBlockColorOnceInput() {
        tableController.askCCBlockColorOnceInput();
    }

    @Override
    public void askCCChoose1DiningRoomInput(List<Color> allowedValues) {
        tableController.askCCChoose1DiningRoomInput();
    }

    @Override
    public void askCCChoose1ToIslandInput(List<Color> allowedColors, int maxIsland) {
        tableController.askCCChoose1ToIslandInput();
    }

    @Override
    public void askCCChoose3ToEntranceInput(List<Color> allowedFromCC, List<Color> allowedFromEntrance, int inputCount) {
        tableController.askCCChoose3ToEntranceInput(inputCount);
    }

    @Override
    public void askCCChooseIslandInput(int maxIsland) {
        tableController.askCCChooseIslandInput();
    }

    @Override
    public void askCCExchange2StudentsInput(List<Color> entrance, List<Color> diningRoom, int inputCount) {
        tableController.askCCExchange2StudentsInput(inputCount);
    }

    @Override
    public void askCCNoEntryIslandInput(int maxIsland) {
        tableController.askCCNoEntryIslandInput();
    }

    @Override
    public void showStringMessage(String content) {
        tableController.showMessage(content);
    }

    @Override
    public void shutdown(String message) {
        Platform.runLater(() -> {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setHeaderText("Connection error");
            a.setContentText(message);

            a.setOnCloseRequest(de -> Platform.exit());
            a.show();
        });
    }

    @Override
    public void showServerUnreachable() {
        Platform.runLater(() -> {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setHeaderText("Server unreachable");
            a.setContentText("The connection with the server was lost");

            a.setOnCloseRequest(de -> Platform.exit());
            a.show();
        });
    }

    @Override
    public void showWinnerToOthers(String winnerNick) {
        Platform.runLater(() -> {tableController.showLoser(winnerNick); });
    }

    @Override
    public void notifyWinner() {
        Platform.runLater(() -> {tableController.showWinner(); });
    }

    public void showTable() {
        Platform.runLater(() -> {
            Gui.getStage().setScene(new Scene(tableRoot));
            Gui.getStage().setResizable(true);
            Gui.getStage().setMinHeight(950);
            Gui.getStage().setMinWidth(1350);
        });
    }

    public String getNickname() {
        return nickname;
    }
}