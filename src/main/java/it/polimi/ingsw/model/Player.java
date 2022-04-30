package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.EnumMap;

public class Player {
    private int coins;
    private Hand hand;
    private SchoolDashboard schoolDashboard;
    private ArrayList<AssistantCard> discardPile;
    private int playedPriority;
    private int maxPosition;
    private Game currentGame;

    /** each player starts with a hand of 10 assistant cards, 10 coins and a school dashboard. Each player is identified by his/her nickname**/
    public Player()
    {
        hand = new Hand();
        schoolDashboard = new SchoolDashboard();
        coins = 0;

    }

    public void setCurrentGame(Game game)
    {
        this.currentGame=game;
    }

    public int getPlayedPriority()
    {
        return playedPriority;
    }

    public int getMaxPosition() {
        return maxPosition;
    }

    //aggiunta io
    public void setMaxPosition(int maxPosition){
        this.maxPosition=maxPosition;
    }

    //aggiunta io
    public SchoolDashboard getSchoolDashboard(){return schoolDashboard; }


    public void playAssistantCard(int i) throws IndexOutOfBoundsException
    {

        maxPosition = hand.assistantCards.get(i).getMaxSteps();
        playedPriority = hand.assistantCards.get(i).getPriority();
        discardPile.add(hand.assistantCards.get(i));
        hand.assistantCards.remove(i);
    }

    public EnumMap<Color,Integer> chooseStudent(EnumMap<Color,Integer> in,Color c){
        EnumMap<Color,Integer> choosed= new EnumMap<>(Color.class);

        choosed.put(c,1);
        in.put(c,in.get(c)-1);

        return choosed;
    }

    public Color chooseStudent1(EnumMap<Color,Integer> in,Color c){
        in.put(c,in.get(c)-1);
        return c;
    }



}
