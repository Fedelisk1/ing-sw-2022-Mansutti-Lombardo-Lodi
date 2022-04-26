package it.polimi.ingsw.model;

import java.util.ArrayList;

public class Player {
    private String nickname;
    private int coins;
    private Hand hand;
    private SchoolDashboard schoolDashboard;

    public Player()
    {
        hand = new Hand();
        schoolDashboard = new SchoolDashboard();


    }


}
