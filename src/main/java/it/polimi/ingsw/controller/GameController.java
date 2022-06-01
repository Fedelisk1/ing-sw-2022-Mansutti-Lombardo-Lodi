package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.charactercards.*;
import it.polimi.ingsw.model.reduced.ReducedGame;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.view.VirtualView;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Handles the flow of the game serer side.
 */
public class GameController implements Observer {
    private final Game game;
    private final Map<String, VirtualView> nickVirtualViewMap;
    private GameState state;
    private int playerActionCount;
    private final int gameId;

    public GameController(int playersNumber, boolean expertMode, int gameId)
    {
        this.game = new Game(playersNumber, expertMode);
        this.nickVirtualViewMap = Collections.synchronizedMap(new HashMap<>());
        this.state= new InitialState(this);
        this.gameId = gameId;
        playerActionCount=0;
    }

    /**
     * Adds a player to the game.
     * @param nickname Player's nickname.
     * @param virtualView Player's virtualview.
     */
    public void addPlayer(String nickname, VirtualView virtualView) {
        nickVirtualViewMap.put(nickname, virtualView);
        game.addPlayer(nickname);
        game.addObserver(virtualView);
    }

    public List<String> getNicknames() {
        //return game.getPlayers().stream().map(Player::getNickname).collect(Collectors.toList());
        return new ArrayList<>(nickVirtualViewMap.keySet());
    }

    public int getMaxPlayers() {
        return game.getMaxPlayers();
    }

    public void changeState(GameState state)
    {
        System.out.println(gameLogHeader() + " new state: " + state.toString());
        this.state = state;
    }

    public GameState getState() {
        return state;
    }

    private String gameLogHeader() {
        return "[Game" + getId() + "]";
    }

    public void startGame()
    {
        if (game.getPlayersCount() != game.getMaxPlayers())
            return;

        state.startGame(); // state changes to Planning1State
        broadcastMessage("All players have joined.");
        state.planning1();
        askAssistantCard(null);
    }

    public Game getGame() {
        return game;
    }

    public int getId() {
        return gameId;
    }

    public VirtualView getVirtualView(String nickname) {
        return nickVirtualViewMap.get(nickname);
    }

    public VirtualView getCurrentPlayerView() {
        return getVirtualView(game.getCurrentPlayerNick());
    }

