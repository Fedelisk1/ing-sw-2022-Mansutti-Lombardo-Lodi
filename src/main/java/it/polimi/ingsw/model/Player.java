package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.MissingStudentException;

import java.util.*;

public class Player {
    private int coins;
    private Hand hand;
    private String nickname;
    private final SchoolDashboard schoolDashboard;
    private ArrayList<AssistantCard> discardPile;
    private int cardValue;
    private int maxSteps;
    private Game currentGame;
    private int count;
    private Wizard wizard;
    private TowerColor towerColor;


    /** each player starts with a hand of 10 assistant cards, 10 coins and a school dashboard. Each player is identified by his/her nickname**/
    public Player(Game currentGame)
    {
        hand = new Hand();
        schoolDashboard = new SchoolDashboard();

        for(Color c: Color.values())
            schoolDashboard.getDiningRoom().put(c, 0);

        discardPile = new ArrayList<>();

        this.currentGame = currentGame;

        Set<TowerColor> unusedColors = currentGame.getUnusedTowers();
        unusedColors.stream().findFirst().ifPresent(tc -> towerColor = tc);
    }

    public TowerColor getTowerColor() {
        return towerColor;
    }

    public void setWizard(Wizard wizard) {
        this.wizard = wizard;
    }

    public Wizard getWizard() {
        return wizard;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void addCoins(){
        coins=getCoins()+1;
        currentGame.decreaseTotalCoins();
    }

    public int getCardValue()
    {
        return cardValue;
    }

    public int getMaxSteps() {
        return maxSteps;
    }

    public void setMaxSteps(int maxSteps){
        this.maxSteps=maxSteps;
    }

    public SchoolDashboard getSchoolDashboard(){return schoolDashboard; }


    /**
     * Player chooses the assistant card to play, setting the maximum position that mother nature can move and the priority. It gets placed in the discard pile.
     * @param chosenCard, card number chosenCard from the left in hand
     * @throws IndexOutOfBoundsException if chosenCard is outside the possible range of values
     */
    public void chooseAssistantCard(int chosenCard) throws IndexOutOfBoundsException {
        int chosenCardIndex = 0;
        int i = 0;
        for (AssistantCard assistantCard : getHand().getAssistantCards()) {
            if (assistantCard.getPriority() == chosenCard) {
                chosenCardIndex = i;
                break;
            }
            i++;
        }

        maxSteps = hand.getAssistantCards().get(chosenCardIndex).getMaxSteps();
        cardValue = hand.getAssistantCards().get(chosenCardIndex).getPriority();
        discardPile.add(hand.getAssistantCards().get(chosenCardIndex));
        hand.getAssistantCards().remove(chosenCardIndex);
    }


    public void chooseCloudCard(int i) {
        currentGame.getCloudCards().get(i).transferStudents();
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public void removeCoins(int coins) {
        this.coins -= coins;
    }

    public void moveOneOfThreeToIsland(Color color, int i) throws NullPointerException, MissingStudentException
    {
        try {
            if(count>=3)throw new IllegalStateException("limit of students moved reached for this turn");
            schoolDashboard.moveToIslandGroup(color, i);
        }
        catch (IndexOutOfBoundsException e)
        {
            System.out.println("Chosen island is outside the possible range of values");
        }
        finally{
            count++;
        }


    }

    public void resetCount()
    {
        count=0;
    }
    public void moveOneOfThreeToDiningRoom(Color color) throws NullPointerException, MissingStudentException
    {
        if(count==3)throw new IllegalStateException("limit of students moved reached for this turn");
        schoolDashboard.moveStudentToDiningRoom(color);
        count++;

    }
    public Hand getHand()
    {
        return hand;
    }
}