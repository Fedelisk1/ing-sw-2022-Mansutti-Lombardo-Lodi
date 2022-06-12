package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.Wizard;

import java.io.Serial;
import java.util.List;

public class WizardsUpdate extends Message{
    @Serial
    private static final long serialVersionUID = -1919261354660976209L;
    private final List<Wizard> availableWizards;

    public WizardsUpdate(List<Wizard> availableWizards) {
        super(SERVER_NICKNAME, MessageType.WIZARDS_UPDATE);

        this.availableWizards = availableWizards;
    }

    public List<Wizard> getAvailableWizards() {
        return availableWizards;
    }
}
