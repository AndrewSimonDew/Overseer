package org.andrexserver.overseer.Commands;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import org.andrexserver.overseer.Main;


public class Send implements SimpleCommand {
    @Override
    public void execute(Invocation invocation) {
        CommandSource source = invocation.source();
        if (!source.hasPermission("overseer.send")) {
            Main.sendMessage(source, "§cSorry, you don't have permission to do that!");
            return;
        }

        if (invocation.arguments().length != 2) {
            Main.sendMessage(source, "§cCorrect Usage: §6/send <player> <server>");
            return;
        }

        String player = invocation.arguments()[0];
        String server = invocation.arguments()[1];
        RegisteredServer registeredServer = Main.instance.proxy.getAllServers().stream()
                .filter(s -> s.getServerInfo().getName().equals(server))
                .findFirst()
                .orElse(null);

        if (registeredServer == null) {
            Main.sendMessage(source, "§cServer not found: §6" + server);
            return;
        }
        Player p = Main.instance.proxy.getAllPlayers().stream()
                .filter(s -> s.getUsername().equals(player))
                .findFirst()
                .orElse(null);
        if (p == null) {
            Main.sendMessage(source, "§cPlayer §6" + player + " §c not online!");
            return;
        }
        Main.instance.sendPlayerToServer(p,registeredServer).thenAccept(success -> {
            if (success) {
                Main.sendMessage(source,"§aSuccessfully sent §6" + player + " §ato §6"+ server);
            } else {
                Main.sendMessage(source,"§cCouldn't send §6" + player + " §cto §6"+ server);
            }
        });

    }
}
