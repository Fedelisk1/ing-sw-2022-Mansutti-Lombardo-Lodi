package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.MissingStudentException;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.charactercards.*;
import it.polimi.ingsw.view.VirtualView;

import java.util.EnumMap;

public class Action1State implements GameState{
    private final GameController gameController;
    private final Game game;
    private int movesCount;

    public Action1State(GameController gameController)
    {
        movesCount = 1;
        this.gameController=gameController;
        game= gameController.getGame();
    }

    /**
     * Performs action1.
     * @param color color of the student to move
     * @param islandNumber 1-indexed index of the destination island. If negative, the student will be moved to the dining room.
     */
    private void action1(Color color, int islandNumber) {
        String currentPlayer = game.getCurrentPlayerNick();
        VirtualView virtualView = gameController.getCurrentPlayerView();

        try {
            if (islandNumber < 0)
                game.getPlayers().get(game.getCurrentPlayer()).getSchoolDashboard().moveStudentToDiningRoom(color);
            else
                game.getCurrentPlayerInstance().getSchoolDashboard().moveToIslandGroup(color, islandNumber - 1);

            gameController.updateViews();
            //gameController.viewsExceptCurrentPlayer().forEach(v -> v.showStringMessage(currentPlayer + " is playing (action phase 1) ..."));

            //if there are 2 players, after 3 times the move method is called, with 3 players 4 times.
            if(movesCount == game.getMaxPlayers() + 1) {
                gameController.changeState(new Action2State(gameController));
                gameController.askActionPhase2();
            } else {
                movesCount++;
                //virtualView.askActionPhase1(movesCount, game.getIslands().size());
                gameController.askActionPhase1();
            }

        } catch (MissingStudentException e) {
            virtualView.showStringMessage("Chosen color is not available in your entrance!");
            virtualView.askActionPhase1(movesCount, game.getIslands().size(), game.isExpertMode());
        }
    }

    /**
     * moves a student of a specified color to a specified island
     * @param color color of the student to move
     * @param islandNumber 1-indexed island number
     */
    @Override
    public void action1Island(Color color, int islandNumber) {
        action1(color, islandNumber);
    }

    /**
     * moves a student of a specified color to a specified dining room
     * @param color color of the student to move
     */
    @Override
    public void action1DiningRoom(Color color) {
        action1(color, -1);
    }

    @Override
    public void ccAllRemoveColor(Color color)
    {
        AllRemoveColor chosenCard = (AllRemoveColor) game.getCharacterCard(CharacterCardType.ALL_REMOVE_COLOR);
        chosenCard.doEffect(color);
    }

    public int getMovesCount() {
        return movesCount;
    }

    //    @Override
//    public void ccBlockColorOnce(Color color)
//    {
//        BlockColorOnce chosenCard = (BlockColorOnce) game.getCharacterCards().get(cardPosition);
//        chosenCard.doEffect(color);
//    }
//    @Override
//    public void ccBlockTower()
//    {
//        BlockTower chosenCard = (BlockTower) game.getCharacterCards().get(cardPosition);
//        chosenCard.doEffect();
//    }
//    @Override
//    public void ccChoose1DiningRoom(Color color)
//    {
//        Choose1DiningRoom chosenCard = (Choose1DiningRoom) game.getCharacterCards().get(cardPosition);
//        chosenCard.doEffect(color);
//    }
//    @Override
//    public void ccChoose1ToIsland(Color color, int islandNumber)
//    {
//        Choose1ToIsland chosenCard = (Choose1ToIsland) game.getCharacterCards().get(cardPosition);
//        chosenCard.doEffect(color, islandNumber);
//    }
//    @Override
//    public void ccChoose3ToEntrance(EnumMap<Color,Integer> chosenFromCard , EnumMap<Color,Integer> chosenFromEntrance, int cardPosition)
//    {
//        Choose3toEntrance chosenCard = (Choose3toEntrance) game.getCharacterCards().get(cardPosition);
//        try {
//            chosenCard.doEffect(chosenFromCard,chosenFromEntrance);
//        } catch (MissingStudentException e) {
//            throw new RuntimeException(e);
//        }
//    }
//    @Override
//    public void ccChooseIsland(int islandNumber)
//    {
//        ChooseIsland chosenCard = (ChooseIsland) game.getCharacterCards().get(cardPosition);
//        chosenCard.doEffect(islandNumber);
//    }
//    @Override
//    public void ccExchange2Students(EnumMap<Color,Integer> chosenFromEntrance,EnumMap<Color,Integer> chosenFromDiningRoom)
//    {
//        Exchange2Students chosenCard = (Exchange2Students) game.getCharacterCards().get(cardPosition);
//        try {
//            chosenCard.doEffect(chosenFromEntrance,chosenFromDiningRoom);
//        } catch (MissingStudentException e) {
//            throw new RuntimeException(e);
//        }
//    }
//    @Override
//    public void ccNoEntryIsland(int islandNumber,int cardPosition)
//    {
//        NoEntryIsland chosenCard = (NoEntryIsland) game.getCharacterCards().get(cardPosition);
//        chosenCard.doEffect(islandNumber);
//    }
//    @Override
//    public void ccPlus2Influence(int cardPosition)
//    {
//        Plus2Influence chosenCard = (Plus2Influence) game.getCharacterCards().get(cardPosition);
//        chosenCard.doEffect();
//    }
//    @Override
//    public void ccTempControlProf(int cardPosition)
//    {
//        TempControlProf chosenCard = (TempControlProf) game.getCharacterCards().get(cardPosition);
//        chosenCard.doEffect();
//    }
//    @Override
//    public void ccTwoAdditionalMoves(int cardPosition)
//    {
//        TwoAdditionalMoves chosenCard = (TwoAdditionalMoves) game.getCharacterCards().get(cardPosition);
//        chosenCard.doEffect();
//    }

    @Override
    public void action2(int steps) {

    }

    @Override
    public void action3(int cloudCard) {

    }

    @Override
    public void endPlayerTurn() {

    }


    @Override
    public void startGame() {

    }

    @Override
    public void planning1() {

    }

    @Override
    public void planning2(int chosenCard) {

    }
}
