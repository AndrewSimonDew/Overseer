package org.andrexserver.overseer;

import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ConnectionRequestBuilder;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.andrexserver.overseer.Commands.*;
import org.andrexserver.overseer.Events.PlayerJoin;
import org.slf4j.Logger;

import java.nio.file.Path;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArraySet;

@Plugin(
    id = "overseer",
    name = "Overseer",
    version = BuildConstants.VERSION,
    description = "Overseer is a velocity plugin that allows easy management of players",
    authors = {"Andrex"}
)
public class Main {

    @Inject
    public Logger logger;
    public static Main instance;
    public static Set<Player> notifiedAdmins = new CopyOnWriteArraySet<>();
    public static ConfigManager config;
    public final ProxyServer proxy;

    @Inject
    public Main(ProxyServer proxy, Logger logger, @DataDirectory Path dataDirectory) {
        this.proxy = proxy;
        instance = this;
        config = new ConfigManager(dataDirectory, "config.yml", "default_config.yml");

    }
    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        logger.info("Overseer Initialized!");
        proxy.getEventManager().register(this,new PlayerJoin());
        proxy.getCommandManager().register("sendall", new SendAll());
        proxy.getCommandManager().register("send", new Send());
        proxy.getCommandManager().register("playerinfo",new PlayerInfo());
        proxy.getCommandManager().register("playercount", new PlayerCount());
        proxy.getCommandManager().register("proxycount",new ProxyCount());

    }

    public static void sendMessage(CommandSource source, String text) throws IllegalArgumentException {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Attempting to send empty string");
        }

        String prefix = "§a[§6Over§bseer§a]§r ";

        String[] lines = text.split("\n");
        TextComponent.Builder builder = Component.text();

        for (int i = 0; i < lines.length; i++) {
            builder.append(Component.text(prefix + lines[i]));
            if (i < lines.length - 1) {
                builder.append(Component.text("\n"));  // keep the new lines intact
            }
        }

        source.sendMessage(builder.build());
    }

    public CompletableFuture<Boolean> sendPlayerToServer(Player player, RegisteredServer server) {
        return player.createConnectionRequest(server).connect()
                .thenApply(ConnectionRequestBuilder.Result::isSuccessful);
    }
}
