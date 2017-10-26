package club.encast.survivalgames.state;

public enum SGGameState {

    LOBBY(0, "lobby"),
    STARTING(1, "starting"),
    GAME(2, "game"),
    DEATHMATCH(3, "deathmatch"),
    END(4, "end");

    private int index;
    private String internalName;

    SGGameState(int index, String internalName) {
        this.index = index;
        this.internalName = internalName;
    }

    public int getIndex() {
        return index;
    }

    public String getInternalName() {
        return internalName;
    }
}
