package it.polimi.ingsw.model.reduced;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Wizard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ReducedPlayer implements Serializable {
    private final String nickname;
    private final Wizard wizard;

    public ReducedPlayer(Player player) {
        nickname = player.getNickname();
        wizard = player.getWizard();
    }

    public Wizard getWizard() {
        return wizard;
    }

    public String getNickname() {
        return nickname;
    }

    public static List<ReducedPlayer> list(Game game) {
        List<ReducedPlayer> result = new ArrayList<>();

        game.getPlayers().stream()
                .filter(p -> p.getWizard() != null)
                .forEach(p -> result.add(new ReducedPlayer(p)));

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (! (obj instanceof ReducedPlayer))
            return false;

        ReducedPlayer reducedPlayer = (ReducedPlayer) obj;

        return reducedPlayer.getNickname().equals(nickname) && reducedPlayer.getWizard().equals(wizard);
    }
}
