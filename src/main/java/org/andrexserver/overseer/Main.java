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

import java.nio.file.Path;
import java.util.List;

@Plugin(
    id = "overseer",
    name = "Overseer",
    version = BuildConstants.VERSION
    ,description = "Overseer is a velocity plugin that allows easy management of players"
    ,authors = {"Andrex"}
)
public class Main {

    @Inject private Logger logger;
    public static Main instance;
    public static List<Player> notifiedAdmins;
    public static ConfigManager config;

    @Inject
    public Main(ProxyServer proxy, Logger logger, @DataDirectory Path dataDirectory) {
        instance = this;
        config = new ConfigManager(dataDirectory, "config.yml", "default_config.yml");

        proxy.getEventManager().register(this, new PlayerJoin());
    }
    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        logger.info("Overseer Initialized!");


    }

    public static void sendMessage(CommandSource source, String text) throws IllegalArgumentException {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Attempting to send empty string");
        } else {
            source.sendMessage(Component.text("§a[§6PteroUtil§a]§r " + text));
        }
    }
}
