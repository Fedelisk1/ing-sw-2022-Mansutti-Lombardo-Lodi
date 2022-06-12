package it.polimi.ingsw.model;

public enum Wizard {
    KING, PIXIE, SORCERER, WIZARD;

    public String toString() {
        return Utils.toTitleCase(name());
    }
}
