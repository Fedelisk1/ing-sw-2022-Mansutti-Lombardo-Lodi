package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.Color;

public class CCBlockColorOnce extends Message{

        private MessageType messageType;
        private String nickname;
        private Color color;

        public CCBlockColorOnce(String nickname, MessageType messageType, Color color) {
            super(nickname, MessageType.CC_BLOCK_COLOR_ONCE);
            this.color=color;
        }

        public Color getColor() {
            return color;
        }

}
