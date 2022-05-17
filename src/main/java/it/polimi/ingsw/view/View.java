package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.GameController;

public class View {
    GameController gameController;
    public View()
    {

    }

    public void addListener(GameController gameController)
    {
        this.gameController=gameController;
    }
}
