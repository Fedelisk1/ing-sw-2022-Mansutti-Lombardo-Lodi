package it.polimi.ingsw.controller;

import it.polimi.ingsw.network.ClientHandler;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.VirtualView;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameControllerTest {
    GameController gameController;
    class TestVirtialView extends VirtualView implements View {
        private String nickname;
        private int askAP1Counter = 0;

        public TestVirtialView(ClientHandler clientHandler, String nickname) {
            super(clientHandler);
            this.nickname = nickname;
        }

        @Override
        public void askAssistantCard(Map<Integer, Integer> hand, List<Integer> notPlayable) {
            switch (nickname) {
                case "1" -> gameController.onMessageArrived(new PlayCharacterCard("1", 1));
                case "2" -> gameController.onMessageArrived(new PlayCharacterCard("2", 6));
                case "3" -> gameController.onMessageArrived(new PlayCharacterCard("3", 4));
            }
        }

        public void askActionPhase1(int count, int maxIsland, boolean expert) {
            askAP1Counter++;
            if (count == 1) {
                if (askAP1Counter == 1)
                    assertEquals(nickname, "1");
                else if (askAP1Counter == 2)
                    assertEquals(nickname, "3");
                else if (askAP1Counter == 3)
                    assertEquals(nickname, "2");
            }
        }
    }

    @Test
    public void test() {

        gameController = new GameController(3, true, 0);

        String nick1 = "1";
        String nick2 = "2";
        String nick3 = "3";

        TestVirtialView view1 = new TestVirtialView(null, nick1);
        TestVirtialView view2 = new TestVirtialView(null, nick2);
        TestVirtialView view3 = new TestVirtialView(null, nick3);

        gameController.addPlayer(nick1, view1);
        gameController.addPlayer(nick2, view2);
        gameController.addPlayer(nick3, view3);

    }
}