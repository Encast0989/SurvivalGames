package club.encast.survivalgames;

public class GameSettings {

    private boolean pvp;

    public GameSettings() {
        this.pvp = false;
    }

    public boolean isPvp() {
        return pvp;
    }

    public void setPvp(boolean pvp) {
        this.pvp = pvp;
    }
}
