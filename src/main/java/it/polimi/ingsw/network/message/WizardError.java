package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.Wizard;

import java.io.Serial;
import java.util.List;

public class WizardError extends Message {
    @Serial
    private static final long serialVersionUID = -5456575696854881718L;
    private final List<Wizard> availableWizards;

    public WizardError(List<Wizard> availableWizards) {
        super(SERVER_NICKNAME, MessageType.WIZARD_ERROR);

        this.availableWizards = availableWizards;
    }

    public List<Wizard> getAvailableWizards() {
        return availableWizards;
    }
}
