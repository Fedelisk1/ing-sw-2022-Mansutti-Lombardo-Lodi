package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class TurnHandler {
    private GamePhase gamePhase;
    private Game game;
    /**
     * Mapping number of players with the quantity of moved students to islands or dining room in action phase 1
     */
    private Map<Integer, Integer> actionPhase1MoveQty;

    public TurnHandler(Game game) {
        this.game = game;
        gamePhase = GamePhase.PLANNING;

        actionPhase1MoveQty = new HashMap<>();
        actionPhase1MoveQty.put(2, 3);
        actionPhase1MoveQty.put(3, 4);
    }

    public void planningPhase1() {
        for (CloudCard cc : game.getCloudCards()) {
            cc.fill();
        }
    }

    public void planningPhase2() {
        int chosenAssistantCard = 1;
        game.getCurrentPlayerInstance().chooseAssistantCard(chosenAssistantCard);
    }

    /**
     * Manages action phase 1
     */
    public void actionPhase1() {
        for (int i = 0; i < actionPhase1MoveQty.get(game.getPlayers().size()); i++) {
            //TODO replace with msg retrieval
            String destination = "island";
            Color color = Color.BLUE;
            int islandIndex = 2;


            if (destination.equals("island")) {
                game.getCurrentPlayerInstance().moveOneOfThreeToIsland(color, islandIndex);
            } else if(destination.equals("diningRoom")) {
                game.getCurrentPlayerInstance().moveOneOfThreeToDiningRoom(color);
            }
        }
    }

    /**
     * Manages action phase part 2: move mother nature
     */
    public void actionPhase2() {
        //TODO: replace with msg retrieval
        int steps = 3;

        game.moveMotherNature(steps);

        IslandGroup mnIsland = game.getMotherNatureIsland();

        // new owner for mnIsland
        Player owner = game.playerWithHigherInfluence(mnIsland);
        mnIsland.setOccupiedBy(owner);

        //TODO: merge islands
    }

    public void actionPhase3() {

    }
}
