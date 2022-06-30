package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Game;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.network.ClientHandler;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.VirtualView;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Action2StateTest {
    GameController gameController;
    ClientController clientController;
    Socket socket1;
    Socket socket2;
    ServerSocket serverSocket;

    class TestVirtualView extends VirtualView implements View {
        private String nickname;
        private int askAP1Counter = 0;

        public TestVirtualView(ClientHandler clientHandler, String nickname) {
            super(clientHandler);
            this.nickname = nickname;
        }

    }


    //setsup client side (server needs to be run before tests are run)
    @BeforeEach
    public void setUpClient() throws IOException {
        gameController = new GameController(2, true, 0);
        Map<String, GameController> nickControllerMap = Collections.synchronizedMap(new HashMap<>());

        String nick1 = "fede";
        String nick2 = "chiara";


        socket1 = new Socket(InetAddress.getLocalHost(),12345);
        socket2 = new Socket(InetAddress.getLocalHost(),12345);

        TestVirtualView view1 = new TestVirtualView(new ClientHandler(socket1, nickControllerMap), nick1);
        clientController = new ClientController(view1);

        TestVirtualView view2 = new TestVirtualView(new ClientHandler(socket2, nickControllerMap), nick2);
        clientController = new ClientController(view2);

        gameController.addPlayer(nick1, view1);
        gameController.addPlayer(nick2, view2);

        gameController.changeState(new Action2State(gameController));

    }
    @BeforeEach
    public void setUpServer() throws IOException {
        serverSocket = new ServerSocket(12345);
    }


    /**
     * ensures an unoccupied island is occupied correctly
     * @throws IOException
     */
    @Test
    void action2NoOccupation() throws IOException {
        Game game = gameController.getGame();

        game.getCurrentPlayerInstance().getSchoolDashboard().addStudentToDiningRoom(Color.RED);
        game.getIslands().get(1).getStudents().clear();
        game.getIslands().get(1).addStudents(Color.RED);
        gameController.getState().action2(1);


        Assertions.assertEquals(1,game.getCurrentPlayerInstance().getSchoolDashboard().getDiningRoom(Color.RED));
        Assertions.assertEquals(1,game.getIslands().get(1).getStudents(Color.RED));
        Assertions.assertEquals(game.getCurrentPlayerInstance(),game.getIslands().get(1).getOccupiedBy());
        Assertions.assertEquals(7,game.getCurrentPlayerInstance().getSchoolDashboard().getTowers());

        socket1.close();
        socket2.close();
    }


}