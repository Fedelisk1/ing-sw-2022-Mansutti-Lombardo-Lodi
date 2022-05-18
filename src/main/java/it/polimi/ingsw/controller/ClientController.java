package it.polimi.ingsw.controller;

public class ClientController {

    public static boolean validateIPAddress(String ip) {
        String regex = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
        return ip.matches(regex);
    }

    public static boolean validatePort(String port) {
        int portInt;
        try {
            portInt = Integer.parseInt(port);
        } catch (NumberFormatException e) {
            return false;
        }

        return 1 <= portInt && portInt <= 65535;
    }
}
