package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.InputMismatchException;

public class SchoolDashboard {
    private ArrayList<Professor> professors;
    private int towers;
    private EnumMap<Color,Integer> lounge;
    private EnumMap<Color,Integer> lobby;

    /**le torri non vengono gestite tramite colore ma tramite proprietario**/
    SchoolDashboard()
    {
        professors= new ArrayList<>();
        towers = 8;
        lounge = new EnumMap<>(Color.class);
        lobby = new EnumMap<>(Color.class);

    }

    public void addTowers(int n) throws InputMismatchException
    {
        if(n<0) throw new IllegalArgumentException();
        try{
            towers = towers + n;
        }
        catch(IllegalArgumentException e){
            System.out.println("torri negative");
        }
        catch(InputMismatchException e){
            System.out.println("torri non intere");
        }
    }

    public void removeTowers(int n) throws InputMismatchException
    {
        if(n<0) throw new IllegalArgumentException();
        try{
            towers = towers + n;
        }
        catch(IllegalArgumentException e){
            System.out.println("torri negative");
        }
        catch(InputMismatchException e){
            System.out.println("torri non intere");
        }

    }

}
