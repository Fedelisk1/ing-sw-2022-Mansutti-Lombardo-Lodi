package it.polimi.ingsw.view.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventDispatchChain;
import javafx.event.EventDispatcher;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class Gui extends Application {
    private static Stage stage;
    private static ConnectToServerController connectToServerController;


    public static void main() {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Gui.stage = stage;

        FXMLLoader connectLoader = new FXMLLoader(getClass().getResource("/fxml/connectToServer.fxml"));
        Parent root = connectLoader.load();
        stage.setTitle("Eriantys");
        stage.setScene(new Scene(root));
        connectToServerController = connectLoader.getController();

        //stage.setResizable(false);

        stage.show();
        GuiManager.getSemaphore().release();

/*
        try {
            GuiManager.queue.put("done");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
*/
        stage.setOnCloseRequest((WindowEvent t) -> {
            Platform.exit();
            System.exit(0);
        });


    }

    public static Stage getStage() {
        return stage;
    }

    public static ConnectToServerController getConnectToServerController() {
        return connectToServerController;
    }
}
