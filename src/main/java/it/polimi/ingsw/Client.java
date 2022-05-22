package it.polimi.ingsw;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.view.cli.Cli;

public class Client {
    public static void main(String[] args) {
        boolean useCli = false;

        for (String arg : args) {
            if (arg.equals("--cli")) {
                useCli = true;
            }
        }

        if (useCli) {
            Cli cli = new Cli();
            ClientController cc = new ClientController(cli);
            cli.addObserver(cc);
            cli.begin();
        } else {
            // TODO: start JaxaFX thread
        }
    }
}
