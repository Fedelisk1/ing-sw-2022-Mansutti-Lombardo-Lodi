package it.polimi.ingsw.model;

public class AllRemoveColor extends CharacterCard {
    public AllRemoveColor() {
        cost = 3;
    }

    /**
     * For each player three students of color c are removed from the dining room, but if you have fewer pieces
     * you have to remove the ones you have.
     *
     * @param c is the color
     */
    public void doEffect(Color c) {
        currentGame.getPlayers().get(currentGame.getCurrentPlayer()).setCoins(currentGame.getPlayers().get(currentGame.getCurrentPlayer()).getCoins() - cost);

        cost = 4;

        for (Player p : currentGame.getPlayers()) {
            int numberOfStudents = p.getSchoolDashboard().getDiningRoom().get(c);

            if (numberOfStudents > 2) {
                for (int i = 0; i < 3; i++)
                    p.getSchoolDashboard().removeStudentFromDiningRoom(c);

            } else if (numberOfStudents == 2) {
                for (int i = 0; i < 2; i++)
                    p.getSchoolDashboard().removeStudentFromDiningRoom(c);
            } else if (numberOfStudents == 1)
                p.getSchoolDashboard().removeStudentFromDiningRoom(c);

        }

    }


}
