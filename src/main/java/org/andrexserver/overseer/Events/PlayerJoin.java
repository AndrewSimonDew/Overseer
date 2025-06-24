package org.andrexserver.overseer.Events;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.ServerPreConnectEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import org.andrexserver.overseer.Main;

public class PlayerJoin {

    @Subscribe
    public void onPlayerJoin(ServerPreConnectEvent event) {
        Player player = event.getPlayer();
        RegisteredServer server = event.getOriginalServer();
        RegisteredServer previousServer = event.getPreviousServer();
        if(player.hasPermission("overseer.join-notified")) {
            Main.notifiedAdmins.add(player);
        }
        for(Player p : Main.notifiedAdmins) {
            if(previousServer == null) {
                Main.sendMessage(p,"§c" + p.getUsername() + "§a has just joined the proxy, now on: §6" + server.getServerInfo().getName());
            } else {
                Main.sendMessage(p,"§c" + p.getUsername() + "§a has switched to §6" + server.getServerInfo().getName());
            }
        }
    }
}
