package club.encast.survivalgames.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        e.setCancelled(true);
        Bukkit.getServer().getOnlinePlayers().forEach(o ->
                o.sendMessage("§b[TESTER] " + e.getPlayer().getName() + "§f: " + e.getMessage()));
    }
}
