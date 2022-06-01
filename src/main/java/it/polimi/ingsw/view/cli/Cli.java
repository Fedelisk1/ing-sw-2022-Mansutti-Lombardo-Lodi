package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.Server;
import it.polimi.ingsw.model.CloudCard;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.reduced.ReducedCharacterCard;
import it.polimi.ingsw.model.reduced.ReducedGame;
import it.polimi.ingsw.model.reduced.ReducedIsland;
import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.view.View;
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
        System.out.println("▄▄▄ .▄▄▄  ▪   ▄▄▄·  ▐ ▄ ▄▄▄▄▄ ▄· ▄▌.▄▄ · ");
        System.out.println("▀▄.▀·▀▄ █·██ ▐█ ▀█ •█▌▐█•██  ▐█▪██▌▐█ ▀. ");
        System.out.println("▐▀▀▪▄▐▀▀▄ ▐█·▄█▀▀█ ▐█▐▐▌ ▐█.▪▐█▌▐█▪▄▀▀▀█▄");
        System.out.println("▐█▄▄▌▐█•█▌▐█▌▐█ ▪▐▌██▐█▌ ▐█▌· ▐█▀·.▐█▄▪▐█");
        System.out.println(" ▀▀▀ .▀  ▀▀▀▀ ▀  ▀ ▀▀ █▪ ▀▀▀   ▀ •  ▀▀▀▀ ");
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
        System.out.println("Chosen address: " + serverAddress);

        System.out.println("Please enter the server port, or press ENTER to use the default port (" + Server.DEFAULT_PORT + "): ");

        String port = readLine();

        port = port.equals("") ? Server.DEFAULT_PORT + "" : port;
        serverInfo.put("port", port);
        System.out.println("Chosen port: " + port);

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
    @Override
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
    public void showLoginOutcome(boolean userNameAvailable, boolean newGame, String nickname) {
        if (userNameAvailable) {
            System.out.println("Hi, " + nickname + ". You are logged in!");

            if (newGame)
                askPlayersNumberAndGameMode();

        } else {
            System.out.println("Chosen nickname is not available :(\nPlease choose a new one.");
            nicknameInput();
        }
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
     * Displays the lobby status to the user.
     *
     * @param nicknames nicknames of the users already in the lobby.
     * @param players maximum number of players of the lobby.
     */
    public void showLobby(List<String> nicknames, int players) {
        int currentPlayers = nicknames.size();

        System.out.print("Players in the lobby (" + currentPlayers + " out of " + players + "): ");

        for (int i = 0; i < currentPlayers; i++) {
            System.out.print(nicknames.get(i));

            if (i != currentPlayers - 1)
                System.out.print(", ");
            else
                System.out.println();
        }

        if (currentPlayers != players)
            System.out.println("Waiting for players to join...");
    }

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

    @Override
    public void shutdown(String message) {
        System.out.println(message);
        System.exit(1);
    }

    @Override
    public void showServerUnreachable() {
        System.out.println("Server unreachable");
        System.exit(1);
    }

    @Override
    public void showPlayedAssistantCard(String player, int card) {
        System.out.println(player + " has chosen assistant card " + card);
    }

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

            if (island.isMotherNature())
                System.out.println("[MotherNature]");

            String occupier = island.getOccupierNick();
            if (occupier != null && !occupier.equals(""))
                System.out.println("[" + island.getTowers() + " " + occupier + "'s tower(s)]");

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
                        System.out.print("\n");
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

    @Override
    public void askActionPhase1(int count, int maxIsland) {
        String destination = strInput(Arrays.asList("i", "dr", "cc"), "Action Phase 1 - Move "
                + count + ": where do you want to move your student? (enter \"I\" for island or \"DR\" for Dining Room; if you wish to play a Character Card, enter \"CC\") ");

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

    @Override
    public void askActionPhase2(int maxMNSteps) {
        List<String> allowedStr = new ArrayList<>();
        IntStream.rangeClosed(1, maxMNSteps).forEach(i -> allowedStr.add(i + ""));
        allowedStr.add("cc");

        System.out.println("allowed: " + allowedStr);

        String input = strInput(allowedStr, "Please enter how many steps you want to move Mother Nature, ore enter \"CC\" if you wish to play a Character Card");
        try {
            int mnSteps = Integer.parseInt(input);
            notifyObservers(o -> o.onMotherNatureMoved(mnSteps));
        } catch (NumberFormatException e) {
            askCharacterCard();
        }
    }

    @Override
    public void askActionPhase3(List<Integer> alloweValues) {
        List<String> allowedStr = new ArrayList<>(alloweValues.stream().map(Object::toString).toList());
        allowedStr.add("cc");

        System.out.println("allowed: " + allowedStr);

        String input = strInput(allowedStr, "Please, enter a cloud card's number to refill your School Dashboard's entrance, or enter \"CC\" if you wish to play a Character Caard: ");

        if (input.equalsIgnoreCase("cc"))
            askCharacterCard();
        else
            notifyObservers(o -> o.onCloudCardChosen(Integer.parseInt(input)));
    }

    @Override
    public void askCCAllRemoveColorInput() {
        Color color = colorInput();

        notifyObservers(o -> o.onCCAllRemoveColorInput(color));
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

        return inputStr;
    }

    private Color colorInput() {
        return colorInput("Please choose a color ");
    }

    private Color colorInput(String prompt) {
        List<String> allowedColors = Arrays.stream(Color.values()).map(Color::toString).toList();
        String input = strInput(allowedColors, prompt + allowedColors + ": ");
        return Color.valueOf(input.toUpperCase());
    }

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



    private void displayCharacterCards(List<ReducedCharacterCard> characterCards) {
        int i = 1;
        for (ReducedCharacterCard cc : characterCards) {
            System.out.print(i + ". ");
            if (cc.getStudents() != null) {
                cc.getStudents().forEach(ColorCli::printCircles);
                System.out.print(" ");
            }

            System.out.println(cc.getDescription());

            i++;
        }
    }

    private void askCharacterCard() {
        int chosenCard = intInput(1, 3, "Please enter the number of the Character Card to play: ");

        notifyObservers(o -> o.onCCChosen(chosenCard));
    }
}
