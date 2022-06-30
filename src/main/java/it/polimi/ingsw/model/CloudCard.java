package it.polimi.ingsw.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;

public class CloudCard implements Serializable {
    @Serial
    private static final long serialVersionUID = -6310738750800145727L;
    private EnumMap<Color, Integer> students;
    private transient final Game currentGame;

    public CloudCard(Game currentGame)
    {
        students=new EnumMap<>(Color.class);

        Arrays.stream(Color.values()).forEach(c -> students.putIfAbsent(c, 0));

        this.currentGame=currentGame;
    }

    public EnumMap<Color, Integer> getStudents()
    {
        return students;
    }

    /**
     * Transfers students from cloud card to current player's entrance
     */
    public void transferStudents()
    {
        for(Color color : students.keySet())
        {
            Player currentPlayer = currentGame.getCurrentPlayerInstance();
            SchoolDashboard schoolDashboard = currentPlayer.getSchoolDashboard();
            int previousValue = schoolDashboard.getEntrance().getOrDefault(color, 0);
            schoolDashboard.getEntrance().put(color, previousValue + students.get(color));
        }
        students.clear();
    }

    /**
     * Fill the cloud card with a number of students proportional to number of players
     */
    public void fill() {
        this.students = currentGame.extractFromBag(currentGame.getPlayers().size() + 1);
    }

}


