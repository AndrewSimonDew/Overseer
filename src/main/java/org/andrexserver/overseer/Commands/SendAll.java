package org.andrexserver.overseer.Commands;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import org.andrexserver.overseer.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class SendAll implements SimpleCommand {
    @Override
    public void execute(Invocation invocation) {
        CommandSource source = invocation.source();
        if (!source.hasPermission("overseer.sendall")) {
            Main.sendMessage(source, "§cSorry, you don't have permission to do that!");
            return;
        }

        if (invocation.arguments().length != 1) {
            Main.sendMessage(source, "§cCorrect Usage: §6/sendall <server>");
            return;
        }

        String server = invocation.arguments()[0];
        RegisteredServer registeredServer = Main.instance.proxy.getAllServers().stream()
                .filter(s -> s.getServerInfo().getName().equals(server))
                .findFirst()
                .orElse(null);

        if (registeredServer == null) {
            Main.sendMessage(source, "§cServer not found: §6" + server);
            return;
        }

        var players = Main.instance.proxy.getAllPlayers();
        if (players.isEmpty()) {
            Main.sendMessage(source, "§cNo players online to send.");
            return;
        }

        List<CompletableFuture<Boolean>> futures = new ArrayList<>();
        for (Player player : players) {
            futures.add(Main.instance.sendPlayerToServer(player, registeredServer));
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).thenRun(() -> {
            Main.sendMessage(source, "§aAll players have been processed for transfer to §6" + server);
            // Optional: add detailed success/fail counts here by analyzing futures
        });
    }
    @Override
    public List<String> suggest(Invocation invocation) {
        String[] args = invocation.arguments();
        if (args.length == 1) {
            return Main.instance.proxy.getAllServers().stream()
                    .map(server -> server.getServerInfo().getName())
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}
