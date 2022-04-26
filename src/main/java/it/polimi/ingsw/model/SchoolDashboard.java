package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.InputMismatchException;

public class SchoolDashboard {
    private ArrayList<Professor> professors;
    private int towers;
    private EnumMap<Color,Integer> entrance;
    private EnumMap<Color,Integer> diningRoom;

    /**le torri non vengono gestite tramite colore ma tramite proprietario**/
    SchoolDashboard()
    {
        professors= new ArrayList<>();
        towers = 8;
        entrance = new EnumMap<>(Color.class);
        diningRoom = new EnumMap<>(Color.class);

    }

    public void addTowers(int n)
    {
        if(n<0 || towers+n>8) throw new IllegalArgumentException("numero torri non valido");
        towers = towers + n;
    }

    public void removeTowers(int n) throws InputMismatchException
    {
        if(n<0 || towers-n<0) throw new IllegalArgumentException("numero torri non valido");
        towers = towers - n;
    }

    public int getTowers()
    {
        return towers;
    }

    public EnumMap<Color, Integer> getEntrance()
    {
        return entrance;
    }
    
    public EnumMap<Color, Integer> getDiningRoom()
    {
        return diningRoom;
    }

}
