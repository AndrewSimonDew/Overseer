package org.andrexserver.overseer;

import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import net.kyori.adventure.text.Component;
import org.andrexserver.overseer.Events.PlayerJoin;
import org.slf4j.Logger;

import java.lang.reflect.Proxy;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
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
    private final ProxyServer proxy;

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

    }

    public static void sendMessage(CommandSource source, String text) throws IllegalArgumentException {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Attempting to send empty string");
        } else {
            source.sendMessage(Component.text("§a[§6PteroUtil§a]§r " + text));
        }
    }
}
