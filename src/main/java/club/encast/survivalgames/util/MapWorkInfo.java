package club.encast.survivalgames.util;

public class MapWorkInfo {

    private boolean created;
    private String previousMapName;
    private String currentMapName;

    public MapWorkInfo(boolean created, String previousMapName, String currentMapName) {
        this.created = created;
        this.previousMapName = previousMapName;
        this.currentMapName = currentMapName;
    }

    public boolean isCreated() {
        return created;
    }

    public String getPreviousMapName() {
        return previousMapName;
    }

    public String getCurrentMapName() {
        return currentMapName;
    }
}
