package it.polimi.ingsw.observer;

import it.polimi.ingsw.network.message.Message;

public interface Observer {
    void onMessageArrived(Message message);
}
