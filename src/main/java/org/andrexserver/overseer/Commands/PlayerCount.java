package org.andrexserver.overseer.Commands;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import org.andrexserver.overseer.Main;

import java.util.List;
import java.util.stream.Collectors;

public class PlayerCount implements SimpleCommand {
    @Override
    public void execute(Invocation invocation) {
        CommandSource source = invocation.source();
        String[] args = invocation.arguments();
        if (!source.hasPermission("oversser.playercount")) {
            Main.sendMessage(source,"§cSorry, You don't have permission to use this command!");
            return;
        }

        if (args.length != 1) {
            Main.sendMessage(source,"§cUsage /playercount <server>");
            return;
        }
        String server = args[0];
        RegisteredServer registeredServer = Main.instance.proxy.getAllServers().stream()
                .filter(s -> s.getServerInfo().getName().equals(server))
                .findFirst()
                .orElse(null);

        if (registeredServer == null) {
            Main.sendMessage(source, "§cServer not found: §6" + server);
            return;
        }
        Main.sendMessage(source,"§aThere are §6" + registeredServer.getPlayersConnected().size() + "§a Players connected to §6" + server);
    }

    @Override
    public List<String> suggest(Invocation invocation) {
        if (invocation.arguments().length == 1) {
            return Main.instance.proxy.getAllServers().stream()
                    .map(server -> server.getServerInfo().getName())
                    .collect(Collectors.toList());
        }
        return List.of();
    }
}
