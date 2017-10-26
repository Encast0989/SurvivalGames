package club.encast.survivalgames.map;

import club.encast.survivalgames.util.PartialLocation;

public class AelinsTower implements SGMap {

    @Override
    public String getMapName() {
        return "Aelin's Tower";
    }

    @Override
    public PartialLocation getLobbyLocation() {
        return new PartialLocation(-2468.5, 25, 778, -176.6f, 1.5f);
    }

    @Override
    public PartialLocation getDeathmatchLocation() {
        return new PartialLocation(-2405.5, 12, 779.5, 180f, -1.3f);
    }

    @Override
    public PartialLocation getMiddleLocation() {
        return new PartialLocation(-2468, 41, 778, 0, 0);
    }
}
