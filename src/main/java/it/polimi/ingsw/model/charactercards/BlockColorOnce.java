package it.polimi.ingsw.model.charactercards;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Game;

public class BlockColorOnce extends CharacterCard {
    public BlockColorOnce(Game currentGame) {
        this.currentGame = currentGame;
        cost = 3;

        type = CharacterCardType.BLOCK_COLOR_ONCE;
        name = "BlockColorOnce";
        description = "Choose a color of Student: during the influence calculation this turn, that color adds no influence";
    }

    /**
     * Set true the boolean blockColorOnce of IslandGroup and set the color to block
     *
     * @param c is the color to block
     */
    public void doEffect(Color c) {
        currentGame.getCurrentPlayerInstance().setCoins(currentGame.getPlayers().get(currentGame.getCurrentPlayer()).getCoins() - cost);

        cost = 4;
        currentGame.setBlockColorOnce(true);
        currentGame.setBlockedColor(c);
    }
}
