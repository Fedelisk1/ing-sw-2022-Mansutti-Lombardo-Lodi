package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.Server;
import it.polimi.ingsw.model.CloudCard;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Wizard;
import it.polimi.ingsw.model.reduced.ReducedCharacterCard;
import it.polimi.ingsw.model.reduced.ReducedGame;
import it.polimi.ingsw.model.reduced.ReducedIsland;
import it.polimi.ingsw.model.reduced.ReducedPlayer;
import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.view.View;
import javafx.application.Platform;
import jdk.dynalink.NamedOperation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Cli extends ViewObservable implements View {
    private final Scanner input;

    public Cli() {
        input = new Scanner(System.in);
    }

    /**
     * Used to ask the user the desired view: cli or gui, if no program arguments have been provided.
     *
     * @return String representing the view: "CLI" or "GUI"
     */
    public static String askViewModeAtStart() {
        Scanner input = new Scanner(System.in);
        boolean inputOk = false;
        String view = "";

        System.out.println("Which interface do you want to use? Please type it here. You can choose between \"cli\" and \"gui\"");
        while (! inputOk)  {
            view = input.nextLine();
            view = view.toUpperCase();
            inputOk = view.equals("CLI") || view.equals("GUI");

            if (! inputOk)
                System.out.println("Input is not correct. Please retry.");
        }
        return view;
    }

    /**
     * Prints intro logo and calls askServerInfo.
     */
    public void begin() {
        System.out.println("  _____          _                   _                 \n" +
                " | ____|  _ __  (_)   __ _   _ __   | |_   _   _   ___ \n" +
                " |  _|   | '__| | |  / _` | | '_ \\  | __| | | | | / __|\n" +
                " | |___  | |    | | | (_| | | | | | | |_  | |_| | \\__ \\\n" +
                " |_____| |_|    |_|  \\__,_| |_| |_|  \\__|  \\__, | |___/\n" +
                "                                           |___/       ");
        System.out.println();

        askServerInfo();
    }

    /**
     * Lets the user enter server's address and port.
     */
    public void askServerInfo() {
        HashMap<String, String> serverInfo = new HashMap<>();

        System.out.println("--- Connect to Server ---");

        System.out.println("Please enter the server address, or press ENTER to use the default value (localhost): ");
        String serverAddress = readLine();

        serverAddress = serverAddress.equals("") ? "localhost" : serverAddress;
        serverInfo.put("address", serverAddress);

        System.out.println("Please enter the server port, or press ENTER to use the default port (" + Server.DEFAULT_PORT + "): ");

        String port = readLine();

        port = port.equals("") ? Server.DEFAULT_PORT + "" : port;
        serverInfo.put("port", port);

        notifyObservers(o -> o.onServerInfoInput(serverInfo));
    }

    /**
     * Displays appropriate message for connection to server.
     *
     * @param connectionOk true if connection was successful.
     */
    @Override
    public void showConnectionOutcome(boolean connectionOk) {
        if (connectionOk) {
            System.out.println("Successfully connected to server!");
            nicknameInput();
        } else {
            System.out.println("Unable to connect to server. Please check parameters and retry.");
            System.exit(-1);
        }
    }

    /**
     * Asks the user to enter the desired nickname.
     */
    public void nicknameInput() {
        System.out.println("Please enter a nickname: ");
        String nick = input.nextLine();
        notifyObservers(o -> o.onNicknameInput(nick));
    }

    /**
     * Displays the outcome of login.
     * @param userNameAvailable true if the chosen nickname is available.
     * @param newGame true if the client is going to start a new game.
     * @param nickname nickname of the user.
     */
    @Override
    public void showLoginOutcome(boolean userNameAvailable, boolean newGame, String nickname, List<Wizard> availableWizards) {
        if (userNameAvailable) {
            System.out.println("Hi, " + nickname + ". You are logged in!");

            if (newGame)
                askPlayersNumberAndGameMode();

            askWizard(availableWizards);

        } else {
            System.out.println("Chosen nickname is not available :(\nPlease choose a new one.");
            nicknameInput();
        }
    }

    @Override
    public void updateAvailableWizards(List<Wizard> availableWizards) {

    }

    /**
     * Asks the user to input the desired number of players to start a new game.
     */
    public void askPlayersNumberAndGameMode() {
        int players = intInput(Arrays.asList(2, 3), "Please enter the number of players (2 or 3): ");
        boolean expertMode = boolInput("Do you want to play in expert mode (y/n)? ");

        notifyObservers(o -> o.onNewGameParametersInput(players, expertMode));
    }

    /**
     * Asks the user to enter the desired wizard.
     * @param availableWizards list of available wizards to choose from.
     */
    public void askWizard(List<Wizard> availableWizards) {
        String chosenWizard = strInput(availableWizards.stream().map(Wizard::toString).toList(), "Please, choose your wizard " + availableWizards);

        notifyObservers(o -> o.onWizardChosen(Wizard.valueOf(chosenWizard)));
    }

    @Override
    public void showWizardError(List<Wizard> availableWizards) {
        System.out.println("\nLooks like this wizard has already been chosen by another player :( \n");

        askWizard(availableWizards);
    }

    /**
     * Displays the lobby status to the user.
     *
     * @param players players already in the lobby.
     * @param playersNumber maximum number of players of the lobby.
     */
    public void showLobby(List<ReducedPlayer> players, int playersNumber) {
        int currentPlayers = players.size();

        System.out.print("Players in the lobby (" + currentPlayers + " out of " + playersNumber + "): ");

        for (int i = 0; i < currentPlayers; i++) {
            System.out.print(players.get(i).getNickname());

            if (i != currentPlayers - 1)
                System.out.print(", ");
            else
                System.out.println();
        }

        if (currentPlayers != playersNumber)
            System.out.println("Waiting for players to join...");
    }

    /**
     * Displays the playable assistant card and asks to choose one
     * @param hand all assistant card in the player's hand
     * @param notPlayable list of not playable cards
     */
    @Override
    public void askAssistantCard(Map<Integer, Integer> hand, List<Integer> notPlayable) {
        System.out.println("--- PLAY ASSISTANT CARD ---");

        List<Integer> playable = hand.keySet().stream().filter(card -> !notPlayable.contains(card)).toList();

        playable.forEach(card -> System.out.println("Card priority: " + card + " - Max MN steps: " + hand.get(card)));

        int chosenCard = intInput(playable, "Please, choose an assistant card (enter card priority): ");

        notifyObservers(o -> o.onAssistantCardChosen(chosenCard));
    }

    @Override
    public void showStringMessage(String content) {
        System.out.println(content);
    }

    /**
     * Displays reason of exit
     * @param message
     */
    @Override
    public void shutdown(String message) {
        System.out.println(message);
        System.exit(1);
    }

    /**
     * Displays reason of exit
     */
    @Override
    public void showServerUnreachable() {
        System.out.println("Server unreachable");
        System.exit(1);
    }

    /**
     * Displays the winner player's name to other player
     * @param winnerNick name of the winner
     */
    @Override
    public void showWinnerToOthers(String winnerNick) {
        System.out.println(winnerNick + " wins the game!");
        System.exit(1);
    }

    /**
     * Displays to the winner player that the game ends
     */
    @Override
    public void notifyWinner() {
        System.out.println("You won! The game ends here.");
        System.exit(1);
    }

    /**
     * Displays to other player that current player has chosen an assistant card
     * @param player who chose the card
     * @param card number of card
     */
    @Override
    public void showPlayedAssistantCard(String player, int card) {
        System.out.println(player + " has chosen assistant card " + card);
    }

    /**
     * Whenever is called shows for all player: the island, every school dashboard, cloud tiles with students/professors/towers/mother nature.
     * If the expert mode is active it displays coins and the three character card
     * @param game the current game
     */
    @Override
    public void update(ReducedGame game) {
        System.out.println("\n\n-------- Islands --------");

        int i = 1;
        for (ReducedIsland island : game.getIslands()) {
            System.out.print("Island " + i + " ");

            // if there is at least one student on the island, print them
            if(island.getStudents().size() > 0)
                island.getStudents().forEach(ColorCli::printCircles);
            System.out.println();

            String attributes = "";

            if (island.isMotherNature())
                attributes += "[MotherNature] ";

            if (island.getNoEntryTiles() > 0)
                attributes += ColorCli.noEntryTiles(island.getNoEntryTiles()) + " ";

            String occupier = island.getOccupierNick();
            if (occupier != null && !occupier.equals(""))
                attributes += "[" + island.getTowers() + " " + occupier + "'s tower(s)]";

            if(! attributes.equals(""))
                System.out.println(attributes);

            i++;
            System.out.println("-------------------------");
        }

        System.out.println("\n\n--- School Dashboards ---");
        game.getSchoolDashboards().forEach((nick, sd) -> {
            System.out.println(nick + ":");
            System.out.print("Entrance -> ");
            sd.getEntrance().forEach(ColorCli::printCircles);

            // if there is at least one student, print dining room
            if (sd.getDiningRoom().values().stream().mapToInt(s -> s).sum() > 0) {
                System.out.print("\nDining Room -> ");
                Arrays.stream(Color.values()).forEach(c -> {
                    ColorCli.printCircles(c, sd.getDiningRoom().get(c));
                    if (sd.getDiningRoom().get(c) > 0)
                        System.out.print("\n               ");
                });
            }

            // if there is at least one controlled professor, print them
            if (sd.getProfessors().size() > 0) {
                System.out.print("\nControlled professors -> ");
                sd.getProfessors().forEach(color -> ColorCli.printCircles(color, 1));
            }

            System.out.println();

            System.out.println("Towers: " + sd.getTowers() + "");

            System.out.println("-------------------------");
        });

        if(game.isExpert()) {
            System.out.println("\n\n--- Coins ---");
            game.getCoins().forEach((nick, coins) -> {
                System.out.print(nick + " -> (" + coins + ") ");
                ColorCli.printCoins(coins);
                System.out.println();
            });

            System.out.println("\n\n--- Character Cards ---");
            displayCharacterCards(game.getCharacterCards());
        }

        i = 1;
        System.out.println("\n\n--- Cloud Cards ---");
        for (CloudCard cc : game.getCloudCards()) {
            System.out.print("Cloud Card " + i + " -> ");
            cc.getStudents().forEach(ColorCli::printCircles);
            System.out.println();
            i++;
        }

        System.out.println();

    }

    /**
     * Displays the request of action phase 1 and asks where do you want to move the student. It is called a number of times equals to number of players plus one.
     * If the expert mode is active accept the request of playing a character card
     * @param count number of iteration
     * @param maxIsland number of island
     * @param expert true if the expert mode is active
     */
    @Override
    public void askActionPhase1(int count, int maxIsland, boolean expert) {
        List<String> allowed = new ArrayList<>(Arrays.asList("i", "dr"));

        String prompt = "Action Phase 1 - Move " + count + ": where do you want to move your student? Please, enter \"I\" for island or \"DR\" for Dining Room";
        if (expert) {
            prompt = prompt.concat("; if you wish to play a Character Card, enter \"CC\" ");
            allowed.add("cc");
        }

        String destination = strInput(allowed, prompt);

        if (destination.equalsIgnoreCase("i")) {
            Color color = colorInput();

            int island = intInput(1, maxIsland, new ArrayList<>(), "Enter island number: ");
            notifyObservers((o) -> o.onStudentMovedToIsland(island, color));
        } else if (destination.equalsIgnoreCase("dr")) {
            Color color = colorInput();

            notifyObservers(o -> o.onStudentMovedToDiningRoom(color));
        } else {
            askCharacterCard();
        }
    }

    /**
     * Displays the request of action phase 2 and asks how many steps you want to move mother nature.
     * If the expert mode is active accept the request of playing a character card
     * @param maxMNSteps number of steps granted by the assistant card played
     * @param expert if the expert mode is active
     */
    @Override
    public void askActionPhase2(int maxMNSteps, boolean expert) {
        List<String> allowedStr = new ArrayList<>();
        IntStream.rangeClosed(1, maxMNSteps).forEach(i -> allowedStr.add(i + ""));

        //System.out.println("allowed: " + allowedStr);

        String prompt = "Please enter how many steps you want to move Mother Nature";
        if (expert) {
            prompt += ", or enter \"CC\" if you wish to play a Character Card";
            allowedStr.add("cc");
        }

        String input = strInput(allowedStr, prompt);
        try {
            int mnSteps = Integer.parseInt(input);
            notifyObservers(o -> o.onMotherNatureMoved(mnSteps));
        } catch (NumberFormatException e) {
            askCharacterCard();
        }
    }

    /**
     * Displays the request of action phase 3 and asks a cloud's number to refill the entrance.
     * If the expert mode is active accept the request of playing a character card
     * @param alloweValues list of cloud's number fill of students
     * @param expert if the expert mode is active
     */
    @Override
    public void askActionPhase3(List<Integer> alloweValues, boolean expert) {
        String prompt = "Please, enter a cloud card's number to refill your School Dashboard's entrance";

        List<String> allowedStr = new ArrayList<>(alloweValues.stream().map(Object::toString).toList());
        if(expert) {
            prompt += ", or enter \"CC\" if you wish to play a Character Caard";
            allowedStr.add("cc");
        }

        String input = strInput(allowedStr, prompt);

        if (input.equalsIgnoreCase("cc"))
            askCharacterCard();
        else
            notifyObservers(o -> o.onCloudCardChosen(Integer.parseInt(input)));
    }

    /**
     * Asks color for activation of CC
     */
    @Override
    public void askCCAllRemoveColorInput() {
        Color color = colorInput();

        notifyObservers(o -> o.onCCAllRemoveColorInput(color));
    }
    /**
     * Asks color for activation of CC
     */
    @Override
    public void askCCBlockColorOnceInput() {
        Color color = colorInput();
        notifyObservers(o -> o.onCCBlockColorOnceInput(color));
    }

    /**
     * Asks color for activation of CC
     * @param allowedValues list of allowed color
     */
    @Override
    public void askCCChoose1DiningRoomInput(List<Color> allowedValues) {
        Color color = colorInput(allowedValues);
        notifyObservers(o -> o.onCCChoose1DiningRoomInput(color));
    }

    /**
     * Asks color for activation of CC
     * @param allowedColors list of allowed color
     * @param maxIsland number of island
     */
    @Override
    public void askCCChoose1ToIslandInput(List<Color> allowedColors, int maxIsland) {
        Color color = colorInput(allowedColors);
        int island = intInput(1, maxIsland, "Please, enter destinaiton island: ");

        notifyObservers(o -> o.onCCChose1ToIslandInput(color, island));
    }

    /**
     * Asks color for activation of CC showing of allowed color
     * @param allowedFromCC list of allowed color from CC
     * @param allowedFromEntrance list of allowed color from entrance
     * @param inputCount number of iteration
     */
    @Override
    public void askCCChoose3ToEntranceInput(List<Color> allowedFromCC, List<Color> allowedFromEntrance, int inputCount) {
        EnumMap<Color, Integer> chosenFromCard = new EnumMap<>(Color.class);
        EnumMap<Color, Integer> chosenFromEntrance = new EnumMap<>(Color.class);
        Color chosenColor;

        List<String> order = new ArrayList<>(Arrays.asList("first", "second", "third"));

        for (int i = 0; i < 3; i++) {
            chosenColor = colorOrStringInput(allowedFromCC, "s", "Please choose the " + order.get(i) + " student from the CARD; enter \"S\" if you don't want to swap students anymore ");

            if(chosenColor == null)
                break;

            allowedFromCC.remove(chosenColor);
            chosenFromCard.put(chosenColor, chosenFromCard.getOrDefault(chosenColor, 0) + 1);

            chosenColor = colorInput(allowedFromEntrance, "Please choose a student from your ENTRANCE to be replaced with a " + chosenColor + " one ");
            allowedFromEntrance.remove(chosenColor);
            chosenFromEntrance.put(chosenColor, chosenFromEntrance.getOrDefault(chosenColor, 0) + 1);
        }

        notifyObservers(o -> o.onCCChoose3ToEntranceInput(chosenFromCard, chosenFromEntrance));
    }

    /**
     * Asks number of island on which apply the effect of CC
     * @param maxIsland number of island
     */
    @Override
    public void askCCChooseIslandInput(int maxIsland) {
        int chosenIsland = intInput(1, maxIsland, "Please, choose an island on which apply the effect: ");

        notifyObservers(o -> o.onCCChooseIslandInput(chosenIsland));
    }

    /**
     * Asks color for activation of CC showing of allowed color
     * @param entrance list of allowed entrance's color
     * @param diningRoom list of allowed dining room's color
     * @param inputCount
     */
    @Override
    public void askCCExchange2StudentsInput(List<Color> entrance, List<Color> diningRoom, int inputCount) {
        EnumMap<Color, Integer> chosenFromEntrance = new EnumMap<>(Color.class);
        EnumMap<Color, Integer> chosenFromDiningRoom = new EnumMap<>(Color.class);
        Color chosenColor;

        List<String> order = new ArrayList<>(Arrays.asList("first", "second", "third"));

        for (int i = 0; i < 2; i++) {
            chosenColor = colorOrStringInput(entrance, "s", "Please choose the " + order.get(i) + " student from your ENTRANCE; enter \"S\" if you don't want to swap students anymore ");

            if(chosenColor == null)
                break;

            entrance.remove(chosenColor);
            chosenFromEntrance.put(chosenColor, chosenFromEntrance.getOrDefault(chosenColor, 0) + 1);

            chosenColor = colorInput(diningRoom, "Please choose a student from your DINING ROOM to be replaced with a " + chosenColor + " from entrance ");
            diningRoom.remove(chosenColor);
            chosenFromDiningRoom.put(chosenColor, chosenFromDiningRoom.getOrDefault(chosenColor, 0) + 1);
        }

        notifyObservers(o -> o.onCCExchange2StudentsInput(chosenFromEntrance, chosenFromDiningRoom));
    }
    /**
     * Asks number of island on which apply the effect of CC
     * @param maxIsland number of island
     */
    @Override
    public void askCCNoEntryIslandInput(int maxIsland) {
        int chosenIsland = intInput(1, maxIsland, "Please, choose an island on which apply the effect: ");

        notifyObservers(o -> o.onCCNoEntryIslandInput(chosenIsland));
    }

    /**
     * Asks an integer input to the user, providing input validation based on the allowedValues list
     * parameter: until the input is not contained into the list an error message is displayed
     * and the input is required again.
     *
     * @param allowedValues allowed values for validation purposes.
     * @param prompt string to prompt to the user before actual input.
     * @return integer value entered by the user.
     */
    private int intInput(List<Integer> allowedValues, String prompt) {
        System.out.println(prompt);
        String inputStr;
        int inputInt = 0;
        boolean inputOk = false;

        while (! inputOk) {
            try {
                inputStr = input.nextLine();
                inputInt = Integer.parseInt(inputStr);
                inputOk = allowedValues.contains(inputInt);
            } catch (NumberFormatException e) {
                inputOk = false;
            }

            if (! inputOk)
                System.out.println("Provided value is not allowed. Please try again.");
        }

        return inputInt;
    }

    /**
     * Allows an integer input from the user, providing input validation: the input must be
     * contained in the interval defined by minAllowed and maxAllowed (boundaries included) and
     * it must not be contained in the excluded list argument.
     *
     * @param minAllowed lower value accepted.
     * @param maxAllowed higher value accepted.
     * @param excluded list of excluded values; values not in the range are not relevant,
     * @param prompt prompt message.
     * @return validated integer input.
     */
    private int intInput(int minAllowed, int maxAllowed, List<Integer> excluded, String prompt) {
        List<Integer> allowed = new ArrayList<>();
        IntStream.rangeClosed(minAllowed, maxAllowed)
                 .filter((i) -> !excluded.contains(i))
                 .forEach(allowed::add);

        return intInput(allowed, prompt);
    }

    /**
     * Allows an integer input from the user, providing input validation: the input must be
     * contained in the interval defined by minAllowed and maxAllowed (boundaries included).
     *
     * @param minAllowed lower value accepted.
     * @param maxAllowed higher value accepted.
     * @param prompt prompt message.
     * @return validated integer input.
     */
    private int intInput(int minAllowed, int maxAllowed, String prompt) {
        return intInput(minAllowed, maxAllowed, new ArrayList<>(), prompt);
    }

    /**
     * Allows y/n input from keyboard with input validation.
     *
     * @param prompt string to prompt to the user.
     * @return true if user entered 'y' or 'Y', false if 'n' or 'N' was entered.
     */
    private boolean boolInput(String prompt) {
        String inputStr = strInput(Arrays.asList("y", "Y", "n", "N"), prompt);

        return inputStr.equalsIgnoreCase("y");
    }

    /**
     * Allows input of a string with validation based on provided allowed values, case-insensitive.
     *
     * @param allowedValues list of allowed values.
     * @param prompt string to prompt to the user.
     * @return validated input from the user.
     */
    private String strInput(List<String> allowedValues, String prompt) {
        System.out.println(prompt);
        String inputStr = "";
        boolean inputOk = false;
        List<String> allowedUpper = allowedValues.stream().map(String::toUpperCase).toList();

        while (! inputOk) {
            inputStr = input.nextLine();
            inputOk = allowedUpper.contains(inputStr.toUpperCase());

            if (! inputOk)
                System.out.println("Input not allowed. Please retry.");
        }

        return inputStr.toUpperCase();
    }

    private Color colorInput() {
        return colorInput(List.of(Color.values()));
    }

    private Color colorInput(List<Color> allowed) {
        return colorInput(allowed, "Please choose a color ");
    }

    private Color colorInput(List<Color> allowed, String prompt) {
        return colorOrStringInput(allowed, null, prompt);
    }

    /**
     * Allows a string input from the user, providing input validation: the input must be
     * contained in the allowed list
     * @param allowed list of allowed color
     * @param string
     * @param prompt
     * @return
     */
    private Color colorOrStringInput(List<Color> allowed, String string, String prompt) {
        List<String> allowedStrings = new ArrayList<>(allowed.stream().map(Color::toString).toList());
        Set<String> allowedColorsSet = new HashSet<>(allowedStrings);

        if (string != null)
            allowedStrings.add(string);

        String input = strInput(allowedStrings, prompt + allowedColorsSet + ": ");

        if (input.equals(string))
            return null;
        else
            return Color.valueOf(input.toUpperCase());
    }

    /**
     * Reads the input
     * @return the value read
     */
    private String readLine() {
        FutureTask<String> futureTask = new FutureTask<>(() -> {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            try {
                return br.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        Thread inputThread = new Thread(futureTask);
        inputThread.start();

        String input = null;

        try {
            input = futureTask.get();
        } catch (InterruptedException | ExecutionException e) {
            futureTask.cancel(true);
            Thread.currentThread().interrupt();
        }
        return input;
    }

    /**
     * Displays all available character card
     * @param characterCards list of CC
     */
    private void displayCharacterCards(List<ReducedCharacterCard> characterCards) {
        int i = 1;
        for (ReducedCharacterCard cc : characterCards) {
            System.out.print(i + ". ");
            ColorCli.printCoins(cc.getCost());

            if (cc.getNoEntryTiles() > 0)
                System.out.print(ColorCli.noEntryTiles(cc.getNoEntryTiles()) + " ");

            if (cc.getStudents() != null) {
                cc.getStudents().forEach(ColorCli::printCircles);
                System.out.print(" ");
            }

            System.out.println(cc.getDescription());

            i++;
        }
    }

    /**
     * Asks the number of character card to play
     */
    private void askCharacterCard() {
        int chosenCard = intInput(1, Game.CHARACTER_CARDS, "Please enter the number of the Character Card to play: ");

        notifyObservers(o -> o.onCCChosen(chosenCard));
    }
}
