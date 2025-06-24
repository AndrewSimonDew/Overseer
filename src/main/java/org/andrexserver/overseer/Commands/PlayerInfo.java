package org.andrexserver.overseer.Commands;

import com.google.common.base.Optional;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.Component;
import org.andrexserver.overseer.Main;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PlayerInfo implements SimpleCommand {
    @Override
    public void execute(Invocation invocation) {
        CommandSource source = invocation.source();
        String[] args = invocation.arguments();
        if (!source.hasPermission("oversser.playerinfo")) {
            Main.sendMessage(source,"§cSorry, You don't have permission to use this command!");
            return;
        }

        if (args.length != 1) {
            Main.sendMessage(source,"§cUsage /playerinfo <player>");
            return;
        }
        String player = args[0];
        Player p = Main.instance.proxy.getAllPlayers().stream()
                .filter(s -> Objects.equals(s.getUsername(), player))
                .findFirst()
                .orElse(null);
        if(p == null) {
            Main.sendMessage(source,"§cPlayer §6" + player + " §c not online!");
            return;
        }
        Main.sendMessage(source,printPlayerInfo(p));

    }

    public String printPlayerInfo(Player player) {
        // Basic Info
        String username = player.getUsername();
        String uuid = player.getUniqueId().toString();
        String currentServer = player.getCurrentServer()
                .map(serverConnection -> serverConnection.getServerInfo().getName())
                .orElse("Not connected to a server");
        long ping = player.getPing();

        // Client Info
        String clientBrand = player.getClientBrand() != null ? player.getClientBrand() : "Unknown";


        return String.format("§a=== Player Info ===\n" +
                        "§6Username: §a%s\n" +
                        "§6UUID: §a%s\n" +
                        "§6Current Server: §a%s\n" +
                        "§6Ping: §a%d ms\n" +
                        "§6Client Brand: §a%s\n" +
                        "§a=== Player Info ===\n",
                username, uuid, currentServer, ping, clientBrand);
    }
    @Override
    public List<String> suggest(Invocation invocation) {
        String[] args = invocation.arguments();
        if (args.length == 1) {
            return Main.instance.proxy.getAllPlayers().stream()
                    .map(Player::getUsername)
                    .collect(Collectors.toList());
        }
        return List.of();
    }
}
