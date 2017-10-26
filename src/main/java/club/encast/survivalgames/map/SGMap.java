package club.encast.survivalgames.map;

import club.encast.survivalgames.util.PartialLocation;

public interface SGMap {

    String getMapName();

    PartialLocation getLobbyLocation();

    PartialLocation getDeathmatchLocation();

    PartialLocation getMiddleLocation();
}
