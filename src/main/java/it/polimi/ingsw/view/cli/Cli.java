package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.view.ViewInterface;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Scanner;

public class Cli extends ViewObservable implements ViewInterface {
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
    }

    public void askServerInfo() {
        HashMap<String, String> serverInfo = new HashMap<>();
        boolean inputOk = false;

        System.out.println("--- Server connection ---");

        while (!inputOk) {
            System.out.println("Please enter the server address, or press ENTER to use the default value (localhost): ");
            String serverAddress = input.nextLine();

            serverAddress = serverAddress.equals("") ? "localhost" : serverAddress;

            inputOk = ClientController.validateIPAddress(serverAddress);
            if (inputOk)
                serverInfo.put("address", serverAddress);
        }

        while (!inputOk) {
            System.out.println("Please enter the server port, or press ENTER to use the default port (12345): ");
            String port = input.nextLine();

            port = port.equals("") ? "12345" : port;

            inputOk = ClientController.validatePort(port);
            if (inputOk)
                serverInfo.put("port", port);
        }

        notifyObservers(o -> o.onServerInfoInput(serverInfo));
    }
}
