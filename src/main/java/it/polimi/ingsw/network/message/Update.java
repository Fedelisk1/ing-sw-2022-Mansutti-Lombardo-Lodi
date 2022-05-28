package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.SchoolDashboard;
import it.polimi.ingsw.model.reduced.ReducedGame;

import java.io.Serial;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Update extends Message {
    @Serial
    private static final long serialVersionUID = -3874137112822061223L;
    private final ReducedGame reducedGame;

    public Update(ReducedGame reducedGame) {
        super(SERVER_NICKNAME, MessageType.UPDATE);

        this.reducedGame = reducedGame;
    }

    public ReducedGame getReducedGame() {
        return reducedGame;
    }
}
