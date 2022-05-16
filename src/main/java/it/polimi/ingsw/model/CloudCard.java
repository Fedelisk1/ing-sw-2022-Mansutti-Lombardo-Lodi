package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.EnumMap;

public class CloudCard {
    private EnumMap<Color, Integer> students;
    private Game currentGame;
    private final int CLOUD_CARD_STUDENTS_2_PLAYERS = 3;
    private final int CLOUD_CARD_STUDENTS_3_PLAYERS = 4;

    public void setUp() {
        students = currentGame.extractFromBag(cardSize());

        students.putIfAbsent(Color.GREEN, 0);
        students.putIfAbsent(Color.RED, 0);
        students.putIfAbsent(Color.BLUE, 0);
        students.putIfAbsent(Color.YELLOW, 0);
        students.putIfAbsent(Color.PINK, 0);
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

    public void setCurrentGame(Game game) {
        this.currentGame=game;
    }

    public void fill() {
        this.students = currentGame.extractFromBag(cardSize());
    }

    private int cardSize() {
        int toExtract;
        if (currentGame.getPlayers().size() == 2)
            toExtract = CLOUD_CARD_STUDENTS_2_PLAYERS;
        else
            toExtract = CLOUD_CARD_STUDENTS_3_PLAYERS;

        return toExtract;
    }
}


