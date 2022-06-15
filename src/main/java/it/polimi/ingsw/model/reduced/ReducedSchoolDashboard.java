package it.polimi.ingsw.model.reduced;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.SchoolDashboard;
import it.polimi.ingsw.model.TowerColor;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumMap;

public class ReducedSchoolDashboard implements Serializable {
    @Serial
    private static final long serialVersionUID = 5713815891839075525L;
    private ArrayList<Color> professors;
    private int towers;
    private EnumMap<Color,Integer> entrance;
    private EnumMap<Color,Integer> diningRoom;
    private TowerColor towerColor;

    public ReducedSchoolDashboard(Player player) {
        SchoolDashboard schoolDashboard = player.getSchoolDashboard();
        professors = schoolDashboard.getProfessors();
        towers = schoolDashboard.getTowers();
        entrance = schoolDashboard.getEntrance();
        diningRoom = schoolDashboard.getDiningRoom();
        towerColor = player.getTowerColor();
    }

    public ArrayList<Color> getProfessors() {
        return professors;
    }

    public int getTowers() {
        return towers;
    }

    public EnumMap<Color, Integer> getEntrance() {
        return entrance;
    }

    public EnumMap<Color, Integer> getDiningRoom() {
        return diningRoom;
    }

    public TowerColor getTowerColor() {
        return  towerColor;
    }
}
