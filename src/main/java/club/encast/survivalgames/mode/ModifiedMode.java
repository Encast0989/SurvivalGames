package club.encast.survivalgames.mode;

import club.encast.survivalgames.Game;

public abstract class ModifiedMode {

    private String name;

    public ModifiedMode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void onActivate(Game game);

    public abstract void onTick(Game game);
}
