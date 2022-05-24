package it.polimi.ingsw.observer;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.network.message.Message;

public abstract class Observable {
    private List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers(Message message) {
        observers.forEach((o) -> o.onMessageArrived(message));
    }
}
