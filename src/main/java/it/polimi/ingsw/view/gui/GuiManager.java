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
    /**
     * Displays appropriate message for connection to server.
     *
     * @param connectionOk true if connection was successful.
     */
    @Override
    public void showConnectionOutcome(boolean connectionOk) {
        if (connectionOk) {
            Platform.runLater(() -> Gui.getStage().setScene(new Scene(nicknameRoot)));
        } else {
            connectToServerController.onError();
        }
    }
    /**
     * Displays the outcome of login.
     * @param userNameAvailable true if the chosen nickname is available.
     * @param newGame true if the client is going to start a new game.
     * @param nickname nickname of the user.
     */
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

    /**
     * At the beginning of the game it asks you to select a wizard
     */
    public void askWizard() {
        Platform.runLater(() -> Gui.getStage().setScene(new Scene(wizardRoot)));
    }

    /**
     * After that one player has chosen the wizard, he updates the list of those available
     * @param availableWizards list of available wizards
     */
    @Override
    public void updateAvailableWizards(List<Wizard> availableWizards) {
        wizardController.setAvailableWizards(availableWizards);
    }
    /**
     * When a player chooses a wizard not present in the list of available
     * @param availableWizards list of available wizard
     */
    @Override
    public void showWizardError(List<Wizard> availableWizards) {
        wizardController.setAvailableWizards(availableWizards);
    }
    /**
     * Displays the lobby status to the user.
     *
     * @param players players already in the lobby.
     * @param playersNumber maximum number of players of the lobby.
     */
    @Override
    public void showLobby(List<ReducedPlayer> players, int playersNumber) {
        Platform.runLater(() -> {
            if(! Gui.getStage().getScene().getRoot().equals(lobbyRoot))
                Gui.getStage().setScene(new Scene(lobbyRoot));

            lobbyController.setPlayersNumber(playersNumber);
            lobbyController.setPlayers(players);
        });
    }
    /**
     * Displays the playable assistant card and asks to choose one
     * @param hand all assistant card in the player's hand
     * @param notPlayable list of not playable cards
     */
    @Override
    public void askAssistantCard(Map<Integer, Integer> hand, List<Integer> notPlayable) {
        tableController.askAssistantCard(hand, notPlayable);
    }
    /**
     * Displays to other player that current player has chosen an assistant card
     * @param player who chose the card
     * @param card number of card
     */
    @Override
    public void showPlayedAssistantCard(String player, int card) {
        tableController.showPlayedAssistant(player, card);
    }
    /**
     * Whenever is called update for all player: the island, every school dashboard, cloud tiles with students/professors/towers/mother nature.
     * If the expert mode is active it displays coins and the three character card
     * @param game the current game
     */
    @Override
    public void update(ReducedGame game) {
        tableController.update(game);
    }
    /**
     * Displays the request of action phase 1 and asks where do you want to move the student. It is called a number of times equals to number of players plus one.
     * If the expert mode is active accept the request of playing a character card
     * @param count number of iteration
     * @param maxIsland number of island
     * @param expert true if the expert mode is active
     */
    @Override
    public void askActionPhase1(int count, int maxIsland, boolean expert) {
        Platform.runLater(() -> tableController.askActionPhase1(count));
    }
    /**
     * Displays the request of action phase 2 and asks how many steps you want to move mother nature.
     * If the expert mode is active accept the request of playing a character card
     * @param maxMNStpes number of steps granted by the assistant card played
     * @param expert if the expert mode is active
     */
    @Override
    public void askActionPhase2(int maxMNStpes, boolean expert) {
        Platform.runLater(() -> tableController.askActionPhase2(maxMNStpes));
    }
    /**
     * Displays the request of action phase 3 and asks a cloud's number to refill the entrance.
     * If the expert mode is active accept the request of playing a character card
     * @param alloweValues list of cloud's number fill of students
     * @param expert if the expert mode is active
     */
    @Override
    public void askActionPhase3(List<Integer> alloweValues, boolean expert) {
        Platform.runLater(() -> tableController.askActionPhase3(alloweValues));
    }
    /**
     * Asks color for activation of CC
     */
    @Override
    public void askCCAllRemoveColorInput() {
        tableController.askCCAllRemoveColorInput();
    }
    /**
     * Asks color for activation of CC
     */
    @Override
    public void askCCBlockColorOnceInput() {
        tableController.askCCBlockColorOnceInput();
    }
    /**
     * Asks color for activation of CC
     * @param allowedValues list of allowed color
     */
    @Override
    public void askCCChoose1DiningRoomInput(List<Color> allowedValues) {
        tableController.askCCChoose1DiningRoomInput();
    }
    /**
     * Asks color for activation of CC
     * @param allowedColors list of allowed color
     * @param maxIsland number of island
     */
    @Override
    public void askCCChoose1ToIslandInput(List<Color> allowedColors, int maxIsland) {
        tableController.askCCChoose1ToIslandInput();
    }
    /**
     * Asks color for activation of CC showing of allowed color
     * @param allowedFromCC list of allowed color from CC
     * @param allowedFromEntrance list of allowed color from entrance
     * @param inputCount number of iteration
     */
    @Override
    public void askCCChoose3ToEntranceInput(List<Color> allowedFromCC, List<Color> allowedFromEntrance, int inputCount) {
        tableController.askCCChoose3ToEntranceInput(inputCount);
    }

    /**
     * Asks number of island on which apply the effect of CC
     * @param maxIsland number of island
     */
    @Override
    public void askCCChooseIslandInput(int maxIsland) {
        tableController.askCCChooseIslandInput();
    }
    /**
     * Asks color for activation of CC showing of allowed color
     * @param entrance list of allowed entrance's color
     * @param diningRoom list of allowed dining room's color
     * @param inputCount number of iteration
     */
    @Override
    public void askCCExchange2StudentsInput(List<Color> entrance, List<Color> diningRoom, int inputCount) {
        tableController.askCCExchange2StudentsInput(inputCount);
    }
    /**
     * Asks number of island on which apply the effect of CC
     * @param maxIsland number of island
     */
    @Override
    public void askCCNoEntryIslandInput(int maxIsland) {
        tableController.askCCNoEntryIslandInput();
    }

    /**
     * Displays a string message
     * @param content
     */
    @Override
    public void showStringMessage(String content) {
        tableController.showMessage(content);
    }

    /**
     * Closes the game window when there is a connection error
     * @param message this is a string
     */
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

    /**
     * Shows a string when the connection has been lost
     */
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

    /**
     * When one player wins the game he shows to others the winner's name
     * @param winnerNick name of the winner
     */
    @Override
    public void showWinnerToOthers(String winnerNick) {
        Platform.runLater(() -> {tableController.showLoser(winnerNick); });
    }

    /**
     * Notify the winner
     */
    @Override
    public void notifyWinner() {
        Platform.runLater(() -> {tableController.showWinner(); });
    }

    /**
     * Show off gui
     */
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