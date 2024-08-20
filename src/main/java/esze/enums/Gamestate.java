package esze.enums;

public enum Gamestate {

    INGAME, LOBBY;

    private static Gamestate state = Gamestate.LOBBY;

    public static Gamestate getGameState() {
        return state;
    }

    public static void setGameState(Gamestate state) {
        Gamestate.state = state;
    }

}