    @Override
    public void onMessageArrived(Message message)
    {
        switch (message.getMessageType()) {
            case FILL_CLOUD_CARDS -> state.planning1();
            case PLAY_ASSISTANT_CARD -> {
                PlayAssistantCard msg = (PlayAssistantCard) message;
                state.planning2(msg.getChosenCard());
            }
            case MOVE_STUDENT_TO_ISLAND -> {
                MoveStudentToIsland msg2 = (MoveStudentToIsland) message;
                state.action1Island(msg2.getColor(), msg2.getIslandNumber());
            }
            case MOVE_STUDENT_TO_DINING_ROOM -> {
                MoveStudentToDiningRoom msg3 = (MoveStudentToDiningRoom) message;
                state.action1DiningRoom(msg3.getColor());
            }
            case MOVE_MOTHER_NATURE -> {
                MoveMotherNature msg4 = (MoveMotherNature) message;
                state.action2(msg4.getSteps());
            }
            case CHOOSE_CLOUD_CARD -> {
                ChooseCloudCard msg5 = (ChooseCloudCard) message;
                state.action3(msg5.getCloudCard());
            }
            case PLAY_CHARACTER_CARD -> {
                PlayCharacterCard playCharacterCard = (PlayCharacterCard) message;
                handleCharacterCardRequest(playCharacterCard.getChosenCard());
            }
            case CC_ALL_REMOVE_COLOR -> {
                CCAllRemoveColor msg6 = (CCAllRemoveColor) message;
                playCCAllRemoveColor(msg6.getColor());
            }
            case CC_ALL_REMOVE_COLOR_REPLY -> {
                CCAllRemoveColorReply ccAllRemoveColorReply = (CCAllRemoveColorReply) message;
                playCCAllRemoveColor(ccAllRemoveColorReply.getColor());
            }
//            case CC_BLOCK_COLOR_ONCE -> {
//                CCBlockColorOnce msg7 = (CCBlockColorOnce) message;
//                state.ccBlockColorOnce(msg7.getColor(), msg7.getCardPosition());
//            }
//            case CC_BLOCK_TOWER -> {
//                CCBlockTower msg14 = (CCBlockTower) message;
//                state.ccBlockTower(msg14.getCardPosition());
//            }
//            case CC_CHOOSE_1_DINING_ROOM -> {
//                CCChoose1DiningRoom msg8 = (CCChoose1DiningRoom) message;
//                state.ccChoose1DiningRoom(msg8.getColor(), msg8.getCardPosition());
//            }
//            case CC_CHOOSE_1_TO_ISLAND -> {
//                CCChooseOneToIsland msg9 = (CCChooseOneToIsland) message;
//                state.ccChoose1ToIsland(msg9.getColor(), msg9.getIslandNumber(), msg9.getCardPosition());
//            }
//            case CC_CHOOSE_3_TO_ENTRANCE -> {
//                CCChoose3ToEntrance msg10 = (CCChoose3ToEntrance) message;
//                state.ccChoose3ToEntrance(msg10.getChosenFromCard(), msg10.getChosenFromEntrance(), msg10.getCardPosition());
//            }
//            case CC_CHOOSE_ISLAND -> {
//                CCChooseIsland msg11 = (CCChooseIsland) message;
//                state.ccChooseIsland(msg11.getIslnumb(), msg11.getCardPosition());
//            }
//            case CC_EXCHANGE_2_STUDENTS -> {
//                CCExchange2Students msg12 = (CCExchange2Students) message;
//                state.ccExchange2Students(msg12.getChosenFromEntrance(), msg12.getChosenFromDiningRoom(), msg12.getCardPosition());
//            }
//            case CC_NO_ENTRY_ISLAND -> {
//                CCNoEntryIsland msg13 = (CCNoEntryIsland) message;
//                state.ccNoEntryIsland(msg13.getIslNumb(), msg13.getCardPosition());
//            }
//            case CC_PLUS_2_INFLUENCE -> {
//                CCPlus2Influence msg15 = (CCPlus2Influence) message;
//                state.ccPlus2Influence(msg15.getCardPosition());
//            }
//            case CC_TEMP_CONTROL_PROF -> {
//                CCTempControlProf msg16 = (CCTempControlProf) message;
//                state.ccTempControlProf(msg16.getCardPosition());
//            }
//            case CC_TWO_ADDITIONAL_MOVES -> {
//                CCTwoAdditionalMoves msg17 = (CCTwoAdditionalMoves) message;
//                state.ccTwoAdditionalMoves(msg17.getCardPosition());
//            }
            default -> throw new IllegalStateException("Protocol violation: unexpected " + message.getMessageType());
        }

    }

    public void clearPlayerActionCount() {
        this.playerActionCount = 0;
    }

    public void addPlayerActionCount() {
        this.playerActionCount++;
    }

    public int getPlayerActionCount() {
        return playerActionCount;
    }

    /**
     * Allows to check if the game can still be joined (the maximum number of players has not been reached yet).
     * @return true if the game can be joined, false otherwise.
     */
    public boolean canBeJoined() {
        return (game.getPlayersCount() < game.getMaxPlayers());
    }

    /**
     * Broadcasts a string message to all the views.
     * @param content String message to display.
     */
    private void broadcastMessage(String content) {
        nickVirtualViewMap.values().forEach(virtualView -> virtualView.showStringMessage(content));
    }

    /**
     * Broadcasts a string message to all the views except the provided one.
     * @param content String message to display.
     */
    public void broadcastExceptCurrentPlayer(String content) {
        viewsExceptCurrentPlayer().forEach(virtualView -> virtualView.showStringMessage(content));
    }

    /**
     * Asks current Player to play an assistant card, providing possible values.
     * @param notPlayable List integers representing priorities of not playable character cards.
     */
    public void askAssistantCard(List<Integer> notPlayable) {
        notPlayable = notPlayable == null ? new ArrayList<>() : notPlayable;

        String currentNick = game.getCurrentPlayerNick();
        System.out.println("[Game" + gameId + "] asking assistantCard to " + currentNick);

        VirtualView currentView = getVirtualView(currentNick);
        currentView.askAssistantCard(game.getCurrentPlayerInstance().getHand().getAsMap(), notPlayable);
        broadcastMessage(currentNick + " is choosing an assistant card... ", currentNick);
    }

