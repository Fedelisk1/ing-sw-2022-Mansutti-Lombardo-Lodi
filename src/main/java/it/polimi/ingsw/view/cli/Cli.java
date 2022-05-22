package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.Server;
import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.view.View;

import java.util.HashMap;
import java.util.Scanner;

public class Cli extends ViewObservable implements View {
    Scanner input;

    public Cli() {
        input = new Scanner(System.in);

    }


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

    public void askServerInfo() {
        HashMap<String, String> serverInfo = new HashMap<>();

        System.out.println("--- Connect to Server ---");

        System.out.println("Please enter the server address, or press ENTER to use the default value (localhost): ");
        String serverAddress = input.nextLine();

        serverAddress = serverAddress.equals("") ? "localhost" : serverAddress;
        serverInfo.put("address", serverAddress);


        System.out.println("Please enter the server port, or press ENTER to use the default port (" + Server.DEFAULT_PORT + "): ");
        String port = input.nextLine();

        port = port.equals("") ? Server.DEFAULT_PORT + "" : port;
        serverInfo.put("port", port);

        notifyObservers(o -> o.onServerInfoInput(serverInfo));
    }

    @Override
    public void nicknameInput() {
        System.out.println("Please enter a nickname: ");
        String nick = input.nextLine();
        notifyObservers(o -> {});
    }

    @Override
    public void showLoginOutcome(boolean success, String username) {
        if (success) {
            System.out.println("Successfully connected to server!");
            nicknameInput();
        } else {
            System.out.println("Unable to connect to server. Please check parameters and retry.");
            System.exit(-1);
        }
    }
}
