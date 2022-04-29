package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.InputMismatchException;

public class SchoolDashboard {
    private ArrayList<Color> professors;
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
        if(n<0 || towers+n>8) throw new IllegalArgumentException("tower number not valid");
        towers = towers + n;
    }

    public void removeTowers(int n)
    {
        if(n<0 || towers-n<0) throw new IllegalArgumentException("tower number not valid");
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

    /**
     * removes a student from the entrance
     * @param color
     * @throws IllegalArgumentException
     * @throws NullPointerException
     */
    public void removeStudentFromEntrance(Color color) throws NullPointerException
    {
        if(entrance.get(color)==0) throw new IllegalArgumentException("no students of that color");
        entrance.put(color,entrance.get(color)-1);
    }

    /**
     * adds a student to the entrance
     * @param color
     * @throws NullPointerException
     */
    public void addStudentToEntrance(Color color) throws NullPointerException
    {
        entrance.put(color,entrance.get(color)+1);
    }


    public EnumMap<Color, Integer> getDiningRoom()
    {
        return diningRoom;
    }

    /**
     *moves student from entrance to dining room
     * @param color
     */
    public void moveStudentToDiningRoom(Color color)
    {
        try
        {
            removeStudentFromEntrance(color);
            addStudentToDiningRoom(color);
        }
        catch(IllegalArgumentException e)
        {
            System.out.println("Remove student failed");
        }
        catch(NullPointerException e)
        {
            System.out.println("Null parameter");
        }
        catch(Exception e)
        {
            System.out.println("Unknown error");
        }

    }

    /**
     * adds a student to the Dining Room
     * @param color
     * @throws NullPointerException
     */
    public void addStudentToDiningRoom(Color color) throws NullPointerException
    {
        diningRoom.put(color,diningRoom.get(color)+1);
    }

    /**
     *Adds a professor to the player's school dashboard
     * @param color
     * @throws IllegalArgumentException
     */
    public void addProfessor(Color color)
    {
        if(professors.contains(color)) throw new IllegalArgumentException("There is already a color of that professor");
        professors.add(color);
    }

}