    /**
     * Broadcasts a message to the views, excluding the nickname provided.
     * @param content message to display.
     * @param excludedNick nickname to exclude from message delivery.
     */
    public void broadcastMessage(String content, String excludedNick) {
        viewsExcept(excludedNick).forEach(virtualView -> virtualView.showStringMessage(content));
    }

    /**
     * @param excludedNick Nickname of the excluded user.
     * @return List of {@link VirtualView} associated with users in the game, except the specified user.
     */
    public List<VirtualView> viewsExcept(String excludedNick) {
        return nickVirtualViewMap.keySet().stream()
                .filter(nick -> !nick.equals(excludedNick))
                .map(nickVirtualViewMap::get)
                .collect(Collectors.toList());
    }

    /**
     * @return List of views of the players in the game except current player.
     */
    public List<VirtualView> viewsExceptCurrentPlayer() {
        return viewsExcept(game.getCurrentPlayerNick());
    }

    /**
     * @return List of the VirtualViews of the players in the game.
     */
    public List<VirtualView> getViews() {
        return nickVirtualViewMap.values().stream().toList();
    }

    /**
     * Broadcasts clients views with up-to-date content.
     */
    public void updateViews() {
        getViews().forEach(vv -> vv.update(new ReducedGame(this.game)));
    }

    /**
     * Shows disconnection message to clients.
     */
    public void handleDisconnection(String nicknameDisconnected) {
        nickVirtualViewMap.remove(nicknameDisconnected);
        getViews().forEach(vv -> vv.shutdown(nicknameDisconnected + " has disconnected"));
    }

    private void handleCharacterCardRequest(int chosenCard) {
        CharacterCard card = game.getCharacterCard(chosenCard);

        broadcastMessage(game.getCurrentPlayerNick() + " is activating Character Card " + chosenCard + "... ");

        switch (card.getType()) {
            case ALL_REMOVE_COLOR -> {
                getCurrentPlayerView().askCCAllRemoveColorInput();
            }
            case BLOCK_COLOR_ONCE -> {

            }
            case CHOOSE_1_DINING_ROOM -> {

            }
            case CHOOSE_1_TO_ISLAND -> {
                System.out.println("Ask color and island number");
            }
            case BLOCK_TOWER -> {
                BlockTower blockTower = (BlockTower) card;
                blockTower.doEffect();
            }
            case CHOOSE_3_TO_ENTRANCE -> {
                System.out.println("Ask 3 from card, ask 3 from entrance");
            }
            case CHOOSE_ISLAND -> {
                System.out.println("ask island number");
            }
            case EXCHANGE_2_STUDENTS -> {
                System.out.println("ask from entrance, ask from dr");
            }
            case NO_ENTRY_ISLAND -> {
                System.out.println("ask island number.");
            }
            case PLUS_2_INFLUENCE -> {
                Plus2Influence plus2Influence = (Plus2Influence) card;
                plus2Influence.doEffect();
            }
            case TEMP_CONTROL_PROF -> {
                TempControlProf tempControlProf = (TempControlProf) card;
                tempControlProf.doEffect();
            }
            case TWO_ADDITIONAL_MOVES -> {
                TwoAdditionalMoves twoAdditionalMoves = (TwoAdditionalMoves) card;
                twoAdditionalMoves.doEffect();
            }
        }
    }

    private void playCCAllRemoveColor(Color color) {
        if(color != null) {
            AllRemoveColor card = (AllRemoveColor) game.getCharacterCard(CharacterCardType.ALL_REMOVE_COLOR);
            card.doEffect(color);
        } else {
            getCurrentPlayerView().askCCAllRemoveColorInput();
        }

        restoreGameFlow();
    }

    private void restoreGameFlow() {
        updateViews();

        if(state instanceof Action1State) {
            askActionPhase1();
        } else if(state instanceof Action2State) {
            askActionPhase2();
        } else if(state instanceof Action3State) {
            askActionPhase3();
        }
    }

    public void askActionPhase1() {
        if(state instanceof Action1State) {
            int action1Moves = ((Action1State) state).getMovesCount();
            if (action1Moves <= 3) {
                getCurrentPlayerView().askActionPhase1(action1Moves, game.getIslands().size());
            }
        }
    }

    public void askActionPhase2() {
        getCurrentPlayerView().askActionPhase2(game.getCurrentPlayerInstance().getMaxSteps());
    }

    public void askActionPhase3() {
        getCurrentPlayerView().askActionPhase3(game.getPlayableCloudCards().stream().map(i -> i+1).toList());
    }

}