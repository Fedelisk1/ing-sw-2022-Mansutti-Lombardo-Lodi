package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.Player;

import java.io.Serial;

public class PlayCharacterCard extends Message {

    @Serial
    private static final long serialVersionUID = -5981604812491803304L;
    private final int chosenCard;

    public PlayCharacterCard(String nickname, int chosenCard) {
        super(nickname, MessageType.PLAY_CHARACTER_CARD);

        this.chosenCard = chosenCard;
    }

    public int getChosenCard() {
        return chosenCard;
    }
}
