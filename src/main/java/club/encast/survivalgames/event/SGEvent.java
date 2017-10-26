package club.encast.survivalgames.event;

import club.encast.survivalgames.Game;

public abstract class SGEvent {

    private String name;
    private String description;

    public SGEvent(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public abstract void onActivate(Game game);

    public abstract void onTick(Game game);
}
