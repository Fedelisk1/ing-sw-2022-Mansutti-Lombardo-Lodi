package it.polimi.ingsw.model;

public enum Color {
    GREEN, RED, YELLOW, PINK, BLUE;

    public String toString() {
        return toTitleCase(name());
    }

    public String toTitleCase(String input) {
        StringBuilder titleCase = new StringBuilder(input.length());
        boolean nextTitleCase = true;

        for (char c : input.toCharArray()) {
            if (Character.isSpaceChar(c)) {
                nextTitleCase = true;
            } else if (nextTitleCase) {
                c = Character.toTitleCase(c);
                nextTitleCase = false;
            }

            titleCase.append(c);
        }

        return titleCase.toString();
    }
}

