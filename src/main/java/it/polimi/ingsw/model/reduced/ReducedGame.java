package it.polimi.ingsw.model.reduced;

import it.polimi.ingsw.model.CloudCard;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

public class ReducedGame implements Serializable {
    @Serial
    private static final long serialVersionUID = -9013518094037129387L;
    private List<ReducedIsland> islands;
    private Map<String, ReducedSchoolDashboard> schoolDashboards;
    private List<CloudCard> cloudCards;
    /**
     * Maps nickname with owned coins.
     */
    private Map<String, Integer> coins;

    public ReducedGame(Game game) {
        islands = new ArrayList<>();
        schoolDashboards = new HashMap<>();
        cloudCards = new ArrayList<>();
        coins = new HashMap<>();

        game.getIslands().forEach(islandGroup -> islands.add(new ReducedIsland(game, islandGroup)));

        for (Player p : game.getPlayers()) {
            schoolDashboards.put(p.getNickname(), new ReducedSchoolDashboard(p.getSchoolDashboard()));
            coins.put(p.getNickname(), p.getCoins());
        }

        cloudCards.addAll(game.getCloudCards());
    }

    public List<ReducedIsland> getIslands() {
        return islands;
    }

    public Map<String, ReducedSchoolDashboard> getSchoolDashboards() {
        return schoolDashboards;
    }

    public List<CloudCard> getCloudCards() {
        return cloudCards;
    }

    public Map<String, Integer> getCoins() {
        return coins;
    }
}
