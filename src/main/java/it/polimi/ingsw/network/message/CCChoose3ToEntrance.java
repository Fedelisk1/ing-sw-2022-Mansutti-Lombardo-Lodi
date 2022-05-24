package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.Color;

import java.util.EnumMap;

public class CCChoose3ToEntrance extends Message {
    private MessageType messageType;
    private String nickname;
    private EnumMap<Color, Integer> chosenFromCard;
    private EnumMap<Color, Integer> chosenFromEntrance;
    private int cardPosition;

    public CCChoose3ToEntrance(String nickname, MessageType messageType, EnumMap<Color, Integer> chosenFromCard, EnumMap<Color, Integer> chosenFromEntrance, int cardPosition){
        super(nickname, MessageType.CC_CHOOSE_3_TO_ENTRANCE);
        this.chosenFromCard=chosenFromCard;
        this.chosenFromEntrance=chosenFromEntrance;
        this.cardPosition=cardPosition;
    }

    public EnumMap<Color, Integer> getChosenFromCard() {
        return chosenFromCard;
    }

    public EnumMap<Color, Integer> getChosenFromEntrance() {
        return chosenFromEntrance;
    }

    public int getCardPosition() {
        return cardPosition;
    }
}
