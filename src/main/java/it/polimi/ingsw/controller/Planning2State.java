package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;

public class Planning2State implements GameState{

    private GameController gameController;
    private Game game;
    int count;

    public Planning2State(GameController gameController)
    {
        this.gameController=gameController;
        this.game=gameController.getGame();
        count = 0;
    }

    /**
     * executes planning phase 2, planning phase 2 must be called a number of times equal to player number to change to next state
     * @param chosenCard by the player
     */
    @Override
    public void planning2(int chosenCard)
    {
        //TODO: check discard pile for duplicate cards


        //the current player chooses the assistant card, then the current player is changed to the next
        game.getPlayers().get(game.getCurrentPlayer()).chooseAssistantCard(chosenCard);
        if(game.getCurrentPlayer()+1==game.getPlayers().size())
        {
            game.setCurrentPlayer(0);

        }
        else
        {
            game.setCurrentPlayer(game.getCurrentPlayer()+1);
        }
        count++;

        //if all players have chosen an assistant card, it sets the current player to the player that has chosen the card
        //with the least value and changes state
        if(count==game.getPlayers().size())
        {
            int position=0;
            for(int i=1;i<game.getPlayers().size();i++)
            {
                if(game.getPlayers().get(i).getCardValue()<game.getPlayers().get(position).getCardValue())
                {
                    position=i;
                }
            }
            game.setCurrentPlayer(position);
            gameController.changeState(new Action1State(gameController));
        }


    }

    @Override
    public void action1() {

    }


    @Override
    public void startGame() {
        //should never happen
    }

    @Override
    public void planning1() {
        //should never happen
    }
}
