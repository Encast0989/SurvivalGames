package club.encast.survivalgames.map;

public enum MapType {

    AELINS_TOWER(new AelinsTower(), "aelins_tower"),
    WINTER(new Winter(), "winter");

    private SGMap map;
    private String fileName;

    MapType(SGMap map, String fileName) {
        this.map = map;
        this.fileName = fileName;
    }

    public SGMap getMap() {
        return map;
    }

    public String getFileName() {
        return fileName;
    }
}
