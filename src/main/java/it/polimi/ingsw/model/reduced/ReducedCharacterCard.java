package it.polimi.ingsw.model.reduced;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.charactercards.CharacterCard;

import java.io.Serial;
import java.io.Serializable;
import java.util.EnumMap;

public class ReducedCharacterCard implements Serializable {

    @Serial
    private static final long serialVersionUID = -6338397618973231010L;

    private final String name;
    private final String description;
    private final EnumMap<Color, Integer> students;
    private final int cost;


    public ReducedCharacterCard(CharacterCard characterCard) {
        if (characterCard.getStudents() != null)
            students = characterCard.getStudents();
        else
            students = new EnumMap<>(Color.class);

        name = characterCard.getName();
        description = characterCard.getDescription();
        cost = characterCard.getCost();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public EnumMap<Color, Integer> getStudents() {
        return students;
    }

    public int getCost() {
        return cost;
    }
}
