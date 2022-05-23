package it.polimi.ingsw.network.message;

public enum MessageType {
    LOGIN_REQUEST, LOGIN_OUTCOME,
    PLAYERNUMBER_REQUEST,
    NEW_GAME_REQUEST,
    ERROR,
    START_GAME,
    FILL_CLOUD_CARDS,
    PLAY_ASSISTANT_CARD,
    MOVE_STUDENT_TO_ISLAND,
    MOVE_STUDENT_TO_DINING_ROOM,
    MOVE_MOTHER_NATURE,
    CC_ALL_REMOVE_COLOR,
}
