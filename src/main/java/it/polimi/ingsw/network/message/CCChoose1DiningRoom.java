package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.Color;

public class CCChoose1DiningRoom extends Message {

        private MessageType messageType;
        private String nickname;
        private Color color;

        public CCChoose1DiningRoom(String nickname, MessageType messageType, Color color) {
            super(nickname, MessageType.CC_CHOOSE_1_DINING_ROOM);
            this.color=color;
        }

        public Color getColor() {
            return color;
        }

}
