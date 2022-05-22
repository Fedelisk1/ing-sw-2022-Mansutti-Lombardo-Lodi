package it.polimi.ingsw.observer;

import java.util.List;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.observer.Observer;

public abstract class Observable {
    private List<Observer> observers;

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers(Message message) {
        observers.forEach((o) -> o.update(message));
    }
}
