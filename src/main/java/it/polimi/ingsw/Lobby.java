package it.polimi.ingsw;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Game;

import java.net.Socket;
import java.util.ArrayList;

/**
 * Allocates connected clients.
 * When a client connects, if a game is available, it joins. Otherwise it creates a new onw.
 */
public class Lobby {
    ArrayList<GameController> games;

    public Lobby() {
        games = new ArrayList<>();
    }

    public synchronized void allocateClient(Socket client) {
        boolean isAvailableGame = false;
        GameController availableGame = null;

        for (GameController game : games) {
            // find available spot

        }

    }
}
