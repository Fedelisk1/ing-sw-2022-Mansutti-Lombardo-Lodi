package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.InputMismatchException;

public class SchoolDashboard {
    private ArrayList<Color> professors;
    private int towers;
    private EnumMap<Color,Integer> entrance;
    private EnumMap<Color,Integer> diningRoom;
    private Game currentGame;

    /**le torri non vengono gestite tramite colore ma tramite proprietario**/
    SchoolDashboard()
    {
        professors= new ArrayList<>();
        towers = 8;
        entrance = new EnumMap<>(Color.class);
        diningRoom = new EnumMap<>(Color.class);

    }

    public void setCurrentGame(Game currentGame) {
        this.currentGame = currentGame;
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
     * @param color student color
     * @throws IllegalArgumentException if there is no student of the chosen color in the entrance
     * @throws NullPointerException if color is null
     */
    public void removeStudentFromEntrance(Color color) throws NullPointerException
    {
        entrance.putIfAbsent(color, 0);
        if(entrance.get(color)==0) throw new IllegalArgumentException("no students of that color");
        entrance.put(color,entrance.get(color)-1);
    }

    /**
     * adds a student to the entrance
     * @param color student color
     * @throws NullPointerException if color is null
     */
    public void addStudentToEntrance(Color color) throws NullPointerException
    {
        entrance.putIfAbsent(color, 0);
        entrance.put(color,entrance.get(color)+1);
    }


    public EnumMap<Color, Integer> getDiningRoom()
    {
        return diningRoom;
    }

    /**
     *moves student from entrance to dining room
     * @param color student color
     * @throws NullPointerException if color is null
     * @throws IllegalArgumentException if there is no student of the chosen color in the entrance
     */
    public void moveStudentToDiningRoom(Color color) throws NullPointerException, IllegalArgumentException
    {
        removeStudentFromEntrance(color);
        addStudentToDiningRoom(color);
    }

    /**
     * adds a student to the Dining Room
     * @param color student color
     * @throws NullPointerException if color is null
     */
    public void addStudentToDiningRoom(Color color) throws NullPointerException
    {
        diningRoom.putIfAbsent(color, 0);
        diningRoom.put(color,diningRoom.get(color)+1);
    }

    public void removeStudentFromDiningRoom(Color color) throws NullPointerException{
        diningRoom.put(color,diningRoom.get(color)-1);
    }
    /**
     *Adds a professor to the player's school dashboard
     * @param color professor color
     * @throws IllegalArgumentException if there is already a professor of that color
     * @throws NullPointerException if color is null
     */
    public void addProfessor(Color color) throws NullPointerException
    {
        if(professors.contains(color)) throw new IllegalArgumentException("There is already a color of that professor");
        professors.add(color);
    }



    /**
     * removes a professor from the school dashboard
     * @param color professor color
     * @throws NullPointerException if color is null
     * @throws IllegalArgumentException if there is no professor of the chosen color
     */

    public void removeProfessor(Color color) throws NullPointerException
    {
        if(!professors.contains(color)) throw new IllegalArgumentException("There is no professor of such color");

    }

    /**
     *
     * @param color of the student to me moved
     * @param i islandGroup number
     * @throws NullPointerException if color is null
     * @throws IllegalArgumentException if there is no specified color in the entrance
     */
    public void moveToIslandGroup(Color color, int i) throws NullPointerException, IllegalArgumentException
    {
        removeStudentFromEntrance(color);
        currentGame.getIslands().get(i).addStudents(color);
    }

}
