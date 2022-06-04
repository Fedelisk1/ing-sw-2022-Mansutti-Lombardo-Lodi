package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.Client;
import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.reduced.ReducedGame;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.observer.ViewObserver;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.cli.Cli;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class GuiManager extends ViewObservable implements View{
    private final Parent nicknameRoot;

    private final ConnectToServerController connectToServerController;
    private final NicknameController nicknameController;

    private static Semaphore semaphore;

    public GuiManager() {
        semaphore = new Semaphore(0);

        // wait for graphics to be initialized
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        FXMLLoader nicknameLoader = new FXMLLoader(getClass().getResource("/fxml/nickname.fxml"));

        try {
            nicknameRoot = nicknameLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        connectToServerController = Gui.getConnectToServerController();
        nicknameController = nicknameLoader.getController();
    }

    @Override
    public void addObserver(ViewObserver viewObserver) {
        super.addObserver(viewObserver);

        connectToServerController.addObserver(viewObserver);
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
    public void showLoginOutcome(boolean userNameAvailable, boolean newGame, String nickname) {

    }

    @Override
    public void nicknameInput() {

    }

    @Override
    public void showLobby(List<String> nicknames, int players) {

    }

    @Override
    public void askAssistantCard(Map<Integer, Integer> hand, List<Integer> notPlayable) {

    }

    @Override
    public void showPlayedAssistantCard(String player, int card) {

    }

    @Override
    public void update(ReducedGame game) {

    }

    @Override
    public void askActionPhase1(int count, int maxIsland) {

    }

    @Override
    public void askActionPhase2(int maxMNStpes) {

    }

    @Override
    public void askActionPhase3(List<Integer> alloweValues) {

    }

    @Override
    public void askCCAllRemoveColorInput() {

    }

    @Override
    public void askCCBlockColorOnceInput() {

    }

    @Override
    public void askCCChoose1DiningRoomInput(List<Color> allowedValues) {

    }

    @Override
    public void askCCChoose1ToIslandInput(List<Color> allowedColors, int maxIsland) {

    }

    @Override
    public void askCCChoose3ToEntranceInput(List<Color> allowedFromCC, List<Color> allowedFromEntrance) {

    }

    @Override
    public void askCCChooseIslandInput(int maxIsland) {

    }

    @Override
    public void askCCExchange2StudentsInput(List<Color> entrance, List<Color> diningRoom) {

    }

    @Override
    public void askCCNoEntryIslandInput(int maxIsland) {

    }

    @Override
    public void showStringMessage(String content) {

    }

    @Override
    public void shutdown(String message) {

    }

    @Override
    public void showServerUnreachable() {

    }


}
