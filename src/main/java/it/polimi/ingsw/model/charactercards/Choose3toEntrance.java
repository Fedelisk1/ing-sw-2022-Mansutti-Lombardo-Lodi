package it.polimi.ingsw.model.charactercards;

import it.polimi.ingsw.exceptions.MissingStudentException;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Game;

import java.util.EnumMap;

public class Choose3toEntrance extends CharacterCard {

    public Choose3toEntrance(Game currentGame) {
        this.currentGame = currentGame;
        students = new EnumMap<>(Color.class);
        cost = 1;

        type = CharacterCardType.CHOOSE_3_TO_ENTRANCE;
        name = "Choose3toEntrance";
        description = "You may take up to 3 Students from this card and replace them with the same number of Students from your Entrance";

        init();
    }

    public void init() {
        students = currentGame.extractFromBag(6);
    }


    /**
     * Check that the number of selected students is the same and then move the students from the card to the entrance
     *
     * @param chosenFromCard     represent the students chosen from the card
     * @param chosenFromEntrance represent the students chosen from the entrance
     */

    public void doEffect(EnumMap<Color, Integer> chosenFromCard, EnumMap<Color, Integer> chosenFromEntrance) {
        currentGame.getCurrentPlayerInstance().removeCoins(cost);

        cost = 2;
        EnumMap<Color, Integer> support1 = chosenFromCard.clone();
        EnumMap<Color, Integer> support2 = chosenFromEntrance.clone();

        if (totalNumberOfStudents(support1) != totalNumberOfStudents(support2))
            throw new IllegalArgumentException("different number of selected students");


        for (Color c : chosenFromCard.keySet()) {
            while (chosenFromCard.get(c) > 0) {

                chosenFromCard.put(c, chosenFromCard.get(c) - 1);
                students.put(c, students.get(c) - 1);
                currentGame.getPlayers().get(currentGame.getCurrentPlayer()).getSchoolDashboard().addStudentToEntrance(c);

            }
        }

        for (Color c : chosenFromEntrance.keySet()) {
            while (chosenFromEntrance.get(c) > 0) {

                chosenFromEntrance.put(c, chosenFromEntrance.get(c) - 1);
                students.merge(c, 1, Integer::sum);
                currentGame.getCurrentPlayerInstance().getSchoolDashboard().removeStudentFromEntrance(c);

            }
        }
    }

    public void doPartialEffect(Color fromCard, Color fromEntrance) {
        // remove from the card
        students.merge(fromCard, -1, Integer::sum);

        // add to the entrance
        currentGame.getCurrentPlayerInstance().getSchoolDashboard().getEntrance().merge(fromCard, 1, Integer::sum);

        // remove from entrance
        currentGame.getCurrentPlayerInstance().getSchoolDashboard().getEntrance().merge(fromEntrance, -1, Integer::sum);

        // add to card
        students.merge(fromEntrance, 1, Integer::sum);
    }

    public EnumMap<Color, Integer> getStudents() {
        return students;
    }

    /**
     * Calculate the total number of students
     * !! it empties the enumMap
     *
     * @param e is the EnumMap
     * @return total number of students
     */
    public int totalNumberOfStudents(EnumMap<Color, Integer> e) {
        int sum = 0;

        for (Color c : Color.values()) {
            if(e.containsKey(c)) {
                while (e.get(c) > 0) {
                    e.put(c, e.get(c) - 1);
                    sum++;
                }
            }
        }
        return sum;
    }
    public void clear(){
        for(Color c: Color.values()){
            if(students.containsKey(c))
            while(students.get(c)>0)
                students.put(c,0);
            else students.put(c,0);
        }
    }

}
