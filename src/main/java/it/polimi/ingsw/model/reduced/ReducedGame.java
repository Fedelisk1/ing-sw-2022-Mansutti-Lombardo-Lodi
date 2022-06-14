package it.polimi.ingsw.model.reduced;

import it.polimi.ingsw.model.CloudCard;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.charactercards.CharacterCard;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

public class ReducedGame implements Serializable {
    @Serial
    private static final long serialVersionUID = -9013518094037129387L;
    private final List<ReducedIsland> islands;
    private final Map<String, ReducedSchoolDashboard> schoolDashboards;
    private final List<CloudCard> cloudCards;
    /** Maps nickname with owned coins. */
    private final Map<String, Integer> coins;
    private final boolean expert;
    private final List<ReducedCharacterCard> characterCards;
    private final Set<Color> unusedProfessors;


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

        characterCards = new ArrayList<>();

        expert = game.isExpertMode();
        if(expert)
            game.getCharacterCards().forEach(cc -> characterCards.add(new ReducedCharacterCard(cc)));

        unusedProfessors = new HashSet<>(game.getUnusedProfessors());
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

    public boolean isExpert() {
        return expert;
    }

    public List<ReducedCharacterCard> getCharacterCards() {
        return characterCards;
    }

    /**
     * Returns the index (0-indexed) of the island in which MN is currently into.
     * @return
     */
    public int getMNPosition() {
        return islands.indexOf(islands.stream().filter(ReducedIsland::isMotherNature).findFirst().get());
    }

    public Set<Color> getUnusedProfessors() {
        return unusedProfessors;
    }
}
