package it.polimi.ingsw.model;

import java.util.ArrayList;

public class Player {
    private String nickname;
    private int coins;
    private Hand hand;
    private SchoolDashboard schoolDashboard;

    public Player(String nickname)
    {
        hand = new Hand();
        schoolDashboard = new SchoolDashboard();
        coins = 0;
        this.nickname = nickname;

    }



}
