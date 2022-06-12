package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.Wizard;

import java.io.Serial;

public class ChooseWizard extends Message{
    @Serial
    private static final long serialVersionUID = -7015776671667168314L;
    private final Wizard wizard;

    public ChooseWizard(String nickname, Wizard wizard) {
        super(nickname, MessageType.CHOOSE_WIZARD);
        this.wizard = wizard;
    }

    public Wizard getWizard() {
        return wizard;
    }
}
