package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.Server;
import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

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

        // ask server info
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
        notifyObservers(o -> {o.onNicknameInput(nick);});
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

        System.out.print("Users in the lobby (" + currentPlayers + " out of " + players + "): ");

        for (int i = 0; i < currentPlayers; i++) {
            System.out.print(nicknames.get(i));

            if (i != currentPlayers - 1)
                System.out.print(", ");
        }
    }

    /**
     * Asks an integer input to the user.
     *
     * @param allowedValues allowed values for validation purposes.
     * @param prompt string to prompt to the user before actual input.
     * @return integer value entered by the user.
     */
    private int intInput(List<Integer> allowedValues, String prompt) {
        System.out.println(prompt);
        String inputStr = input.nextLine();
        int inputInt = 0;
        boolean inputOk = false;

        while (! inputOk) {
            try {
                inputInt = Integer.parseInt(inputStr);
                inputOk = allowedValues.contains(inputInt);
                inputOk = true;
            } catch (NumberFormatException e) {
                System.out.println("Provided value is not allowed. Please try again.");
            }
        }

        return inputInt;
    }

    /**
     * Allows y/n input from keyboard with input validation.
     *
     * @param prompt string to prompt to the user.
     * @return true if user entered 'y' or 'Y', false if 'n' or 'N' was entered.
     */
    private boolean boolInput(String prompt) {
        System.out.println(prompt);
        String inputStr = strInput(Arrays.asList("y", "Y", "n", "N"), prompt);

        return inputStr.equalsIgnoreCase("y");
    }

    /**
     * Allows input of a string with validation based on provided allowed values.
     *
     * @param allowedValues list of allowed values.
     * @param prompt string to prompt to the user.
     * @return validated input from the user.
     */
    private String strInput(List<String> allowedValues, String prompt) {
        System.out.println(prompt);
        String inputStr = "";
        boolean inputOk = false;

        while (! inputOk) {
            inputStr = input.nextLine();
            inputOk = allowedValues.contains(inputStr);

            if (! inputOk)
                System.out.println("Input not allowed. Please retry.");
        }

        return inputStr;
    }

    private String readLine() {
        /*
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            return br.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        */

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
}
