package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.EnumMap;

public class CloudCard {
    private EnumMap<Color, Integer> students;
    private Game currentGame;

    public CloudCard(Game currentGame)
    {
        students=new EnumMap<>(Color.class);

        students.putIfAbsent(Color.GREEN, 0);
        students.putIfAbsent(Color.RED, 0);
        students.putIfAbsent(Color.BLUE, 0);
        students.putIfAbsent(Color.YELLOW, 0);
        students.putIfAbsent(Color.PINK, 0);

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
            currentGame.getPlayers().get(currentGame.getCurrentPlayer()).getSchoolDashboard().getEntrance().put(color, currentGame.getPlayers().get(currentGame.getCurrentPlayer()).getSchoolDashboard().getEntrance().get(color)+ students.get(color));
        }
        students=new EnumMap<>(Color.class);
    }

    public void fill() {
        this.students = currentGame.extractFromBag(currentGame.getPlayers().size()+1);
    }

}


