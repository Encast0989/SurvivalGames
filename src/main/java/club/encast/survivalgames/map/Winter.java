package club.encast.survivalgames.map;

import club.encast.survivalgames.util.PartialLocation;

public class Winter implements SGMap {

    @Override
    public String getMapName() {
        return "Winter";
    }

    @Override
    public PartialLocation getLobbyLocation() {
        return new PartialLocation(-2689.5, 63, 719.5, 179.8f, 0.4f);
    }

    @Override
    public PartialLocation getDeathmatchLocation() {
        return new PartialLocation(-2573.5, 98, 730.5, 179.1f, -1.3f);
    }

    @Override
    public PartialLocation getMiddleLocation() {
        return new PartialLocation(-2447, 8, 720, 0, 0);
    }
}
