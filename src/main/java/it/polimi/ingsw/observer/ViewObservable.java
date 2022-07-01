package it.polimi.ingsw.observer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Observable class that can be observed by objects that implement ViewObservable
 */
public abstract class ViewObservable {
    private List<ViewObserver> observers = new ArrayList<>();

    /**
     * Adds new observer
     * @param viewObserver observer to add
     */
    public void addObserver(ViewObserver viewObserver) {
        observers.add(viewObserver);
    }

    public List<ViewObserver> getObservers() {
        return observers;
    }

    /**
     * Notifies all the observers
     * @param lambdaFunction lambda to be called for every observer
     */
    public void notifyObservers(Consumer<ViewObserver> lambdaFunction) {
        observers.forEach(lambdaFunction);
    }
}
