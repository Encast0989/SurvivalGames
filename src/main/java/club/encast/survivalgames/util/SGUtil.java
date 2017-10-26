package club.encast.survivalgames.util;

import club.encast.survivalgames.Game;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;

import java.util.concurrent.TimeUnit;

public class SGUtil {

    public static String formatTime(long time, TimeUnit unit) {
        return (time == 1 ? time + " " + unit.name().toLowerCase().substring(0, unit.name().length() - 1)
                : time + " " + unit.name().toLowerCase());
    }

    public static Location convertToBukkitLocation(PartialLocation coord, Game game) {
        return new Location(Bukkit.getServer().getWorld(game.getMapManager().getCurrentWorldName()),
                coord.getX(),
                coord.getY(),
                coord.getZ(),
                coord.getYaw(),
                coord.getPitch()
        );
    }

    public static String centerMessage(String message){
        final int CENTER_PX = 154;
        if(message == null || message.equals("")) {
            return null;
        }

        message = ChatColor.translateAlternateColorCodes('&', message);

        int messagePxSize = 0;
        boolean previousCode = false;
        boolean isBold = false;

        for(char c : message.toCharArray()) {
            if(c == '§') {
                previousCode = true;
            } else if(previousCode) {
                previousCode = false;
                if(c == 'l' || c == 'L') {
                    isBold = true;
                } else isBold = false;
            } else {
                DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
                messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
                messagePxSize++;
            }
        }

        int halvedMessageSize = messagePxSize / 2;
        int toCompensate = CENTER_PX - halvedMessageSize;
        int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
        int compensated = 0;
        StringBuilder sb = new StringBuilder();
        while(compensated < toCompensate) {
            sb.append(" ");
            compensated += spaceLength;
        }
        return sb.toString() + message;
    }
}
